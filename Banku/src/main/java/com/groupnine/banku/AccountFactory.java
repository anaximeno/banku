package com.groupnine.banku;

import java.lang.Math;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountFactory {
    private static int accountNumberCounter = 0;
    private static double currentEnterpriseInterestRate;
    private static double currentOrdinaryAccountInterestRate;
    private static double currentTemporaryAccountInterestRate;

    public static EnterpriseAccount createEnterpriseAccount (String name, EnterpriseAccountOwner owner ,ParticularAccountOwner admin,
                                                             double initialBalance){
        EnterpriseAccount eAccount = new EnterpriseAccount (getNewAccountNumberEncoded(), name, owner, admin,
        initialBalance, currentEnterpriseInterestRate);
        return eAccount;
    }

    public OrdinaryParticularAccount createOrdinaryParticularAccount (String name, ParticularAccountOwner owner, double balance,
                                                                      ParticularAccountOwner associate){
        OrdinaryParticularAccount oAccount = new OrdinaryParticularAccount(getNewAccountNumberEncoded(), name, owner, balance,
                currentOrdinaryAccountInterestRate);
        return  oAccount;
    }
    
    public TemporaryParticularAccount createTemporaryParticularAccount (String accountName, Double balance,
                                                                        ParticularAccountOwner owner, LocalDate expirationDate){
        TemporaryParticularAccount tAccount = new TemporaryParticularAccount(getNewAccountNumberEncoded(), accountName, balance,
               currentTemporaryAccountInterestRate, owner, LocalDate.now(), expirationDate);
        return  tAccount;
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
