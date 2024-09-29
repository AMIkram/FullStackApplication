package com.example.demo.Controller;

import com.example.demo.DTOs.CustomerDto;
import com.example.demo.Exceptions.CustomerNotFoundException;
import com.example.demo.Services.BankAccountService;
import com.example.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerController {
    private BankAccountService bankAccountService;
    private CustomerRepository customerRepository;
    @GetMapping("/customers")
    public List<CustomerDto> customers(){
        log.info("Saving new Customer");

        return bankAccountService.listOfCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable(name= "id") Long customerId) throws CustomerNotFoundException {
       return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/addCustomer")
    public CustomerDto addCustomer(@RequestBody CustomerDto cust){
        return bankAccountService.saveCustomer(cust);
    }

    @DeleteMapping("deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable (name= "id")Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }


    @PutMapping("updateCustomer/{id}")
    public void UpdateCustomer(@PathVariable (name= "id")Long customerId,@RequestBody CustomerDto customerDto)  throws CustomerNotFoundException {
        customerDto.setId(customerId);
        bankAccountService.updateCustomer(customerDto);
    }

    @GetMapping("searchCustomer")
    public Optional<List<CustomerDto>> searchCustomer(@RequestParam (required = false)String name, @RequestParam (required = false) Long id) throws CustomerNotFoundException {
        return bankAccountService.searchCustomer(name, id);}

}

