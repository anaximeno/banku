package com.groupnine.banku;

import java.time.LocalDateTime;

public class MoneyDeposit extends BankingOperation {
    private Account account;
    private double value;
    private double balanceBefore;
    private double balanceAfter;

    public MoneyDeposit(Employee operator, LocalDateTime dateTime, Account account, double value) {
        super(operator, dateTime);
        this.account = account;
        this.value = value;
    }

    @Override
    public String getDescription() {
        String description = "Money deposit from account with number " + account.getAccountNumber()
                + " in the amount of " + value;
        return description;
    }

    @Override
    public String getFullDescription() {
        String fullDescription = getDescription() + "\n Balance before = " +
                        balanceBefore + "\n Balance after = " + balanceAfter;
        return fullDescription;
    }

    @Override
    public boolean executeOperation() {
        if (!wasExecuted) {
            // Store the balance Before transaction
            this.balanceBefore = account.getAccountBalance();
            account.setAccountBalance(account.getAccountBalance() + value);
            // Store the balance After the transaction
            this.balanceAfter = account.getAccountBalance();
            wasExecuted = true;
            return true;
        }
        return false;
    }
}

