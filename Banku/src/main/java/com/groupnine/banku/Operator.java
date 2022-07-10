package com.groupnine.banku;

import java.time.LocalDateTime;

public class Operator {
    private static int operatorIdCounter = 0;
    private final int id;
    private final Employee employee;
    
    private static void increaseIdCounter() {
        operatorIdCounter += 1;
    }

    public Operator(Employee employee) {
        increaseIdCounter();
        id = operatorIdCounter;
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getId() {
        return id;
    }

    public boolean makeTransaction(Account from, Account to, double value) {
        if (from == null || to == null || value < 0) {
            return false;
        }

        Transaction transaction = new Transaction(this, LocalDateTime.now(), from, to, value);

        boolean result = transaction.executeOperation();

        // Log the transaction
        BankAgency.getInstance().addOperationLog(transaction);
        return result;
    }

    public boolean makeMoneyWithdraw(Account from, double value) {
        if (from != null) {
            MoneyWithdraw moneyWithDraw = new MoneyWithdraw(this, LocalDateTime.now(), from, value);
            boolean result = moneyWithDraw.executeOperation();
            BankAgency.getInstance().addOperationLog(moneyWithDraw);
            return result;
        } else {
            return false;
        }
    }

    public boolean makeMoneyDeposit(Account account, double value) {
        if (account != null) {
            MoneyDeposit moneyDeposit = new MoneyDeposit(this, LocalDateTime.now(), account, value);
            boolean result = moneyDeposit.executeOperation();
            BankAgency.getInstance().addOperationLog(moneyDeposit);
            return result;
        } else {
            return false;
        }
    }

    public boolean addClientAccountToTheBank(EnterpriseAccount account) {
        final BankAgency agency = BankAgency.getInstance();

        for (Account a : agency.getClientAccounts()) {
            if (a.getAccountNumber().equals(account.getAccountNumber()) || a.getAccountName().equals(account.getAccountName())) {
                return false;
            }
        }
        agency.addEnterpriseAccount(account);
        return true;
    }

    public boolean deleteAccount(String accountNumber) {
        final BankAgency agency = BankAgency.getInstance();
        Account accountToBeRemoved = null;
        for (Account eac : agency.getClientAccounts()) {
            if (eac.getAccountNumber().equals(accountNumber)) {
                accountToBeRemoved = eac;
                break;
            }
        }
        if (accountToBeRemoved != null) {
            agency.getClientAccounts().remove(accountToBeRemoved);
            return true;
        } else {
            return false;
        }
    }
}
