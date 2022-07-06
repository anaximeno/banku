package com.groupnine.banku;

import java.lang.Math;
import java.util.ArrayList;

public class AccountFactory {
    private static int accountNumberCounter = 0;
    private static double currentEnterpriseInterestRate;
    private static double InterestRate;
    private static double currentTemporaryAccountInterestRate (){

    }

    public static EnterpriseAccount createEnterpriseAccount (String name, ParticularAccountOwner admin,
                                                             double initialBalance, ArrayList<ParticularAccountOwner> associated){

    }
    private static void increaseCounter() {
        accountNumberCounter += 1;
    }

    private static String getNewAccountNumberEncoded() {
        String num = String.valueOf(accountNumberCounter);

        int x = accountNumberCounter;
        double accu = Math.pow(10d, 12d);

        // Pad the num with zeros
        while (x < accu) {
            num = "0" + num;
            accu = accu / 10;
        }

        // Increase to counter
        increaseCounter();

        return num;
    }
}
