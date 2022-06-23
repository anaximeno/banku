package com.groupnine.banku;

import java.time.LocalDate;
import java.time.Period;
import java.lang.Math;

public class TemporaryParticularAccount extends ParticularAccount {
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private boolean boost;

    public TemporaryParticularAccount(String accountNumber, String accountName, Double balance,
        Double interestRate, ParticularAccountOwner owner, LocalDate creationDate, LocalDate expirationDate) {
        super(accountName, accountNumber, balance, owner, interestRate);
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public TemporaryParticularAccount(String accountNumber, String accountName, Double balance,
          Double interestRate, ParticularAccountOwner owner, LocalDate creationDate, LocalDate expirationDate, boolean boost) {
        this(accountNumber, accountName, balance, interestRate, owner, creationDate, expirationDate);
        this.boost = boost;
    }

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
