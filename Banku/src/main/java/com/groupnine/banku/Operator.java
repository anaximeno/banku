package com.groupnine.banku;

public class Operator {
    public Employee employeeResponsible;

    public Operator(Employee employee) {
        employeeResponsible = employee;
    }

    public boolean makeTransaction(Account from, Account to, double value) {
        final double enterpriseAccountTransactionIterestRate = 0.2;
        final BankAgency agency = BankAgency.getInstance();

        if (from == null || to == null || value < 0) {
            return false;
        }

        double accountFromBalance = from.getAccountBalance();
        double accountToBalance = to.getAccountBalance();

        double accountFromNewBalance = accountFromBalance - value;
        double accountToNewBalance = accountToBalance + value;

        // Se a conta da qual se transfere for uma empresa, aplica-se um juro especial
        if (from instanceof EnterpriseAccount eac) {
            double transactionInterest = enterpriseAccountTransactionIterestRate * value;
            accountFromNewBalance -= transactionInterest;
            agency.addIncomeToBankAccount(transactionInterest);
        }

        from.setAccountBalance(accountFromNewBalance);
        to.setAccountBalance(accountToNewBalance);

        // TODO: Log transaction

        return true;
    }

    public boolean makeMoneyWithdraw(Account from, double value) {
        double fromAccountBalance = from.getAccountBalance();
        double fromAccountBalanceAfter = fromAccountBalance - value;
        if (fromAccountBalanceAfter > 0) {
            from.setAccountBalance(fromAccountBalanceAfter);
            // TODO: Log Withdraw
            return true;
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
