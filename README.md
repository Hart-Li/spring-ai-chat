# Spring AI Chat 学习项目

## 📖 项目简介

Spring AI Chat 是一个专为**小白用户**设计的 Spring AI 学习项目，基于 Spring Boot 3.5.4 和 Spring AI 1.0.0 构建。本项目通过实际案例演示，帮助初学者从零开始学习 Spring AI 的各种功能，包括文本对话、图像生成、语音合成、视频生成等多媒体AI能力。

### 🎯 学习目标
- 掌握 Spring AI 基础概念和使用方法
- 学会集成多种AI模型（DeepSeek、智谱AI、Ollama等）
- 理解AI工具调用和对话记忆机制
- 实践多媒体AI应用开发
- 从简单到复杂，循序渐进地学习AI应用开发

## 📚 学习文档导航

本项目包含**23个详细的学习文档**，位于 `docs/` 目录下，按照学习难度递进排列。每个文档都包含完整的代码示例、运行结果和详细说明。

### 🌱 基础入门篇

#### [1. 环境准备 - docs/1-Prepare.MD](docs/1-Prepare.MD)
- Spring AI 开发环境搭建
- DeepSeek API 申请和配置
- 项目依赖引入和基础配置
- 第一个 Spring AI 应用运行

#### [2. 聊天模型详解 - docs/2-ChatModel.MD](docs/2-ChatModel.MD)
- ChatModel 接口和工作原理
- 支持的AI模型对比
- 基础对话功能实现
- 流式响应处理

#### [3. 提示词使用 - docs/3-Prompt.MD](docs/3-Prompt.MD)
- 提示词结构和最佳实践
- 角色设定和上下文管理
- 提示词优化技巧
- 实际应用案例

#### [4. 提示词模板 - docs/4-PromptTemplate.MD](docs/4-PromptTemplate.MD)
- 模板化提示词处理
- 动态参数替换
- 资源数据集成
- 模板复用和管理

### 🔧 功能进阶篇

#### [5. 多轮对话记忆 - docs/5-multi-chat-memory.MD](docs/5-multi-chat-memory.MD)
- 对话记忆机制原理
- 内存存储实现
- 对话上下文管理
- 记忆窗口配置

#### [6. MySQL存储对话 - docs/6-multi-chat-jdbc.MD](docs/6-multi-chat-jdbc.MD)
- JDBC 对话记忆存储
- 数据库表结构设计
- 持久化对话历史
- 多用户会话管理

#### [7. Redis存储对话 - docs/7-multi-chat-redis.MD](docs/7-multi-chat-redis.MD)
- Redis 对话记忆存储
- 缓存策略优化
- 会话数据序列化
- 性能调优实践

#### [8. 顾问模式 - docs/8-advisor.MD](docs/8-advisor.MD)
- Advisor 机制原理
- 自定义顾问实现
- 请求响应拦截
- 业务逻辑增强

#### [9. 提示词记忆顾问 - docs/9-PromptChatMemoryAdvisor.MD](docs/9-PromptChatMemoryAdvisor.MD)
- 提示词与记忆结合
- 动态上下文构建
- 智能对话管理
- 用户体验优化

#### [10. 安全顾问 - docs/10-safe-advisor.MD](docs/10-safe-advisor.MD)
- 内容安全检测
- 敏感词过滤
- 安全响应策略
- 合规性保障

#### [11. 结构化输出 - docs/11-structure-output.MD](docs/11-structure-output.MD)
- JSON 结构化输出
- 数据格式定义
- 输出验证处理
- 业务数据集成

#### [12. 多模型支持 - docs/12-multi-model.MD](docs/12-multi-model.MD)
- 多AI模型配置
- 模型切换策略
- 负载均衡实现
- 成本优化方案

#### [13. 工具调用 - docs/13-tool-call.MD](docs/13-tool-call.MD)
- 工具调用机制
- 自定义工具开发
- 工具注册和发现
- 调用链管理

