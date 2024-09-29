package com.example.demo.DTOs;

import com.example.demo.enums.OperationType;
import lombok.Data;

import java.util.Date;
@Data
public class DebitDto {
    private String id;
    private double amount;
    private String Description;

}
