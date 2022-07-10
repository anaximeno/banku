package com.groupnine.banku;

import java.time.LocalDateTime;

public class MoneyWithdraw extends BankingOperation {
    private Account account;
    private double valueMoved;
    private double balanceBeforeWithDraw;
    private double balanceAfterWithDraw;

    public String getDescription (){
        String description = "Money draw from account with number " + this.account.getAccountNumber()
                + " in the amount of " + this.valueMoved;
        return description;
    }

    public String getFullDescription (){
        String fullDescription = "Money draw from account with number " + this.account.getAccountNumber()
                + " in the amount of " + this.valueMoved + "\n Balance before = " + this.balanceBeforeWithDraw
                + "\n Balance after = " + this.balanceAfterWithDraw;
        return fullDescription;
    }

    public MoneyWithdraw(Operator operator, LocalDateTime dateTime, Account account, double valueMoved) {
        super(operator, dateTime);
        this.account = account;
        this.valueMoved = valueMoved;
    }

    @Override
    public boolean executeOperation() {
        if (account == null || account.getAccountBalance() < valueMoved || wasExecuted) {
            return false;
        } else {
            // Store Account Balance
            this.balanceBeforeWithDraw = account.getAccountBalance();
            account.setAccountBalance(account.getAccountBalance() - valueMoved);
            // Store Account Balance
            this.balanceAfterWithDraw = account.getAccountBalance();
            wasExecuted = true;
            return true;
        }
    }
}
