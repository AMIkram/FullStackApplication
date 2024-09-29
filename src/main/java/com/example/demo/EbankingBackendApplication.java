package com.example.demo;

import com.example.demo.DTOs.BankAccountDTO;
import com.example.demo.DTOs.CurrentBankAccountDto;
import com.example.demo.DTOs.CustomerDto;
import com.example.demo.DTOs.SavingBankAccountDto;
import com.example.demo.Entities.*;
import com.example.demo.Exceptions.BalanceNotSufficientException;
import com.example.demo.Exceptions.BankAccountNotFoundException;
import com.example.demo.Exceptions.CustomerNotFoundException;
import com.example.demo.Services.BankAccountService;
import com.example.demo.enums.AccountStatus;
import com.example.demo.enums.OperationType;
import com.example.demo.repositories.AccountOperationRepository;
import com.example.demo.repositories.BankAccountRepository;
import com.example.demo.repositories.CustomerRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandeLineRunner(BankAccountService bankAccountService){
		return args -> {
			Stream.of("Hassan","Imane","Mohammed").forEach(name-> {
						CustomerDto customer = new CustomerDto();
						customer.setName(name);
						customer.setEmail(name+"@gmail.com");
						bankAccountService.saveCustomer(customer);
					});

			bankAccountService.listOfCustomers().forEach(customer ->{
                        try {
							bankAccountService.saveCurrentBankAccount(Math.random() * 9000, 900, customer.getId());
							bankAccountService.saveSavingBankAccount(Math.random() * 9000, 5.5, customer.getId());
							List<BankAccountDTO> bankAccountList = bankAccountService.bankAccountList();
							for (BankAccountDTO bankAccount : bankAccountList) {
								for(int i =0; i<10 ; i++) {
									String accountId;
									if(bankAccount instanceof SavingBankAccountDto){
										accountId=((SavingBankAccountDto)bankAccount).getId();
									}
									else{
										accountId=((CurrentBankAccountDto)bankAccount).getId();
									}
									System.out.println(accountId);

									bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "Credit");
									bankAccountService.debit(accountId, 1000+Math.random()*9000,"debit");
								}};}
						 catch (CustomerNotFoundException e) {
                            e.printStackTrace();
                        }
						catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                            e.printStackTrace();
                        }});
//			bankAccountService.bankAccountList().forEach((bankAccount -> {
//				String accountId = "";
//				if(bankAccount instanceof  CurrentBankAccountDto){
//					 accountId = ((CurrentBankAccountDto)bankAccount).getId();
//				}
//				else if(bankAccount instanceof  SavingBankAccountDto){
//					accountId = ((SavingBankAccountDto)bankAccount).getId();
//				}
//                try {
//                    bankAccountService.credit(accountId,1000+Math.random()*12000,"credit");
//
//				} catch (BankAccountNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    bankAccountService.debit(accountId,1000+Math.random()*12000,"debit");
//                } catch (BankAccountNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (BalanceNotSufficientException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }));
		};




	}




	//@Bean
	@Transactional
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("Hassan", "Yassin", "Ali").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name.toLowerCase() + "@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(cust -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000.0);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);
			});

			bankAccountRepository.findAll().forEach(acc -> {
				for (int i = 0; i < 5; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random() * 12000);
					accountOperation.setOperationType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}
			});

			BankAccount bankAccount = bankAccountRepository.findById("6886c6ae-26f6-4ebd-956a-db6417ee2817").orElse(null);
			if (bankAccount != null) {
				System.out.println("*************************************");
				System.out.println("Account ID: " + bankAccount.getId());
				System.out.println("Customer Name: " + bankAccount.getCustomer().getName());
				System.out.println("Account Status: " + bankAccount.getStatus());
				System.out.println("Account Type: " + bankAccount.getClass().getSimpleName());
				if (bankAccount instanceof SavingAccount) {
					System.out.println("Interest Rate: " + ((SavingAccount) bankAccount).getInterestRate());
				} else if (bankAccount instanceof CurrentAccount) {
					System.out.println("Overdraft: " + ((CurrentAccount) bankAccount).getOverDraft());
				}

				bankAccount.getAccountOperationList().forEach(op -> {
					System.out.println("---------------------");
					System.out.println("Operation Type: " + op.getOperationType());
					System.out.println("Operation Date: " + op.getOperationDate());
					System.out.println("Amount: " + op.getAmount());
				});
			} else {
				System.out.println("Bank account not found.");
			}
		};
	}
}