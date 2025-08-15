package com.study.chat.model;

import java.util.List;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-08-15 11:39
 */
// 存储多个手机Record的List集合
public record PhoneRecordList(List<PhoneRecord> phoneRecords) {
}
