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
        String description = "Money transaction from account with number" + accountFrom.getAccountNumber()
                + " to account with number " + accountTo.getAccountNumber() + " in the amount of " + value;
         return description;
    }

    public String getFullDescription (){
        String fullDescription = getDescription()
                + "\n Account balance debited before the transaction = " + balanceOfFromAccountBeforeTransaction
                + "\n Account balance debited after the transaction = " + balanceOfFromAccountAfterTransaction
                + "\n Account balance credited before the transaction = " + balanceOfToAccountBeforeTransaction
                + "\n Account balance credited after the transaction = " + balanceOfToAfterTransaction;
        return fullDescription;

    }

    public Transaction(Operator operator, LocalDateTime dateTime, Account accountFrom, Account accountTo, double value,
                       double balanceOfFromAccountBeforeTransaction, double balanceOfToAccountBeforeTransaction
    ) {
        super(operator, dateTime);
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.value = value;
        this.balanceOfFromAccountBeforeTransaction = balanceOfFromAccountBeforeTransaction;
        double valueWithInterests = value;
        if (accountFrom instanceof EnterpriseAccount) {
            valueWithInterests *= (1 + EnterpriseAccount.TRANSACTION_INTEREST);
        }
        // Interest is only applied to account from which are EnterpriseAccounts
        this.balanceOfFromAccountAfterTransaction = balanceOfFromAccountBeforeTransaction - valueWithInterests;
        this.balanceOfToAccountBeforeTransaction = balanceOfToAccountBeforeTransaction;
        this.balanceOfToAfterTransaction = balanceOfToAccountBeforeTransaction + value;
    }
}
