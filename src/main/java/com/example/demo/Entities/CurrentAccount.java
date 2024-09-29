package com.example.demo.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@Inheritance
@DiscriminatorValue("CUS")
@NoArgsConstructor
@AllArgsConstructor

// single table in account alors il va pas cree une table SavingAccount mais il va remplire la colonne type par la valeu "CUR"

public class CurrentAccount extends BankAccount{

    private Double overDraft;

    public void setOverDraft(Double overDraft) {
        this.overDraft = overDraft;
    }

    public Double getOverDraft() {
        return overDraft;
    }

    //quand on a l'heritage on a trois strategies:
        // single table : une table pour toute la hierarchie avec une colonne type : si il s'agit de compte eparge ou compte current : plus rapide recherche sur meme table l'inconvenient pour chaque ligne il y'a a NULL (quand currentt -> eparge :Null et vice versa), coté objet y 'a trois class coté relationnel on une seule table
        // table per class : (2 tables) table pour compte courant et une autre table pour compte eparge : beaucoup de colonne que se rassemble et qlqs autres different( oon utuluse quand y a beaucoup de difference de colonne entre les tables)
        // joined table : (3 tables) jointure chaque fois je checreh un compte bancaire





}
