package com.groupnine.banku.businesslogic;

import java.time.LocalDateTime;

public class Employee implements IOperator {
    private String name;
    private String lastName;
    private String password;
    private String role;

    public Employee(String name, String lastName, String password, String role) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }


    public boolean CheckPassword(String passwordTry) {
        boolean result = false;
        if (passwordTry.equals(this.password)) {
            result = true;
        }
        return result;
    }

    public void ChangePassword(String newPassword) {
        if (!this.password.equals(newPassword)){
            this.password = newPassword;
        }
    }


    public boolean addNewClientToTheBank(AccountOwner client) {
        final BankAgency agency = BankAgency.getInstance();
        //verificar se ja existe  o cliente
        for (AccountOwner c : agency.getClients()) {
            if (c.getOwnerID().equals(client.getOwnerID()) || c.getNIF().equals(client.getNIF())) {
                return false;
            }
        }

        agency.addClient(client);
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public boolean addNewAccountToTheBank(Account account) {
        final BankAgency agency = BankAgency.getInstance();
        //verificar se ja existe a conta
        for (Account a : agency.getClientAccounts()) {
            if (a.getAccountNumber().equals(account.getAccountNumber()) || a.getAccountName().equals(account.getAccountName())) {
                return false;
            }
        }

        // adiciona o cliente caso não ter sido adicionado antes.
        addNewClientToTheBank(account.getOwner());
        agency.addNewAccount(account);
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

    public boolean deleteAccount(AccountOwner owner) {
        final BankAgency agency = BankAgency.getInstance();
        Account accountToBeRemoved = null;
        for (Account eac : agency.getClientAccounts()) {
            if (eac.getOwner() == owner) {
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

    public boolean deleteAccountOwner(String ownerID){
        final BankAgency agency = BankAgency.getInstance();
        AccountOwner accountOwnerToBeRemoved = null;
        for (AccountOwner eac : agency.getClients()) {
            if (eac.getOwnerID().equals(ownerID)) {
                accountOwnerToBeRemoved = eac;
                break;
            }
        }
        if (accountOwnerToBeRemoved != null) {
            //deletar conta vinculado com o cliente
            deleteAccount(accountOwnerToBeRemoved);
            agency.getClients().remove(accountOwnerToBeRemoved);
            return true;
        } else {
            return false;
        }
    }
}