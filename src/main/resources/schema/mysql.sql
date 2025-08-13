CREATE TABLE spring_ai_chat.SPRING_AI_CHAT_MEMORY (
    conversation_id VARCHAR ( 36 ) NOT NULL DEFAULT '' COMMENT '会话ID',
    content LONGTEXT NOT NULL COMMENT '消息内容',
    type VARCHAR ( 10 ) NOT NULL DEFAULT '' COMMENT '消息类型',
    `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '消息时间戳',
    INDEX idx_conversation_id_timestamp ( conversation_id, `timestamp` DESC )
) COMMENT 'Spring AI 会话记忆表';