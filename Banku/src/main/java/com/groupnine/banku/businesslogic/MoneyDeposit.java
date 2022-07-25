package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MoneyDeposit extends BankingOperation {
    private Account account;
    private double value;
    private double balanceBefore;
    private double balanceAfter;

    public MoneyDeposit(Employee operator, LocalDate dateTime, Account account, double value) {
        super(operator, dateTime);
        this.account = account;
        this.value = value;
    }

    @Override
    public String getDescription() {
        String description = "Dinheiro depositado na conta numero " + account.getAccountNumber()
                + " no valor de " + value;
        return description;
    }

    @Override
    public String getFullDescription() {
        String fullDescription = getDescription() + "\n Saldo anterior = " +
                        balanceBefore + "\n Saldo atual = " + balanceAfter;
        return fullDescription;
    }

    @Override
    public boolean executeOperation() {
        if (!wasExecuted) {
            // guardar o saldo antes da transacao
            this.balanceBefore = account.getAccountBalance();
            account.setAccountBalance(account.getAccountBalance() + value);
            // guardar o saldo apos a transacao
            this.balanceAfter = account.getAccountBalance();
            wasExecuted = true;
            return true;
        }
        return false;
    }
}

