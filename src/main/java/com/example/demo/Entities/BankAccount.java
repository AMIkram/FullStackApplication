package com.example.demo.Entities;

import com.example.demo.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="type", length = 4 , discriminatorType = DiscriminatorType.STRING)

//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
// + changer BankAccount en abstract
public abstract class BankAccount {
    @Id
    private String id;
    private double solde;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private Double balance ;
    private String description;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch =FetchType.LAZY)
    private List<AccountOperation> accountOperationList;

    public List<AccountOperation> getAccountOperationList() {
        return accountOperationList;
    }

    public void setAccountOperationList(List<AccountOperation> accountOperationList) {
        this.accountOperationList = accountOperationList;
    }



    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
