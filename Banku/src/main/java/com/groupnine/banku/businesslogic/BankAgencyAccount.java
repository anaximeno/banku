package com.groupnine.banku.businesslogic;

import java.util.*;

public class BankAgencyAccount {
    private Employee admin;
    private List<Employee> authorizedUsers;
    private double balance;

    public BankAgencyAccount(Employee admin, double balance) {
        this.admin = admin;
        this.balance = balance;
        // TODO: handle authorized users
    }

    public double getBalance() {
        return balance;
    }

    public void setBalanceTo(double balance) {
        this.balance = balance;
    }

    public Employee getAdmin() {
        return admin;
    }

    public void setAdmin(Employee admin) {
        this.admin = admin;
    }
}
