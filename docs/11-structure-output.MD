### 结构化输出

---

### 1. 为什么需要结构化输出

AI 模型生成的响应通常为“**自由文本格式**”，这给“**下游应用程序**”的处理和集成带来了不便。如果直接将该文本传递给前端展示或者写入数据库，需要额外的解析逻辑，容易出错。

![为什么需要结构化输出](images/11/why-need-structure-output.png)

---

### 2. 结构化数据

`Spring AI` 支持将模型的输出转化为“**结构化的数据**”格式。

准备实体类：

1. 使用 lombok 的注解。

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    private String brand;
    private String model;
    private String cpu;
    private String ram;
    private String rom;
    private String screen;
    private String battery;
}
```

2. 使用 record 关键字（最早 jdk14 支持，jdk16 才确定下来）

> PS: record 关键字会自动帮助生成无参构造、全参构造、getter、setter、hashCode、equals、toString 方法。


```java
public record PhoneRecord(String brand, String model, String cpu, String ram, String rom,
                          String screen, String battery) {
}
```

---

### 3. 结构化输入核心 API

#### 3.1 StructuredOutputConverter

`StructuredOutputConverter` 是结构化输出的核心接口，该接口继承了 `Converter` 和 `FormatProvider` 接口，确保在调用模型 **之前和之后** 处理输入和输出。

```java
public interface StructuredOutputConverter<T> extends Converter<String, T>, FormatProvider {
    // 调用模型之前，FormatProvider 接口为模型提供特定的格式设置准则
    // 调用模型之后，Converter 接口负责将模型生成的输出转换为指定的目标类型
}
```

```java
FormatProvider 的“设置准则”举例：
    Your response should be in JSON format.
    您的回复应采用 JSON 格式。
    
```

