package com.groupnine.banku;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Operator {
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

        Transaction transaction = new Transaction(getEmployee(), LocalDateTime.now(), from, to, value);

        boolean result = transaction.executeOperation();

        // Log the transaction
        BankAgency.getInstance().addOperationLog(transaction);
        return result;
    }

    public boolean makeMoneyWithdraw(Account from, double value) {
        if (from != null) {
            MoneyWithdraw moneyWithDraw = new MoneyWithdraw(getEmployee(), LocalDateTime.now(), from, value);
            boolean result = moneyWithDraw.executeOperation();
            BankAgency.getInstance().addOperationLog(moneyWithDraw);
            return result;
        } else {
            return false;
        }
    }

    public boolean makeMoneyDeposit(Account account, double value) {
        if (account != null) {
            MoneyDeposit moneyDeposit = new MoneyDeposit(getEmployee(), LocalDateTime.now(), account, value);
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

public class BankAgency {
    static private BankAgency instance;
    private String name;
    private String address;
    private List<Employee> employees;
    private List<Account> clientAccounts;
    private List<AccountOwner> clients;
    private BankAgencyAccount bankAccount;
    private List<BankingOperation> operationsLog;

    private BankAgency(String name, String address) {
        final double initialBalance = 100000000;
        this.name = name;
        this.address = address;
        this.employees = new ArrayList<>();
        this.clientAccounts = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.bankAccount = new BankAgencyAccount(null, initialBalance);
        this.operationsLog = new ArrayList<>();
    }

    public static BankAgency getInstance() {
        if (instance == null) {
            instance = new BankAgency("Test Bank", "Somewhere");
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Account> getClientAccounts() {
        return clientAccounts;
    }

    public void addEnterpriseAccount(Account eac) {
        this.clientAccounts.add(eac);
    }

    public List<AccountOwner> getClients() {
        return clients;
    }

    public void addClient(AccountOwner aco) {
        this.clients.add(aco);
    }

    public void addIncomeToBankAccount(double income) {
        double balance = this.bankAccount.getBalance();
        this.bankAccount.setBalanceTo(balance + income);
    }

    public List<Employee> getEmployeeList() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public Operator getBankOperator(String name, String lastName, String password) {
        for (Employee e : employees) {
            if (e.getName().equals(name) && e.getLastName().equals(lastName) && e.CheckPassword(password)) {
                return new Operator(e);
            }
        }
        return null;
    }

    public void addOperationLog(BankingOperation operation) {
        this.operationsLog.add(operation);
    }

    public List<BankingOperation> getOperationsLog() {
        return operationsLog;
    }
}