#### [14. 天气工具实战 - docs/14-tool-get_real-weather.MD](docs/14-tool-get_real-weather.MD)
- 高德地图API集成
- 天气查询工具实现
- 工具调用最佳实践
- 错误处理机制

### 🎨 多媒体AI篇

#### [15. 文本生成图像 - docs/15-text-gen-image.MD](docs/15-text-gen-image.MD)
- 智谱AI图像生成
- 提示词优化技巧
- 图像参数配置
- 生成结果处理

#### [16. 图像转文本 - docs/16-image-to-text.MD](docs/16-image-to-text.MD)
- 图像内容识别
- 多模态模型使用
- 图像描述生成
- 识别精度优化

#### [17. 文本转语音 - docs/17-text-to-speech.MD](docs/17-text-to-speech.MD)
- 通义千问TTS集成
- 语音合成参数
- 音频文件处理
- 多语言支持

#### [18. 语音识别 - docs/18-asr.MD](docs/18-asr.MD)
- 语音转文本识别
- 音频格式处理
- 识别准确率优化
- 实时识别实现

#### [19. 声音克隆 - docs/19-audio-clone.MD](docs/19-audio-clone.MD)
- AI声音克隆技术
- 声音模型训练
- 克隆音频生成
- 音质优化处理

#### [20. 实时语音识别 - docs/20-realtime-speech-recognition.MD](docs/20-realtime-speech-recognition.MD)
- 实时语音流处理
- WebSocket 通信
- 流式识别优化
- 延迟控制策略

#### [21. 文本生成视频 - docs/21-text-to-video.MD](docs/21-text-to-video.MD)
- 智谱AI视频生成
- 视频参数配置
- 异步任务处理
- 生成进度监控

#### [22. 图像转视频 - docs/22-image-to-video.MD](docs/22-image-to-video.MD)
- 万兴AI图像转视频
- 视频帧处理
- 转换效果优化
- 输出格式控制

### 🏠 本地部署篇

#### [23. 本地模型部署 - docs/23-local-model.MD](docs/23-local-model.MD)
- Ollama 本地模型
- 模型下载和配置
- 本地推理优化
- 离线AI应用

## 📁 项目目录结构详解

