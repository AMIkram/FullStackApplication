package com.example.demo.DTOs;

import com.example.demo.Entities.AccountOperation;
import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDto> accountOperationListDTO;
}
