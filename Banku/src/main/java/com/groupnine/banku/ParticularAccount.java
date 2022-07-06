package com.groupnine.banku;

public abstract class ParticularAccount extends Account {
    private ParticularAccountOwner owner;

    public ParticularAccount(String accountNumber, String accountName, Double availableBalance, Double interestRate, ParticularAccountOwner owner) {
        super(accountNumber, accountName, availableBalance, interestRate);
        this.owner = owner;
    }

    public ParticularAccountOwner getOwner() {

        return owner;
    }

    public void setOwner(ParticularAccountOwner owner) {

        this.owner = owner;
    }
}