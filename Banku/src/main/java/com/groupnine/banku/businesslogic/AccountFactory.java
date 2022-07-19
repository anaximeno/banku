package com.groupnine.banku.businesslogic;

import java.lang.Math;
import java.time.LocalDate;

// This class is abstract because only one AccountFactory will be used for all accounts
// so there's no need for instantiation on this class
public class AccountFactory extends SequentiableFactory {
    private double currentEnterpriseInterestRate = 0.01;
    private double currentOrdinaryAccountInterestRate = 0.01;
    private double currentTemporaryAccountInterestRate = 0.01;

    public AccountFactory(double particularInterest, double enterpriseIntererst, double temporaryInterest) {
        currentOrdinaryAccountInterestRate = particularInterest;
        currentEnterpriseInterestRate = enterpriseIntererst;
        currentTemporaryAccountInterestRate = temporaryInterest;
    }

    public EnterpriseAccount createEnterpriseAccount(
            String name, EnterpriseAccountOwner owner, ParticularAccountOwner admin, double initialBalance)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentEnterpriseInterestRate;
        return new EnterpriseAccount(accountNumber, name, owner, admin, initialBalance, interest);
    }

    public OrdinaryParticularAccount createOrdinaryParticularAccount(
            String name, ParticularAccountOwner owner, double balance, ParticularAccountOwner associate)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentOrdinaryAccountInterestRate;
        OrdinaryParticularAccount acc = new OrdinaryParticularAccount(accountNumber, name, owner, balance, interest);
        acc.setMinorAccountAssociate(associate);
        return acc;
    }
    
    public TemporaryParticularAccount createTemporaryParticularAccount(
            String accountName, Double balance, ParticularAccountOwner owner, LocalDate expirationDate)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentTemporaryAccountInterestRate;
        return  new TemporaryParticularAccount(accountNumber, accountName, balance, interest, owner, LocalDate.now(), expirationDate);
    }

    public void updateCurrentEnterpriseInterestRate(double currentEnterpriseInterestRate) {
        this.currentEnterpriseInterestRate = currentEnterpriseInterestRate;
    }

    public void updateCurrentOrdinaryAccountInterestRate(double currentOrdinaryAccountInterestRate) {
        this.currentOrdinaryAccountInterestRate = currentOrdinaryAccountInterestRate;
    }

    public void updateCurrentTemporaryAccountInterestRate(double currentTemporaryAccountInterestRate) {
        this.currentTemporaryAccountInterestRate = currentTemporaryAccountInterestRate;
    }
}
