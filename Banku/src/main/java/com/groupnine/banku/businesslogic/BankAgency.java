package com.groupnine.banku.businesslogic;

import com.groupnine.banku.miscellaneous.Logger;

import java.util.ArrayList;
import java.util.List;

public class BankAgency {
    static private BankAgency instance;
    private String name;
    private String address;
    private List<Employee> employees;
    private List<Account> clientAccounts;
    private List<AccountOwner> clients;
    private BankAgencyAccount bankAccount;
    private List<BankingOperation> operationsLog;

    public Account findAccountByNumber (String accountNumber){
        for (Account account : clientAccounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public Account findAccountByName (String accountName){
        for (Account account : clientAccounts) {
            if (account.getAccountName().equals(accountName)) {
                return account;
            }
        }
        return null;
    }

    public List<Account> findAccountsByOwnerID (String ownerID){
        List<Account> list = new ArrayList<>();
        for (Account account : clientAccounts) {
            if (account.getOwner() == findAccountOwnerByID(ownerID)) {
                list.add(account);
            }
        }
        return list;
    }

    public AccountOwner findAccountOwnerByNIF (String NIF){
        for (AccountOwner client : clients) {
            if (client.getNIF().equals(NIF)) {
                return client;
            }
        }
        return null;
    }


    public AccountOwner findAccountOwnerByID (String id){
        for (AccountOwner client : clients) {
            Logger.log("Serching for " + client.getOwnerID() + " is eq to " + id);
            if (client.getOwnerID().equals(id)) {
                return client;
            }
        }
        return null;
    }
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

    // TODO: throw error if account id already used!
    public void addNewAccount(Account eac) {
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

    public IOperator getBankOperator(String name, String lastName, String password) {
        for (Employee e : employees) {
            if (e.getName().equals(name) && e.getLastName().equals(lastName) && e.CheckPassword(password)) {
                return e;
            }
        }
        return null;
    }

    public List<OrdinaryParticularAccount> getOrdinaryAccountList() {
        List<OrdinaryParticularAccount> list = new ArrayList<>();

        for (Account acc : clientAccounts) {
            if (acc instanceof OrdinaryParticularAccount opacc) {
                list.add(opacc);
            }
        }

        return list;
    }

    public List<EnterpriseAccount> getEnterpriseAccountList() {
        List<EnterpriseAccount> list = new ArrayList<>();

        for (Account acc : clientAccounts) {
            if (acc instanceof EnterpriseAccount eacc) {
                list.add(eacc);
            }
        }

        return list;
    }

    public List<TemporaryParticularAccount> getTemporaryAccountList() {
        List<TemporaryParticularAccount> list = new ArrayList<>();

        for (Account acc : clientAccounts) {
            if (acc instanceof TemporaryParticularAccount eacc) {
                list.add(eacc);
            }
        }

        return list;
    }

    public void addOperationLog(BankingOperation operation) {
        this.operationsLog.add(operation);
    }

    public List<BankingOperation> getOperationsLog() {
        return operationsLog;
    }
}
