package com.groupnine.banku;

import java.time.LocalDateTime;

public class InterestApplication extends BankingOperation{
    private Account account;
    private double balanceBefore;
    private double value;
    private double balanceAfter;


    public String getDescription (){
        String description = "Interest money to account with number" + this.account.getAccountNumber()
                + " in the amount of " + this.value;
        return description;
    }

    public String getFullDescription (){
        String fullDescription = getDescription() + this.value +
                "\n Balance before = " +this.balanceBefore + "\n Balance after = "
                + this.balanceAfter;
        return fullDescription;
    }

    public InterestApplication(Operator operator, LocalDateTime dateTime, Account account) {
        super(operator, dateTime);
        this.account = account;
    }

    @Override
    public boolean executeOperation() {
        if (!wasExecuted) {
            this.value = account.getAccountBalance() * account.getInterestRate();
            this.balanceBefore = account.getAccountBalance();
            account.setAccountBalance(account.getAccountBalance() - value);
            this.balanceAfter = account.getAccountBalance();
            wasExecuted = true;
            return true;
        } else {
            return false;
        }
    }
}
