package com.example.demo.DTOs;

import com.example.demo.Entities.BankAccount;
import com.example.demo.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id;
    private Date operationDate;
    private double amount;
    private String Description;
    private OperationType operationType;


}
