package com.groupnine.banku;

import java.util.ArrayList;

public class EnterpriseAccount extends Account{
    private ArrayList<Card> cards;
    private ParticularAccountOwner admin;
    private ArrayList<ParticularAccountOwner> authorizedUsers;
    private ArrayList<Double> dailyBalanceRecordOfTheMonth;
    private boolean todayBalanceWasSaved;

    public EnterpriseAccount(String accountNumber, String accountName, Double availableBalance, Double interestRate,
                             ArrayList<Card> cards, ParticularAccountOwner admin, ArrayList<ParticularAccountOwner> authorizedUsers) {
        super(accountNumber, accountName, availableBalance, interestRate);
        this.cards = cards;
        this.admin = admin;
        this.authorizedUsers = authorizedUsers;
        this.dailyBalanceRecordOfTheMonth = new ArrayList<>();
        this.todayBalanceWasSaved = false;
    }

    public Double getAverageMonthlyBalance(){
        double average = 0d;
        if (dailyBalanceRecordOfTheMonth.size() != 0) {
            for (int i = 0; i < dailyBalanceRecordOfTheMonth.size(); i++) {
                average += dailyBalanceRecordOfTheMonth.get(i).doubleValue();
            }
            average /= dailyBalanceRecordOfTheMonth.size();
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

    public ArrayList<Double> getDailyBalanceRecordOfTheMonth() {
        return dailyBalanceRecordOfTheMonth;
    }

    public void setDailyBalanceRecordOfTheMonth(ArrayList<Double> dailyBalanceRecordOfTheMonth) {
        this.dailyBalanceRecordOfTheMonth = dailyBalanceRecordOfTheMonth;
    }

    public boolean isTodayBalanceWasSaved() {
        return todayBalanceWasSaved;
    }

    public void setTodayBalanceWasSaved(boolean todayBalanceWasSaved) {
        this.todayBalanceWasSaved = todayBalanceWasSaved;
    }
}
