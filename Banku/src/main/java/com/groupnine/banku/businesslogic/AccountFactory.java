package com.groupnine.banku.businesslogic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Uma classe factory ajuda na organizacao na construcao de contas
// Esta classe e abstrata visto que so uma AccountFactory sera usada para todas as contas
// por isso nao e necessario uma instanciacao desta classe
public class AccountFactory extends SequentiableFactory {
    private double currentEnterpriseInterestRate = 0.01;
    private double currentOrdinaryAccountInterestRate = 0.01;
    private double currentTemporaryAccountInterestRate = 0.01;


    public AccountFactory(double particularInterest, double enterpriseInterest, double temporaryInterest) {
        currentOrdinaryAccountInterestRate = particularInterest;
        currentEnterpriseInterestRate = enterpriseInterest;
        currentTemporaryAccountInterestRate = temporaryInterest;
    }

    // funcao que cria uma EnterpriseAccount
    public EnterpriseAccount createEnterpriseAccount(
            String name, EnterpriseAccountOwner owner, ParticularAccountOwner admin, double initialBalance)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentEnterpriseInterestRate;
        return new EnterpriseAccount(accountNumber, name, owner, admin, initialBalance, interest);
    }

    public EnterpriseAccount createEnterpriseAccount(
            String name, EnterpriseAccountOwner owner, ParticularAccountOwner admin, double initialBalance,
            ArrayList<ParticularAccountOwner> users)
    {
        EnterpriseAccount account = createEnterpriseAccount(name, owner, admin, initialBalance);
        account.setAuthorizedUsers(users);
        return account;
    }

    // funcao que criam uma OrdinaryParticularAccount
    public OrdinaryParticularAccount createOrdinaryParticularAccount(
            String name, ParticularAccountOwner owner, double balance, ParticularAccountOwner associate)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentOrdinaryAccountInterestRate;
        OrdinaryParticularAccount acc = new OrdinaryParticularAccount(accountNumber, name, owner, balance, interest);
        acc.setMinorAccountAssociate(associate);
        return acc;
    }

    //functions that create an TemporaryParticularAccount funcao que cria uma TemporaryParticularAccount
    public TemporaryParticularAccount createTemporaryParticularAccount(
            String accountName, Double balance, ParticularAccountOwner owner, LocalDate expirationDate)
    {
        String accountNumber = getEncodedSequentialId();
        double interest = currentTemporaryAccountInterestRate;
        return  new TemporaryParticularAccount(accountNumber, accountName, balance, interest, owner, LocalDate.now(), expirationDate);
    }

    //funcoes que atualizam os valores das taxas de juro
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
