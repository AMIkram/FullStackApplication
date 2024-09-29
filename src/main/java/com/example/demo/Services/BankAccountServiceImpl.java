package com.example.demo.Services;

import com.example.demo.DTOs.*;
import com.example.demo.Entities.*;
import com.example.demo.Exceptions.BalanceNotSufficientException;
import com.example.demo.Exceptions.BankAccountNotFoundException;
import com.example.demo.Exceptions.CustomerNotFoundException;
import com.example.demo.Mappers.BankAccountMapperImpl;
import com.example.demo.enums.OperationType;
import com.example.demo.repositories.AccountOperationRepository;
import com.example.demo.repositories.BankAccountRepository;
import com.example.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
//transactionall : chaque methode est une transaction la une fois il constate l exception il fait ROLLBACK sinon il fait COMMIT
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    // autowired c'est traditionnel , la meilleure façon pour l'injection des dependances est : constructeur ou @AllArgsConstructor
    //@Autowired
    private CustomerRepository customerRepository;
    //@Autowired
    private BankAccountRepository bankAccountRepository;
    //@Autowired
    private AccountOperationRepository accountOperationRepository;
    //instantiation du logger est fait par log
    //Logger log= LoggerFactory.getLogger(this.getClass().getName());
    private BankAccountMapperImpl bankAccountMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        log.info("Saving new Customer");
        Customer customer = bankAccountMapper.fromCustomeDTOToCustomer(customerDto);
        Customer customerDtoToSave = customerRepository.save(customer);
        CustomerDto customerToSave=bankAccountMapper.fromCustomerToCustomerDTO(customerDtoToSave);
        return customerToSave;
    }

    @Override
    public CurrentBankAccountDto saveCurrentBankAccount(double initialBalanace, double overDraft, Long customerId) throws CustomerNotFoundException {
        CurrentAccount bankAccount= new CurrentAccount() ;
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw  new CustomerNotFoundException(("CustomerNot found"));
        }
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalanace);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setCustomer(customer);
        bankAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentBankAccount= bankAccountRepository.save(bankAccount);
        return bankAccountMapper.currentBankAccountToCurretnAccountDto(savedCurrentBankAccount);

    }

    @Override
    public SavingBankAccountDto saveSavingBankAccount(double initialBalanace, double interestRate, Long customerId) throws CustomerNotFoundException {
        SavingAccount bankAccount= new SavingAccount() ;
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw  new CustomerNotFoundException(("CustomerNot found"));
        }
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalanace);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setCustomer(customer);
        bankAccount.setInterestRate(interestRate);
        SavingAccount savedSavingBankAccount= bankAccountRepository.save(bankAccount);
        return bankAccountMapper.savingAccountToSavingAccountDTO(savedSavingBankAccount);
    }


    @Override
    public List<CustomerDto> listOfCustomers() {
        List<Customer> customers =customerRepository.findAll();
        List<CustomerDto> customerDtos=customers.stream()
                .map(bankAccountMapper::fromCustomerToCustomerDTO)
                .collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        BankAccountDTO bankAccountDTO= new BankAccountDTO();
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount=(SavingAccount)bankAccount;
            bankAccountDTO =bankAccountMapper.savingAccountToSavingAccountDTO(savingAccount);
        }
        else if (bankAccount instanceof CurrentAccount) {
            CurrentAccount currentAccount= (CurrentAccount)bankAccount;
            bankAccountDTO =bankAccountMapper.currentBankAccountToCurretnAccountDto(currentAccount);
        }
        return bankAccountDTO ;}

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientException("balance not sufficient");
        }
        AccountOperation accountOperation=new AccountOperation();

        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        log.info("debit");
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        log.info("credit");

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"transfer from"+accountIdSource);
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccountDTO>bankAccount=bankAccountRepository.findAll().stream().map(bankAc -> {
            if(bankAc instanceof  SavingAccount){
                SavingAccount savingAccount=(SavingAccount) bankAc;
                return  bankAccountMapper.savingAccountToSavingAccountDTO(savingAccount);
            }
            else{
                CurrentAccount currentAccount= (CurrentAccount) bankAc;
                return bankAccountMapper.currentBankAccountToCurretnAccountDto(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccount;
    }

    @Override
    public CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException {
        CustomerDto customerDto =customerRepository.findById(customerId).map(bankAccountMapper::fromCustomerToCustomerDTO).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        return customerDto;}

    @Override
    public Optional<List<CustomerDto>> searchCustomer( String name,Long id) throws CustomerNotFoundException {
        if(name != null){name= "%" + name + "%";
            System.out.println(name);}
        if(id != null){System.out.println(id);}

        Optional<List<Customer>> customers = customerRepository.findByNameOrId(name, id);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        if (customers.isPresent() && !customers.get().isEmpty()) {
            for (Customer customer : customers.get()) {
                customerDtoList.add(bankAccountMapper.fromCustomerToCustomerDTO(customer));
            }
            return Optional.of(customerDtoList);
        } else {
            throw new CustomerNotFoundException("No customer found");
        }
    }


    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        log.info("update Customer");
        Customer customer = bankAccountMapper.fromCustomeDTOToCustomer(customerDto);
        Customer customerDtoToSave = customerRepository.save(customer);
        CustomerDto customerToSave=bankAccountMapper.fromCustomerToCustomerDTO(customerDtoToSave);
        return customerToSave;
    }

    @Override
    public List<AccountOperationDto> accountOperationHoistory(String accountId){
        List<AccountOperationDto> accountOperationListDto =accountOperationRepository.findByBankAccountId(accountId).stream().map(bankAccountMapper::toAccountOperationDto).collect(Collectors.toList());
        return accountOperationListDto;

    }

    @Override
    public AccountHistoryDTO getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(id).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        Page<AccountOperation> accountOperationPage = accountOperationRepository.findByBankAccountId(id, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO= new AccountHistoryDTO();
        accountHistoryDTO.setAccountId(id);
        List<AccountOperationDto> accountOperationListDTO =accountOperationPage.getContent().stream().map((bankAccountMapper::toAccountOperationDto)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationListDTO(accountOperationListDTO);
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperationPage.getTotalPages());
        return accountHistoryDTO;
    }
}
