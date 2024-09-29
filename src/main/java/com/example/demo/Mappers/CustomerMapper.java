package com.example.demo.Mappers;

import com.example.demo.DTOs.CustomerDto;
import com.example.demo.Entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public CustomerDto customerTocustomerDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
}
