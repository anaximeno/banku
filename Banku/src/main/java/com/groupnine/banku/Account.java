package com.groupnine.banku;

public abstract class Account {
    private String accountNumber;
    private String accountName;
    private Double availableBalance;
    private Double interestRate;
    private AccountOwner owner;

    public Account(String accountNumber, String accountName, AccountOwner owner, Double availableBalance, Double interestRate) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.availableBalance = availableBalance;
        this.interestRate = interestRate;
        this.owner = owner;
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public void setOwner(ParticularAccountOwner owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        
        return accountName;
    }

    public void setAccountName(String accountName) {
        
        this.accountName = accountName;
    }

    public Double getAccountBalance() {
        
        return availableBalance;
    }

    public void setAccountBalance(Double balance) {
        
        this.availableBalance = balance;
    }

    public void updateInterestRateTo(Double interestRate){

    }
    public Double getInterestRate(){

        return interestRate;
    }

}
