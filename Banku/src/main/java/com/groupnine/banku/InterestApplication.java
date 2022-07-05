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
        String fullDescription = "Interest money to account with number" +this.account.getAccountNumber()
                + " in the amount of " + this.value + "\n Balance before = " +this.balanceBefore + "\n Balance after = "
                + this.balanceAfter;
        return fullDescription;

    }

    public InterestApplication(Operator operator, LocalDateTime dateTime, Account account, double balanceBefore, double value) {
        super(operator, dateTime);
        this.account = account;
        this.balanceBefore = balanceBefore;
        this.value = value;
        this.balanceAfter = this.balanceBefore + this.value;
    }
}
