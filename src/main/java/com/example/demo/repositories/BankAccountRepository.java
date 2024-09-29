package com.example.demo.repositories;

import com.example.demo.Entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount,String>{


    Optional<Object> findAllById(String uuid);
}
