package com.study.chat.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc 自定义时间工具
 * @date 2025-08-16 00:28
 */
@Component
public class DateTimeTools {

    @Tool(name = "current_datetime", description = "获取用户所在时区的当前日期和时间")
    String getCurrentDateTime() {
        System.out.println("获取当前时间的工具被调用");
        return LocalDateTime.now().toString();
    }
    // @Component 可选，未添加则使用 new 方式注入工具
    // @Tool(name="工具名，未提供则使用方法名，类中唯一及特定聊天的工具库中唯一")
    // @Tool(description="工具描述，未提供则使用方法名，强烈建议提供详细的描述")

    @Tool(description = "为用户设置闹钟，时间以 ISO-8601 格式提供")
    void setAlarm(String time) {
        // time: 2025-08-16T00:40:00
        // 也可省略 DateTimeFormatter.ISO_DATE_TIME，默认就是它
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        // 异常处理
        if (alarmTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("闹钟时间不能是过去时间");
        }
        System.out.println("闹钟设置在：" + alarmTime);
    }
}
