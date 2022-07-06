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

    public Transaction(Operator operator, LocalDateTime dateTime, Account accountFrom, Account accountTo, double value) {
        super(operator, dateTime);
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.value = value;
    }

    @Override
    public boolean executeOperation() {
        BankAgency agency = BankAgency.getInstance();
        if (accountFrom == null || accountTo == null || accountFrom.getAccountBalance() - value > 0 || !wasExecuted) {
            double valueWithInterests = value;
            if (accountFrom instanceof EnterpriseAccount) {
                double transactionInterest = value * EnterpriseAccount.TRANSACTION_INTEREST;
                valueWithInterests = valueWithInterests + transactionInterest;
                agency.addIncomeToBankAccount(transactionInterest);
            }

            // Store the balance before the transaction
            this.balanceOfFromAccountBeforeTransaction = accountFrom.getAccountBalance();
            this.balanceOfToAccountBeforeTransaction = accountTo.getAccountBalance();

            accountFrom.setAccountBalance(accountFrom.getAccountBalance() - valueWithInterests);
            accountTo.setAccountBalance(accountTo.getAccountBalance() + value);

            // Store the balance after the transaction
            this.balanceOfFromAccountAfterTransaction = accountFrom.getAccountBalance();
            this.balanceOfToAfterTransaction = accountTo.getAccountBalance();

            wasExecuted = true;
            return true;
        } else {
            return false;
        }
    }
}
