package com.example.demo.Entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue("SAC")
@Data
@AllArgsConstructor
@NoArgsConstructor
// single table in account alors il va pas cree une table SavingAccount mais il va remplire la colonne type par la valeu "CUR"
public class SavingAccount extends BankAccount{
    private Double interestRate;

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
