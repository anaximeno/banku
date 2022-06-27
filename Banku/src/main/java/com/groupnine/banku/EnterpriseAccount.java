package com.groupnine.banku;

import java.util.List;

public class EnterpriseAccount {
    private List<Card> cards;
    private ParticularAccountOwner admin;
    private List<ParticularAccountOwner> authorizedUsers;
    private List<Double> dailyBalanceRecordOfTheMonth;
    private Boolean todaysBalanceWasSaved;

    public List<Double> getAverageMonthlyBalance(){
        return dailyBalanceRecordOfTheMonth;
    }
}
