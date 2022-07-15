package com.groupnine.banku.businesslogic;

import java.lang.Math;
import java.time.LocalDate;

// This class is abstract because only one AccountFactory will be used for all accounts
// so there's no need for instantiation on this class
public abstract class AccountFactory {
    private static int accountNumberCounter = 0;
    private static double currentEnterpriseInterestRate;
    private static double currentOrdinaryAccountInterestRate;
    private static double currentTemporaryAccountInterestRate;

    public static EnterpriseAccount createEnterpriseAccount(
            String name, EnterpriseAccountOwner owner, ParticularAccountOwner admin, double initialBalance) {
        String accountNumber = getNewAccountNumberEncoded();
        double interest = currentEnterpriseInterestRate;
        return new EnterpriseAccount(accountNumber, name, owner, admin, initialBalance, interest);
    }

    public static OrdinaryParticularAccount createOrdinaryParticularAccount(
            String name, ParticularAccountOwner owner, double balance, ParticularAccountOwner associate) {
        String accountNumber = getNewAccountNumberEncoded();
        double interest = currentOrdinaryAccountInterestRate;
        return new OrdinaryParticularAccount(accountNumber, name, owner, balance, interest);
    }
    
    public static TemporaryParticularAccount createTemporaryParticularAccount(
            String accountName, Double balance, ParticularAccountOwner owner, LocalDate expirationDate) {
        String accountNumber = getNewAccountNumberEncoded();
        double interest = currentTemporaryAccountInterestRate;
        return  new TemporaryParticularAccount(accountNumber, accountName, balance, interest, owner, LocalDate.now(), expirationDate);
    }
    private static void increaseCounter() {
        accountNumberCounter += 1;
    }

    private static String getNewAccountNumberEncoded() {
        String num = String.valueOf(accountNumberCounter);

        // accu - is the accumulator, it accumulates the number of decimal places
        // which is then reduced until the value from accu is less than the accountNumberCounter
        double accu = Math.pow(10d, 12d);
        for (int x = accountNumberCounter ; x < accu ; accu = accu / 10) {
            num = "0" + num;
        }

        // Increase the counter
        increaseCounter();
        return num;
    }

    public static void updateCurrentEnterpriseInterestRate(double currentEnterpriseInterestRate) {
        AccountFactory.currentEnterpriseInterestRate = currentEnterpriseInterestRate;
    }

    public static void updateCurrentOrdinaryAccountInterestRate(double currentOrdinaryAccountInterestRate) {
        AccountFactory.currentOrdinaryAccountInterestRate = currentOrdinaryAccountInterestRate;
    }

    public static void updateCurrentTemporaryAccountInterestRate(double currentTemporaryAccountInterestRate) {
        AccountFactory.currentTemporaryAccountInterestRate = currentTemporaryAccountInterestRate;
    }
}
