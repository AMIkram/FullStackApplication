package com.example.demo.Services;
import com.example.demo.DTOs.*;
import com.example.demo.Exceptions.BalanceNotSufficientException;
import com.example.demo.Exceptions.BankAccountNotFoundException;
import com.example.demo.Exceptions.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    CustomerDto saveCustomer(CustomerDto customer);
    CurrentBankAccountDto saveCurrentBankAccount(double initialBalanace, double overDraft, Long CustomerId) throws CustomerNotFoundException;
    SavingBankAccountDto saveSavingBankAccount(double initialBalanace, double interestRate, Long CustomerId) throws CustomerNotFoundException;
    List<CustomerDto> listOfCustomers();
    BankAccountDTO getBankAccount(String accountId) throws CustomerNotFoundException, BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();

    CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException;
    Optional<List<CustomerDto>> searchCustomer(String name,Long id) throws CustomerNotFoundException;
    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDto updateCustomer(CustomerDto customerDto);

    List<AccountOperationDto> accountOperationHoistory(String accountId);

    AccountHistoryDTO getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException;
}
