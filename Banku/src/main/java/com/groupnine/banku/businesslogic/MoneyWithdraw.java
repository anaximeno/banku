package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MoneyWithdraw extends BankingOperation {
    private Account account;
    private double valueMoved;
    private double balanceBeforeWithDraw;
    private double balanceAfterWithDraw;

    public MoneyWithdraw(Employee operator, LocalDate dateTime, Account account, double valueMoved) {
        super(operator, dateTime);
            this.account = account;
            this.valueMoved = valueMoved;
    }

    @Override
    public String getDescription (){
        String description = "Dinheiro retirado da conta numero " + this.account.getAccountNumber()
                + " no valor de " + this.valueMoved;
        return description;
    }

    @Override
    public String getFullDescription (){
        String fullDescription = getDescription() + "\n Saldo anterior = " + this.balanceBeforeWithDraw
                + "\n Saldo atual = " + this.balanceAfterWithDraw;
        return fullDescription;
    }


    @Override
    public boolean executeOperation() {
        if (account == null || account.getAccountBalance() < valueMoved || wasExecuted) {
            return false;
        } else {
            // guardar o saldo antes da transacao
            this.balanceBeforeWithDraw = account.getAccountBalance();
            account.setAccountBalance(account.getAccountBalance() - valueMoved);
            // guardar o saldo depois da transacao
            this.balanceAfterWithDraw = account.getAccountBalance();
            wasExecuted = true;
            return true;
        }
    }
}
