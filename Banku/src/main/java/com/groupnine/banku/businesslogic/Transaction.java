package com.groupnine.banku.businesslogic;

import java.time.LocalDateTime;

public class Transaction extends BankingOperation{

    private Account accountFrom;
    private Account accountTo;
    private double value;
    private double balanceOfFromAccountBeforeTransaction;
    private double balanceOfFromAccountAfterTransaction;
    private double balanceOfToAccountBeforeTransaction;
    private double balanceOfToAfterTransaction;
    private String details;

    public Transaction(Employee operator, LocalDateTime dateTime, Account accountFrom, Account accountTo, double value) {
        super(operator, dateTime);
        if (accountFrom != null && accountTo != null && value > 0d){
            this.accountFrom = accountFrom;
            this.accountTo = accountTo;
            this.value = value;
        }

    }

    @Override
    public String getDescription () {
        // If transaction interest was applied, show the information
        String interestInfo = accountFrom instanceof EnterpriseAccount ?
                "With transaction interest of " + value * EnterpriseAccount.TRANSACTION_INTEREST + "." :  "";
        String description = "Money transaction from account with number " + accountFrom.getAccountNumber()
                + " to account with number " + accountTo.getAccountNumber() + " in the amount of " + value + "."
                + interestInfo;
         return description;
    }

    @Override
    public String getFullDescription (){
        String fullDescription = getDescription()
                + "\n Account balance debited before the transaction = " + balanceOfFromAccountBeforeTransaction
                + "\n Account balance debited after the transaction = " + balanceOfFromAccountAfterTransaction
                + "\n Account balance credited before the transaction = " + balanceOfToAccountBeforeTransaction
                + "\n Account balance credited after the transaction = " + balanceOfToAfterTransaction;
        return fullDescription;

    }

    @Override
    public boolean executeOperation() {
        BankAgency agency = BankAgency.getInstance();
        if (accountFrom == null || accountTo == null || accountFrom.getAccountBalance() - value > 0 || !wasExecuted) {
            // Store the balance before the transaction
            this.balanceOfFromAccountBeforeTransaction = accountFrom.getAccountBalance();
            this.balanceOfToAccountBeforeTransaction = accountTo.getAccountBalance();

            if (accountFrom instanceof EnterpriseAccount) {
                // If the type of the account is Enterprise, a transaction interest is applied.
                InterestApplication interestApplication = new InterestApplication(
                        getOperator(), LocalDateTime.now(), accountFrom, EnterpriseAccount.TRANSACTION_INTEREST
                );
                interestApplication.executeOperation();
                BankAgency.getInstance().addOperationLog(interestApplication);
            }

            accountFrom.setAccountBalance(accountFrom.getAccountBalance() - value);
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
