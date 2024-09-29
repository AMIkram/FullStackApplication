package com.example.demo.repositories;

import com.example.demo.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    @Query("SELECT c from Customer c where (:name is null or c.name LIKE :name) and(:id is null or c.id = :id)")
    Optional<List<Customer>> findByNameOrId(@Param("name") String name, @Param("id") Long id);

// when we tolerate to have one of the attribute to be null so we need to ass the is null in the query
}
