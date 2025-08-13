package com.study.chat.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc Redis 配置
 * @date 2025-08-13 20:50
 */
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
