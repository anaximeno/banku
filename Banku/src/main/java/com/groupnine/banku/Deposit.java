package com.groupnine.banku;

import java.time.LocalDateTime;

public class Deposit  extends BankingOperation{
    private Account account;
    private double valueDeposited;
    private double balanceBeforeDeposit;
    private double balanceAfterDeposit;

    public String getDescription (){
        String description = "Money deposit to account with number " + this.account.getAccountNumber()
                + " in the amount of " + this.valueDeposited;
        return description;
    }

    public String getFullDescription (){
        String fullDescription = "Money deposit to account with number " +this.account.getAccountNumber()
                + " in the amount of " + this.valueDeposited + "\n Balance before = " + this.balanceBeforeDeposit
                + "\n Balance after = " + this.balanceAfterDeposit;
        return fullDescription;
    }

    public Deposit(Operator operator, LocalDateTime dateTime, Account account, double valueDeposited,
                         double balanceBeforeDeposit) {
        super(operator, dateTime);
        if (account != null && valueDeposited > 0 && balanceBeforeDeposit > 0){
            this.account = account;
            this.valueDeposited = valueDeposited;
            this.balanceBeforeDeposit = balanceBeforeDeposit;
            this.balanceAfterDeposit = this.balanceBeforeDeposit + this.valueDeposited;
        }

    }
}
