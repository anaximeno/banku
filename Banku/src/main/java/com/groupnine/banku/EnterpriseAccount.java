package com.groupnine.banku;

import java.util.ArrayList;

public class EnterpriseAccount extends Account {
    private ArrayList<Card> cards;
    private ParticularAccountOwner admin;
    private ArrayList<ParticularAccountOwner> authorizedUsers;
    private ArrayList<Double> dailyBalanceRecord;
    private boolean todayBalanceWasSaved;

    public EnterpriseAccount(String accountNumber, String accountName, double availableBalance, double interestRate,
                 ArrayList<Card> cards, ParticularAccountOwner admin, ArrayList<ParticularAccountOwner> authorizedUsers) {
        super(accountNumber, accountName, availableBalance, interestRate);
        this.cards = cards;
        this.admin = admin;
        this.authorizedUsers = authorizedUsers;
        this.todayBalanceWasSaved = false;
        this.dailyBalanceRecord = new ArrayList<>();
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
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