```
spring-ai-chat/
├── 📚 docs/                          # 学习文档目录
│   ├── 📖 1-Prepare.MD               # 环境准备文档
│   ├── 📖 2-ChatModel.MD             # 聊天模型详解
│   ├── 📖 3-Prompt.MD                # 提示词使用
│   ├── 📖 4-PromptTemplate.MD        # 提示词模板
│   ├── 📖 5-multi-chat-memory.MD     # 多轮对话记忆
│   ├── 📖 6-multi-chat-jdbc.MD       # MySQL存储对话
│   ├── 📖 7-multi-chat-redis.MD      # Redis存储对话
│   ├── 📖 8-advisor.MD               # 顾问模式
│   ├── 📖 9-PromptChatMemoryAdvisor.MD # 提示词记忆顾问
│   ├── 📖 10-safe-advisor.MD         # 安全顾问
│   ├── 📖 11-structure-output.MD     # 结构化输出
│   ├── 📖 12-multi-model.MD          # 多模型支持
│   ├── 📖 13-tool-call.MD            # 工具调用
│   ├── 📖 14-tool-get_real-weather.MD # 天气工具实战
│   ├── 📖 15-text-gen-image.MD       # 文本生成图像
│   ├── 📖 16-image-to-text.MD        # 图像转文本
│   ├── 📖 17-text-to-speech.MD       # 文本转语音
│   ├── 📖 18-asr.MD                  # 语音识别
│   ├── 📖 19-audio-clone.MD          # 声音克隆
│   ├── 📖 20-realtime-speech-recognition.MD # 实时语音识别
│   ├── 📖 21-text-to-video.MD        # 文本生成视频
│   ├── 📖 22-image-to-video.MD       # 图像转视频
│   ├── 📖 23-local-model.MD          # 本地模型部署
│   └── 📸 materials/                 # 文档配套材料
│       ├── 📁 1/                     # 环境准备截图
│       ├── 📁 2/                     # 聊天模型示例
│       ├── 📁 3/                     # 提示词示例
│       └── ...                       # 其他文档材料
│
├── 💻 src/main/java/com/study/chat/
│   ├── ⚙️ config/                    # 配置类
│   │   ├── SpringAIConfig.java       # Spring AI 主配置
│   │   ├── ZhipuAIConfig.java        # 智谱AI 配置
│   │   ├── RedisConfig.java          # Redis 配置
│   │   ├── RedisChatMemoryRepository.java # Redis 对话记忆
│   │   └── SerializableMessage.java  # 消息序列化
│   │
│   ├── 🎮 controller/                # 控制器层
│   │   ├── ChatController.java       # 聊天控制器
│   │   ├── ImageGenerationController.java # 图像生成
│   │   ├── AudioGenerationController.java # 音频生成
│   │   ├── VideoGenerationController.java # 视频生成
│   │   ├── AudioCloneController.java # 声音克隆
│   │   ├── AudioDetectionController.java # 音频检测
│   │   ├── ImageDetectionController.java # 图像检测
│   │   ├── RealTimeSpeechRecognitionController.java # 实时语音识别
│   │   └── OllamaController.java     # Ollama 本地模型
│   │
│   ├── 🔧 tools/                     # 工具类
│   │   ├── DateTimeTools.java        # 日期时间工具
│   │   └── WeatherTools.java         # 天气查询工具
│   │
│   ├── 🛠️ utils/                     # 工具类
│   │   ├── QwenTTS.java              # 通义千问TTS
│   │   ├── QwenASR.java              # 通义千问ASR
│   │   ├── VoiceCloneTools.java      # 声音克隆工具
│   │   ├── RealTimeSpeechRecognitionTools.java # 实时语音识别
│   │   └── WanxI2V.java              # 万兴AI图像转视频
│   │
│   ├── 📊 model/                     # 数据模型
│   │   ├── Phone.java                # 电话模型
│   │   ├── PhoneRecord.java          # 电话记录
│   │   ├── PhoneRecordList.java      # 电话记录列表
│   │   ├── PhoneRecordMap.java       # 电话记录映射
│   │   └── VideoGenerationRequest.java # 视频生成请求
│   │
│   ├── 🔄 service/                   # 服务层
│   │   └── ZhipuAIVideoService.java  # 智谱AI视频服务
│   │
│   ├── 📦 common/                    # 公共类
│   │   └── VoiceUrl.java             # 语音URL
│   │
│   └── 🚀 SpringAiChatApplication.java # 应用启动类
│
├── 📄 src/main/resources/
│   ├── ⚙️ application.yml            # 应用配置文件
│   ├── 🗄️ schema/                    # 数据库脚本
│   │   └── mysql.sql                 # MySQL 建表脚本
│   ├── 📝 prompts/                   # 提示词模板
│   │   └── system-message.st         # 系统消息模板
│   ├── 🎵 audios/                    # 音频文件
│   │   ├── test.mp3                  # 测试音频
│   │   └── output/                   # 输出音频目录
│   │       └── result.mp3            # 生成结果音频
│   └── 🖼️ images/                    # 图像文件
│       ├── girl.png                  # 测试图像
│       ├── report.png                # 报告图像
│       ├── text.png                  # 文本图像
│       ├── cat_first_frame.png       # 猫第一帧
│       └── cat_last_frame.png        # 猫最后一帧
│
├── 📋 pom.xml                        # Maven 项目配置
└── 📖 README.md                      # 项目说明文档
```

## 🚀 快速开始

### 📋 学习前提
- 基础的 Java 编程知识
- 了解 Spring Boot 基本概念
- 对 AI 应用开发感兴趣

### 🛠 环境要求
- JDK 21+
- Maven 3.6+
- MySQL 8.0+（可选，用于对话记忆存储）
- Redis 6.0+（可选，用于缓存和会话管理）

### 📚 学习路径建议

