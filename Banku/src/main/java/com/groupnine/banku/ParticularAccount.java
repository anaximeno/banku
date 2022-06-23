package com.groupnine.banku;

public abstract class ParticularAccount extends Account{
    private ParticularAccountOwner owner;

    public ParticularAccount(String accountName, String accountNumber, Double availableBalance, ParticularAccountOwner owner, Double interestRate) {
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
