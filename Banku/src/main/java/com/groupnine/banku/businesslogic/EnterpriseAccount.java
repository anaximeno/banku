package com.groupnine.banku.businesslogic;

import java.util.ArrayList;

public class EnterpriseAccount extends AccountOfCards {
    final static double TRANSACTION_INTEREST = 0.02;
    private ArrayList<Card> cards;
    private ParticularAccountOwner admin;
    private ArrayList<ParticularAccountOwner> authorizedUsers;
    private ArrayList<Double> dailyBalanceRecord;
    private boolean todayBalanceWasSaved;

    public EnterpriseAccount(String accountNumber, String accountName, EnterpriseAccountOwner owner, ParticularAccountOwner admin,
                     double availableBalance, double interestRate, ArrayList<ParticularAccountOwner> authorizedUsers, ArrayList<Card> cards) {
        super(accountNumber, accountName, owner, availableBalance, interestRate, cards);
        this.admin = admin;
        this.authorizedUsers = authorizedUsers;
        this.todayBalanceWasSaved = false;
        this.dailyBalanceRecord = new ArrayList<>();
    }

    public EnterpriseAccount(String accountNumber, String accountName, EnterpriseAccountOwner owner, ParticularAccountOwner admin,
                             double balance, double interestRate) {
        this(accountNumber, accountName, owner, admin, balance, interestRate, new ArrayList<>(), new ArrayList<>());
    }

    public EnterpriseAccount(String accountNumber, String accountName, EnterpriseAccountOwner owner, double balance, double interestRate) {
        this(accountNumber, accountName, owner, null, balance, interestRate, new ArrayList<>(), new ArrayList<>());
    }

    public Double getTheAverageOfStoredBalances(){
        double average = 0d;
        final int days = dailyBalanceRecord.size();

        for (int i = 0 ; i < days ; i++) {
            // caso days == 0 este loop não será executado,
            // então não precisa ser verificado.
            average += dailyBalanceRecord.get(i) / (double) days;
        }

        return  average;
    }

    public ParticularAccountOwner getAdmin() {
        return admin;
    }

    public void setAdmin(ParticularAccountOwner admin) {
        this.admin = admin;
    }

    public ArrayList<ParticularAccountOwner> getAuthorizedUsers() {
        return authorizedUsers;
    }

    public void setAuthorizedUsers(ArrayList<ParticularAccountOwner> authorizedUsers) {
        this.authorizedUsers = authorizedUsers;
    }

    public ArrayList<Double> getDailyBalanceRecord() {
        return dailyBalanceRecord;
    }

    public void emptyDailyBalanceRecord() {
        this.dailyBalanceRecord = new ArrayList<>();
    }

    public boolean isTodayBalanceWasSaved() {
        return todayBalanceWasSaved;
    }

    public void setTodayBalanceWasSaved(boolean todayBalanceWasSaved) {
        this.todayBalanceWasSaved = todayBalanceWasSaved;
    }
}
