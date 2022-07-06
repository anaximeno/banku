package com.groupnine.banku;

import java.time.LocalDateTime;

public class Operator {
    public Employee employeeResponsible;

    public Operator(Employee employee) {
        employeeResponsible = employee;
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
            MoneyWithDraw moneyWithDraw = new MoneyWithDraw(this, LocalDateTime.now(), from, value);
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

    // TODO: Set account number to int
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