1. **🌱 基础入门** (1-4天)
   - 第1天: [环境准备](docs/1-Prepare.MD) - 搭建开发环境
   - 第2天: [聊天模型详解](docs/2-ChatModel.MD) - 理解基础概念
   - 第3天: [提示词使用](docs/3-Prompt.MD) - 掌握提示词技巧
   - 第4天: [提示词模板](docs/4-PromptTemplate.MD) - 学习模板化处理

2. **🔧 功能进阶** (5-10天)
   - 第5天: [多轮对话记忆](docs/5-multi-chat-memory.MD) - 实现对话记忆
   - 第6天: [MySQL存储对话](docs/6-multi-chat-jdbc.MD) - 持久化存储
   - 第7天: [Redis存储对话](docs/7-multi-chat-redis.MD) - 缓存优化
   - 第8天: [工具调用](docs/13-tool-call.MD) - 学习工具机制
   - 第9天: [天气工具实战](docs/14-tool-get_real-weather.MD) - 实践工具开发
   - 第10天: [多模型支持](docs/12-multi-model.MD) - 多模型集成

3. **🎨 多媒体AI** (11-18天)
   - 第11天: [文本生成图像](docs/15-text-gen-image.MD) - 图像生成
   - 第12天: [图像转文本](docs/16-image-to-text.MD) - 图像识别
   - 第13天: [文本转语音](docs/17-text-to-speech.MD) - 语音合成
   - 第14天: [语音识别](docs/18-asr.MD) - 语音转文本
   - 第15天: [声音克隆](docs/19-audio-clone.MD) - AI声音克隆
   - 第16天: [实时语音识别](docs/20-realtime-speech-recognition.MD) - 实时处理
   - 第17天: [文本生成视频](docs/21-text-to-video.MD) - 视频生成
   - 第18天: [图像转视频](docs/22-image-to-video.MD) - 视频转换

4. **🏠 高级应用** (19-20天)
   - 第19天: [本地模型部署](docs/23-local-model.MD) - 离线AI应用
   - 第20天: 项目实战 - 综合应用开发

### 🛠 安装步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd spring-ai-chat
```

2. **配置数据库**
```sql
-- 创建数据库
CREATE DATABASE spring_ai_chat;
```

3. **配置应用参数**
编辑 `src/main/resources/application.yml` 文件，配置以下参数：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/spring_ai_chat
    username: your_username
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379

  ai:
    openai:
      api-key: your_deepseek_api_key
      base-url: https://api.deepseek.com
    zhipuai:
      api-key: your_zhipuai_api_key
    ollama:
      base-url: http://127.0.0.1:11434

weather:
  gaode:
    key: your_gaode_api_key
```

4. **运行应用**
```bash
mvn spring-boot:run
```

## 🎓 学习成果

通过本项目的学习，您将能够：
- ✅ 独立搭建 Spring AI 开发环境
- ✅ 掌握多种AI模型的集成方法
- ✅ 实现AI工具调用和对话记忆功能
- ✅ 开发多媒体AI应用（图像、音频、视频）
- ✅ 理解AI应用的安全性和性能优化
- ✅ 具备实际AI项目开发能力

## 💡 学习小贴士

- 🔍 **循序渐进**: 不要急于求成，按照文档顺序学习
- 🛠 **动手实践**: 每个示例都要亲自运行和调试
- 📝 **记录笔记**: 记录学习过程中的问题和解决方案
- 🤝 **交流分享**: 遇到问题可以在社区中寻求帮助
- 🎯 **项目实战**: 学完后尝试开发自己的AI应用

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 👨‍💻 作者

**HartLi** - 项目维护者

## 🙏 致谢

- Spring AI 团队提供的优秀框架和详细文档
- 各AI服务提供商的技术支持和免费额度
- 开源社区的贡献和分享
- 所有学习者的反馈和建议

---

⭐ 如果这个学习项目对您有帮助，请给个星标支持一下！也欢迎分享给更多想学习Spring AI的朋友！
