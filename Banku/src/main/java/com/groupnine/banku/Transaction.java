package com.groupnine.banku;

import java.time.LocalDateTime;

public class Transaction extends BankingOperation{

    private Account accountFrom;
    private Account accountTo;
    private double value;
    private double balanceOfFromAccountBeforeTransaction;
    private double balanceOfFromAccountAfterTransaction;
    private double balanceOfToAccountBeforeTransaction;
    private double balanceOfToAfterTransaction;

    public String getDescription (){
        String description = "Money transaction from account with number" +this.accountFrom.getAccountNumber()
                + " to account with number " + this.accountTo.getAccountNumber() + " in the amount of " +this.value;
         return description;
    }

    public String getFullDescription (){
        String fullDescription = "Money transaction from account with number" + this.accountFrom.getAccountNumber()
                + " to account with number " + this.accountTo.getAccountNumber() + " in the amount of " +this.value
                + "\n Account balance debited before the transaction = " + this.balanceOfFromAccountBeforeTransaction
                + "\n Account balance debited after the transaction = " + this.balanceOfFromAccountAfterTransaction
                + "\n Account balance credited before the transaction = " + this.balanceOfToAccountBeforeTransaction
                + "\n Account balance credited after the transaction = " + this.balanceOfToAfterTransaction;
        return fullDescription;

    }

    public Transaction(Operator operator, LocalDateTime dateTime, Account accountFrom, Account accountTo, double value,
                       double balanceOfFromAccountBeforeTransaction, double balanceOfToAccountBeforeTransaction) {
        super(operator, dateTime);
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.value = value;
        this.balanceOfFromAccountBeforeTransaction = balanceOfFromAccountBeforeTransaction;
        this.balanceOfFromAccountAfterTransaction = this.balanceOfFromAccountBeforeTransaction - this.value;
        this.balanceOfToAccountBeforeTransaction = balanceOfToAccountBeforeTransaction;
        this.balanceOfToAfterTransaction = this.balanceOfToAccountBeforeTransaction + this.value;
    }
}
