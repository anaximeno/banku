package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class TemporaryParticularAccount extends Account {
    private final LocalDate creationDate;
    private LocalDate expirationDate;
    private boolean boost;

    public TemporaryParticularAccount(String accountNumber, String accountName, Double balance,
        Double interestRate, ParticularAccountOwner owner, LocalDate creationDate, LocalDate expirationDate) {
        super(accountNumber, accountName, owner, balance, interestRate);
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.boost = false;
    }

    public TemporaryParticularAccount(String accountNumber, String accountName, Double balance,
          Double interestRate, ParticularAccountOwner owner, LocalDate creationDate, LocalDate expirationDate, boolean boost) {
        this(accountNumber, accountName, balance, interestRate, owner, creationDate, expirationDate);
        this.boost = boost;
    }

    //function that confirms if an TemporaryParticularAccount has the possibility to do operations
    public boolean hasBoost() {
        return boost;
    }

    public void activateBoost() {
        this.boost = true;
    }

    public void deactivateBoost() {
        this.boost = false;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Period getNumberOfDaysToExpiration() {
        return Period.between(creationDate, expirationDate);
    }
}
