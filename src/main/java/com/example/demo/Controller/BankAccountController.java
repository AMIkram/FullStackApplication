package com.example.demo.Controller;

import com.example.demo.DTOs.*;
import com.example.demo.Exceptions.BalanceNotSufficientException;
import com.example.demo.Exceptions.BankAccountNotFoundException;
import com.example.demo.Exceptions.CustomerNotFoundException;
import com.example.demo.Services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountController {
    private BankAccountService bankAccountService;
    @GetMapping("/bankAccount/{id}")
    public BankAccountDTO getBankAccountById(@PathVariable String id) throws BankAccountNotFoundException, CustomerNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/bankAccounts")
    private List<BankAccountDTO> getBankAccount(){
        return  bankAccountService.bankAccountList();
    }

    @GetMapping("/account/{id}/operations")
    private List<AccountOperationDto> getBankAccount(@PathVariable String id){
        return  bankAccountService.accountOperationHoistory(id);
    }

    @GetMapping("/account/{id}/pageOperations")
    //RequestParam is the best for passing optional parameters like filtering or searching or in paginatiopn for example
    //RequestParam : parametres in the URL are like : localhost:8085/url../page?id=1&page=0&Size=3
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String id,
            @RequestParam(name="page" ,defaultValue ="0" )int page ,
            @RequestParam(name="size", defaultValue =  "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(id,page,size);

    }


    @PostMapping("/account/Debit")
    //RequestParam is the best for passing optional parameters like filtering or searching or in paginatiopn for example
    //RequestParam : parametres in the URL are like : localhost:8085/url../page?id=1&page=0&Size=3
    public void debit(@RequestBody DebitDto debitDto) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.debit(debitDto.getId(), debitDto.getAmount(), debitDto.getDescription());
    }


    @PostMapping("/account/Credit")
    //RequestParam is the best for passing optional parameters like filtering or searching or in paginatiopn for example
    //RequestParam : parametres in the URL are like : localhost:8085/url../page?id=1&page=0&Size=3
    public void credit(@RequestBody CreditDto creditDto) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.credit(creditDto.getId(), creditDto.getAmount(), creditDto.getDescription());
    }

    @PostMapping("/account/Transfer")
    //RequestParam is the best for passing optional parameters like filtering or searching or in paginatiopn for example
    //RequestParam : parametres in the URL are like : localhost:8085/url../page?id=1&page=0&Size=3
    public void transfer(@RequestBody TransferDto transferDto) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.transfer(transferDto.getAccountSource(), transferDto.getAccountDestination(), transferDto.getAmount());

    }


}
