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

        return true;
    }
}
