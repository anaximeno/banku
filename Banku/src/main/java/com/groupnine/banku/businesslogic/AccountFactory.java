package com.groupnine.banku.businesslogic;

import java.lang.Math;
import java.time.LocalDate;

// This class is abstract because only one AccountFactory will be used for all accounts
// so there's no need for instantiation on this class
public abstract class AccountFactory extends SequentiableFactory {
    private static double currentEnterpriseInterestRate = 0.01;
    private static double currentOrdinaryAccountInterestRate = 0.01;
    private static double currentTemporaryAccountInterestRate = 0.01;

    public static EnterpriseAccount createEnterpriseAccount(
            String name, EnterpriseAccountOwner owner, ParticularAccountOwner admin, double initialBalance)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentEnterpriseInterestRate;
        return new EnterpriseAccount(accountNumber, name, owner, admin, initialBalance, interest);
    }

    public static OrdinaryParticularAccount createOrdinaryParticularAccount(
            String name, ParticularAccountOwner owner, double balance, ParticularAccountOwner associate)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentOrdinaryAccountInterestRate;
        OrdinaryParticularAccount acc = new OrdinaryParticularAccount(accountNumber, name, owner, balance, interest);
        acc.setMinorAccountAssociate(associate);
        return acc;
    }
    
    public static TemporaryParticularAccount createTemporaryParticularAccount(
            String accountName, Double balance, ParticularAccountOwner owner, LocalDate expirationDate)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentTemporaryAccountInterestRate;
        return  new TemporaryParticularAccount(accountNumber, accountName, balance, interest, owner, LocalDate.now(), expirationDate);
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
