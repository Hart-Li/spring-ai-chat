## 多轮对话优化：模板化注入

---

### 1. 什么是 PromptChatMemoryAdvisor

`MemoryChatMemoryAdvisor` 和 `PromptChatMemoryAdvisor` 两个类都是 `BaseChatMemoryAdvisor` 接口的实现类，都可用于实现自动化管理聊天历史。

```
Advisor
    |- CallAdvisor,StreamAdvisor（非流式、流式响应的顾问接口）
        |- BaseAdvisor （组合非流式、流式响应的顾问接口）
            |- BaseChatMemoryAdvisor （ChatMemory 的基础顾问接口）
                |- MessageChatMemoryAdvisor
                |- PromptChatMemoryAdvisor
```

```
场景设定：你正在开发咖啡订购助手，用户连续对话：
    用户：“一杯冰美式，少冰”
    用户：“再加一份糖”
    用户：“改成大杯”
------------------------------------------------------
MessageChatMemoryAdvisor：
    List<Message> history = Arrays.asList(
        new UserMessage("一杯冰美式，少冰"),
        new UserMessage("再加一份糖"),
        new UserMessage("改成大杯")
    )    
特点：把历史对话（history）原封不动加到心情求中，AI 将看到完整对话流水账，优点是精确还原了每条消息，缺点是 AI 需要自行理解上下文的关联，影响效率。如果聊天历史过长时，需要消耗大量的Token，还可能忽略早期关键信息（位置越靠后权重越低）。
------------------------------------------------------
PromptChatMemoryAdvisor：
    USER: 一杯冰美式，少冰
    USER: 再加一份糖
    USER: 改成大杯

1. memory = "USER: 一杯冰美式，少冰" + "\nUSER: 再加一份糖" + "\nUSER: 改成大杯"
2. systemPromptTemplate("...MEMORY:{memory}...") -> SystemMessage

特点：将聊天历史按照模板格式组合成新的系统提示， AI 可以直接看到结构化知识，无需理解上下文的关联，显著提升多轮对话体验。适合需要快速提取核心信息的场景，例如：客服中心/购物助手。
```

---

### 2. 实现自动管理对话记忆

- 创建记忆顾问（单次）

```java
@Configuration
public class SpringAIConfig {

    @Resource
    private RedisChatMemoryRepository redisChatMemoryRepository;

    @Bean
    public ChatClient openAiChatClient(ChatClient.Builder builder) {
        return builder
            .defaultSystem(
                system -> system.text("你是一名{role}，擅长精准而简洁得回答问题")
                    .param("role", "订购助手")).build();
    }
    
    // 其他代码相同，因此省略......
}

    @GetMapping("/ai/chat/deepseek/advisor")
    public String deepSeekChatAdvisor(String question, String conversationId) {
        // 1. 生成会话ID（使用session id 确保用户隔离）
        // String conversationId = session.getId();
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(conversationId)) {
            return "error";
        }
        conversationId = "chat_memory:" + conversationId;
        // 2. 创建模板 advisor
        PromptChatMemoryAdvisor advisor = PromptChatMemoryAdvisor
            .builder(chatMemory)
            .conversationId(conversationId)
            .build();
        // 3.请求模型并提取文本响应内容
        return chatClient.prompt()
            .user(question)
            .advisors(advisor)      // 使用顾问管理对话记忆
            .call()
            .content();
    }
```

测试：
1. http://localhost:8080/ai/chat/deepseek/advisor?question=一杯冰美式，少冰&conversationId=123
2. http://localhost:8080/ai/chat/deepseek/advisor?question=再加一份糖&conversationId=123
3. http://localhost:8080/ai/chat/deepseek/advisor?question=改成大杯&conversationId=123

测试结果：
```
---------------------
MEMORY:
ASSISTANT:好的，一杯冰美式，少冰。  
需要调整其他选项吗？例如甜度、浓度或杯型？
USER:再加一份糖
ASSISTANT:好的，一杯冰美式，少冰，加一份糖。  
还需要其他调整吗？
---------------------
```

PS: 需要在 application.yml 中开启 debug 日志级别才可以看到上述日志。配置如下： 

```java
logging:
    level:
        org.springframework: debug
```

- 创建记忆顾问（全局）
```java
@Configuration
public class SpringAIConfig {

    @Resource
    private RedisChatMemoryRepository redisChatMemoryRepository;

    @Bean
    public ChatClient openAiChatClient(ChatClient.Builder builder) {
        return builder
            .defaultAdvisors(PromptChatMemoryAdvisor
                .builder(chatMemory(redisChatMemoryRepository))
                .build())
            .defaultSystem(
                system -> system.text("你是一名{role}，擅长精准而简洁得回答问题")
                    .param("role", "订购助手")).build();
    }
    
    // 其他代码相同，因此省略......
}

    @GetMapping("/ai/chat/deepseek/advisor")
    public String deepSeekChatAdvisor(String question, String conversationId) {
        // 1. 生成会话ID（使用session id 确保用户隔离）
        // String conversationId = session.getId();
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(conversationId)) {
            return "error";
        }
        conversationId = "chat_memory:" + conversationId;
        // 2.请求模型并提取文本响应内容
        String finalConversationId = conversationId;
        return chatClient.prompt()
            .user(question)
            .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, finalConversationId))      // 使用顾问管理对话记忆
            .call()
            .content();
    }
```

测试路由以及效果与单次创建模板顾问的效果相同，因此不再展示。