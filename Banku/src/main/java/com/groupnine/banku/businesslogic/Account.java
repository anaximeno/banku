package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private String accountName;
    private Double availableBalance;
    private Double interestRate;
    private AccountOwner owner;
    private List<Double> balanceRecord;
    private LocalDate lastTimeRecordWasSave;

    public Account(String accountNumber, String accountName, AccountOwner owner, Double availableBalance, Double interestRate) {
        setAccountNumber(accountNumber);
        setAccountName(accountName);
        setAccountBalance(availableBalance);
        updateInterestRateTo(interestRate);
        setOwner(owner);
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public void setOwner(AccountOwner owner) {
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
        this.interestRate = interestRate;
    }
    public Double getInterestRate(){
        return interestRate;
    }

    public LocalDate getLastTimeRecordWasSave() {
        return lastTimeRecordWasSave;
    }

    public void recordCurrentBalance() {
        if (lastTimeRecordWasSave.isBefore(LocalDate.now())) {
            balanceRecord.add(availableBalance);
        }
    }

    public List<Double> getBalanceRecord() {
        return balanceRecord;
    }
}
