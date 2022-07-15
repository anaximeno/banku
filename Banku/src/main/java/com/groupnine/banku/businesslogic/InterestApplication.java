package com.groupnine.banku.businesslogic;

import java.time.LocalDateTime;

public class InterestApplication extends BankingOperation {
    private Account account;
    private double balanceBefore;
    private double value;
    private double balanceAfter;
    private double interest;

    public InterestApplication(Employee operator, LocalDateTime dateTime, Account account) {
        this(operator, dateTime, account, account.getInterestRate());
    }

    public InterestApplication(Employee operator, LocalDateTime dateTime, Account account, double interest) {
        super(operator, dateTime);
        if ( account != null && interest > 0d){
            this.account = account;
            this.interest = interest;
        }
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
            this.value = account.getAccountBalance() * interest;
            this.balanceBefore = account.getAccountBalance();
            this.account.setAccountBalance(account.getAccountBalance() - value);
            BankAgency.getInstance().addIncomeToBankAccount(value);
            this.balanceAfter = account.getAccountBalance();
            this.wasExecuted = true;
            return true;
        } else {
            return false;
        }
    }
}
