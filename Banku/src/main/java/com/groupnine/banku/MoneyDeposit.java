package com.groupnine.banku;

import java.time.LocalDateTime;

public class MoneyDeposit extends BankingOperation {
    private Account account;
    private double value;
    private double balanceBefore;
    private double balanceAfter;

    public MoneyDeposit(Operator operator, LocalDateTime dateTime, Account account, double value, double balanceBefore) {
        super(operator, dateTime);
        this.account = account;
        this.value = value;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceBefore + value;
    }

    public String getDescription() {
        String description = "Money deposit from account with number " + account.getAccountNumber()
                + " in the amount of " + value;
        return description;
    }

    public String getFullDescription() {
        String fullDescription = getDescription() + "\n Balance before = " +
                        balanceBefore + "\n Balance after = " + balanceAfter;
        return fullDescription;
    }
}

