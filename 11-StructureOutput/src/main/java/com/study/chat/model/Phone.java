package com.study.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-11-25 11:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    private String brand;
    private String model;
    private String cpu;
    private String ram;
    private String rom;
    private String screen;
    private String battery;
}
