package com.groupnine.banku.businesslogic;

import java.time.LocalDateTime;

public class InterestApplication extends BankingOperation {
    private final Account account;
    private double balanceBefore;
    private final double value;
    private double balanceAfter;

    public InterestApplication(Employee operator, LocalDateTime dateTime, Account account) {
        this(operator, dateTime, account, account.getInterestRate() * account.getAccountBalance());
    }

    public InterestApplication(Employee operator, LocalDateTime dateTime, Account account, double value) {
        super(operator, dateTime);
        this.account = account;
        this.value = value;
    }

    @Override
    public String getDescription() {
        String description = "Interest money to account with number" + this.account.getAccountNumber()
                + " in the amount of " + this.value;
        return description;
    }

    @Override
    public String getFullDescription() {
        String fullDescription = getDescription() + this.value +
                "\n Balance before = " +this.balanceBefore + "\n Balance after = "
                + this.balanceAfter;
        return fullDescription;
    }

    @Override
    public boolean executeOperation() {
        if (!wasExecuted) {
            this.balanceBefore = account.getAccountBalance();
            this.account.setAccountBalance(account.getAccountBalance() - this.value);
            BankAgency.getInstance().addIncomeToBankAccount(this.value);
            this.balanceAfter = account.getAccountBalance();
            this.wasExecuted = true;
            return true;
        } else {
            return false;
        }
    }
}
