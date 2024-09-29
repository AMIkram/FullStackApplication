package com.example.demo.DTOs;

import com.example.demo.enums.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
@Data
public class SavingBankAccountDto extends BankAccountDTO {
    private String id;
    private double solde;
    private Date createdAt;
    private AccountStatus status;
    private Double balance ;
    private Double interestRate;
    private CustomerDto customerDto;
}
