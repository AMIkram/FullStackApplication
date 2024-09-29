package com.example.demo.Mappers;

import com.example.demo.DTOs.AccountOperationDto;
import com.example.demo.DTOs.CurrentBankAccountDto;
import com.example.demo.DTOs.CustomerDto;
import com.example.demo.DTOs.SavingBankAccountDto;
import com.example.demo.Entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDto fromCustomerToCustomerDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }


    public Customer fromCustomeDTOToCustomer(CustomerDto customerDto){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }


    public SavingBankAccountDto savingAccountToSavingAccountDTO(SavingAccount savingAccount){
        SavingBankAccountDto savingAccountDto=new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount,savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomerToCustomerDTO(savingAccount.getCustomer()));
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDto;
    }

    public SavingAccount savingAccountDtoToSavingAccount (SavingBankAccountDto savingBankAccountDto){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDto,savingAccount);
        savingAccount.setCustomer(fromCustomeDTOToCustomer(savingBankAccountDto.getCustomerDto()));
        return  savingAccount;
    }

    public CurrentBankAccountDto currentBankAccountToCurretnAccountDto(CurrentAccount currentAccount){
        CurrentBankAccountDto currentBankAccountDto = new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDto);
        currentBankAccountDto.setCustomerDto(fromCustomerToCustomerDTO(currentAccount.getCustomer()));
        currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDto;
    }

    public CurrentAccount currentBankAccountDTOtoAccount (CurrentBankAccountDto currentBankAccountDto){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
        currentAccount.setCustomer(fromCustomeDTOToCustomer(currentBankAccountDto.getCustomerDto()));
        return  currentAccount;
    }

    public AccountOperationDto toAccountOperationDto (AccountOperation accountOperation){
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation,accountOperationDto);
        return accountOperationDto;
    }

    public AccountOperation toAccountOperation (AccountOperationDto accountOperationDto){
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDto,accountOperation);
        return accountOperation;
    }


}
