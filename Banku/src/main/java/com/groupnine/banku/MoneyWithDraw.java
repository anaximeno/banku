package com.groupnine.banku;

import java.time.LocalDateTime;

public class MoneyWithDraw extends BankingOperation{

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
        String fullDescription = "Money draw from account with number " +this.account.getAccountNumber()
                + " in the amount of " + this.valueMoved + "\n Balance before = " + this.balanceBeforeWithDraw
                + "\n Balance after = " + this.balanceAfterWithDraw;
        return fullDescription;
    }

    public MoneyWithDraw(Operator operator, LocalDateTime dateTime, Account account, double valueMoved,
                       double balanceBeforeWithDraw) {
        super(operator, dateTime);
        this.account = account;
        this.valueMoved = valueMoved;
        this.balanceBeforeWithDraw = balanceBeforeWithDraw;
        this.balanceAfterWithDraw = this.balanceBeforeWithDraw + this.valueMoved;
    }
}
