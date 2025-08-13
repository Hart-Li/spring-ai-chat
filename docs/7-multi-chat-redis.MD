## 基于 Redis 的多轮对话实现

--- 

### 1. 回顾 ChatMemoryRepository 

```
ChatMemoryRepository                    存储库抽象
    |- InMemoryChatMemoryRepository     内存存储库
    |- JdbcChatMemoryRepository         JDBC 存储库
    |- RedisChatMemoryRepository        Redis 存储库
```

> PS：官网未提供 RedisChatMemoryRepository 实现类，需手动开发实现。

---

### 2. 可序列化的 Message 包装类

这一部分是本次的难点，创建包装类是因为 `Message` 接口的常用实现均无法实现序列化，但是存储到 Redis 中的数据必须序列化.

```
1. 序列化：具体实现类 -> Message -> SerializableMessage -> Redis bytes
2. 反序列化： Redis bytes -> SerializableMessage -> Message -> 具体实现类
```

```java
public class SerializableMessage implements Serializable {
    // 定义序列化版本号，用于版本控制
    private static final long serialVersionUID = 1L;

    // 明确 Message 接口实现类的枚举（三种消息类型：系统消息、用户消息、助手消息）
    public enum MessageType { SYSTEM, USER, ASSISTANT }

    private final MessageType messageType;  // 消息类型
    private final String content; // 消息内容
    private final Map<String, Object> metadata; // 消息元数据

    // 从 Spring AI 的 Message 对象构造 SerializableMessage 对象(保存对话记录时使用)
    public SerializableMessage(Message message) {
        this.messageType = mapMessageType(message);
        this.content = message.getText();
        this.metadata = message.getMetadata();
    }

    // 判断属于哪种具体的实现类类型
    private MessageType mapMessageType(Message message) {
        if (message instanceof SystemMessage) {
            return MessageType.SYSTEM;
        } else if (message instanceof UserMessage) {
            return MessageType.USER;
        } else if (message instanceof AssistantMessage) {
            return MessageType.ASSISTANT;
        } else {
            throw new IllegalArgumentException("Unknown message type: " + message.getClass().getName());
        }
    }

    // 将 SerializableMessage 对象转换回 Spring AI 的 Message 对象(查询对话记录时使用)
    public Message toMessage() {
        switch (messageType) {
            case SYSTEM:
                return new SystemMessage(content);
            case USER:
                return new UserMessage(content);
            case ASSISTANT:
                return new AssistantMessage(content, metadata);
            default:
                throw new IllegalArgumentException("Unknown message type: " + messageType);
        }
    }

    @JsonCreator
    public SerializableMessage(
        @JsonProperty("messageType") MessageType messageType,
        @JsonProperty("content") String content,
        @JsonProperty("metadata") Map<String, Object> metadata
    ) {
        this.messageType = messageType;
        this.content = content;
        this.metadata = metadata;
    }

}
```

--- 

### 3. 配置 RedisTemplate

#### 3.1 Redis 依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>3.5.4</version>
</dependency>
```

#### 3.2 Redis 连接配置

```
spring:
  # Redis 配置
  data:
    redis:
      host: localhost
      port: 6379
```

#### 3.3 模板配置

模板配置这部分是本次的难点，分如下四个步骤实现：

1. 声明自定义的 RedisTemplate<String, SerializableMessage>模板实例
>   PS：Value 的泛型指定 SerializableMessage 类型
2. 配置支持多态的 Jackson 映射器
>   原因：多态场景下（如父类引用指向子类对象），序列化时若不存储类型信息，反序列化会丢失子类型细节，导致数据还原失败（ClassCastException异常）
3. 创建自定义序列化工具
>   PS：自定义序列化工具基于上一步的 Jackson 映射器去创建
4. Key 使用 String 序列化：Value 使用自定义的序列化工具

```java
@Configuration
public class RedisConfig {
    
    // 1. 声明自定义的 RedisTemplate
    @Bean
    public RedisTemplate<String, SerializableMessage> redisTemplate(
        RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, SerializableMessage> template = new RedisTemplate<>();
        
        // 2. 配置支持多态的 Jackson 映射器
        ObjectMapper objectMapper = new ObjectMapper();         // Jackson 映射器
        objectMapper.activateDefaultTyping(                     // 激活多态类型支持
            objectMapper.getPolymorphicTypeValidator(),         // 多态类型验证器(校验类型的合法性，防止恶意注入)
            ObjectMapper.DefaultTyping.EVERYTHING,              // 指定类型信息的添加范围：所有类型（包括 final 类型）均添加
            JsonTypeInfo.As.PROPERTY                            // 指定类型信息的嵌入方式：独立属性的方式
        );
        // 3. 创建自定义的序列化工具
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        
        // 4. Key 使用 String 序列化：Value 使用自定义的序列化工具
        template.setKeySerializer(new StringRedisSerializer());
        
        return template;
    }
}
```

### 4. 保存对话记录

`ChatMemoryRepository` 接口的 `saveAll()` 方法作用是以`全量替换`的方式更新指定会话ID的聊天消息。

```java
public interface ChatMemoryRepository {
    void saveAll(String conversationId, List<Message> messages);
}
```

具体的对应实现：
```java
@Component
public class RedisChatMemoryRepository implements ChatMemoryRepository {

    @Resource
    private RedisTemplate<String, SerializableMessage> redisTemplate;

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        // 数据转换
        List<SerializableMessage> serializableMessages = messages.stream()
            .map(SerializableMessage::new)
            .toList();
        // 向 Redis 列表的右侧（即尾部）一次性添加多个元素
        if (!serializableMessages.isEmpty()) {
            redisTemplate.opsForList().rightPushAll(conversationId, serializableMessages);
        }
    }
}
```

### 5. 查询对话记录

`ChatMemoryRepository` 接口的 `findByConversationId()` 方法用于查询指定会话ID的聊天消息。

```java
public interface ChatMemoryRepository {
    List<Message> findByConversationId(String conversationId);
}
```

具体的对应实现：
```java

```

