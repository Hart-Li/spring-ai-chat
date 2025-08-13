## 基于 JDBC 的多轮对话实现

---

### 1. 基于 JDBC 的原因

**对话记忆**的存储库默认使用内存（`InMemoryChatMemoryRepository`），数据容易丢失。

```java
    // 创建特定的 ChatMemory实例
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(new InMemoryChatMemoryRepository())  // 对话记忆默认使用内存存储库
            .maxMessages(3)  // 保留最近的 3 条历史记录
            .build();
    }
```

上一节中最后初始化特定的 ChatMemory 实例时，使用的是内存作为存储库 `new InMemoryChatMemoryRepository()`。

---

### 2. ChatMemoryRepository

`ChatMemoryRepository` 接口是对话记忆存储的抽象，支持多种存储方式，例如：内存、`JDBC`、`Redis`等等，每种存储方式都有特定的实现类。

```java
public interface ChatMemoryRepository {
    // 获取所有活跃会话ID的列表
    List<String> findConversationIds();
    // 按会话ID查询消息列表（返回最新消息集合）
    List<Message> findByConversationId(String conversationId);
    // 全量替换存储，清空旧消息，写入新消息列表（实现需保证原子性）
    void saveAll(String conversationId, List<Message> messages);
    // 按会话ID删除所有关联消息
    void deleteByConversationId(String conversationId);
}
```

---

### 3. JdbcChatMemoryRepository

`JdbcChatMemoryRepository` 是使用 `JDBC` 存储对话记忆的实现类。它包含在 `spring-ai-starter-model-chat-memory-repository-jdbc` 模块中。

引入相关 maven 依赖：
```
        <!-- 引入 hat-memory-repository-jdbc -->
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-model-chat-memory-repository-jdbc</artifactId>
            <version>1.0.1</version>
        </dependency>
        <!-- mysql驱动 -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.1.0</version>
        </dependency>
```

使用：

```java
    // 创建基于 JDBC 存储对话记忆的 ChatMemory 实例
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(jdbcChatMemoryRepository)  // 对话记忆使用基于 JDBC 的存储库
            .maxMessages(10)  // 保留最近的 10 条历史记录
            .build();
    }
    // PS: SpringAI 为 JDBC 存储提供了自动配置，可以直接使用。
```