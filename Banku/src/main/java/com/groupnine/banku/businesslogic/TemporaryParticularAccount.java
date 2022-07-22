package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.time.Period;

public class TemporaryParticularAccount extends Account {
    private final LocalDate creationDate;
    private LocalDate expirationDate;
    private int numberOfDaysBetweenCreationAndExpiration;
    private boolean boost;

    public TemporaryParticularAccount(String accountNumber, String accountName, Double balance,
        Double interestRate, ParticularAccountOwner owner, LocalDate creationDate, LocalDate expirationDate) {
        super(accountNumber, accountName, owner, balance, interestRate);
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.boost = false;
        numberOfDaysBetweenCreationAndExpiration = Period.between(creationDate, expirationDate).getDays();
    }

    public TemporaryParticularAccount(String accountNumber, String accountName, Double balance,
          Double interestRate, ParticularAccountOwner owner, LocalDate creationDate, LocalDate expirationDate, boolean boost) {
        this(accountNumber, accountName, balance, interestRate, owner, creationDate, expirationDate);
        this.boost = boost;
    }

    //funcao que confirma se a conta pode realizar operacoes
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
        numberOfDaysBetweenCreationAndExpiration = Period.between(creationDate, expirationDate).getDays();
    }

    public Period getNumberOfDaysToExpiration() {
        return Period.between(creationDate, expirationDate);
    }

    public int getNumberOfDaysBetweenCreationAndExpiration() {
        return numberOfDaysBetweenCreationAndExpiration;
    }
}
