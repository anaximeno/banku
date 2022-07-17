package com.groupnine.banku.businesslogic;

import java.util.ArrayList;

public class EnterpriseAccount extends AccountOfCards {
    final static double TRANSACTION_INTEREST = 0.02;
    private ArrayList<Card> cards;
    private ParticularAccountOwner admin;
    private ArrayList<ParticularAccountOwner> authorizedUsers;

    public EnterpriseAccount(String accountNumber, String accountName, EnterpriseAccountOwner owner, ParticularAccountOwner admin,
                     double availableBalance, double interestRate, ArrayList<ParticularAccountOwner> authorizedUsers, ArrayList<Card> cards) {
        super(accountNumber, accountName, owner, availableBalance, interestRate, cards);
        this.cards = cards;
        this.admin = admin;
        this.authorizedUsers = authorizedUsers;
    }

    public EnterpriseAccount(String accountNumber, String accountName, EnterpriseAccountOwner owner, ParticularAccountOwner admin,
                             double balance, double interestRate) {
        this(accountNumber, accountName, owner, admin, balance, interestRate, new ArrayList<>(), new ArrayList<>());
    }

    public EnterpriseAccount(String accountNumber, String accountName, EnterpriseAccountOwner owner, double balance, double interestRate) {
        this(accountNumber, accountName, owner, null, balance, interestRate, new ArrayList<>(), new ArrayList<>());
    }

    public EnterpriseAccount(String accountNumber, String accountName, EnterpriseAccountOwner owner, ParticularAccountOwner admin,
                             double balance, double interestRate, ArrayList<ParticularAccountOwner> authorizedUsers) {
        this(accountNumber, accountName, owner, admin, balance, interestRate, authorizedUsers, new ArrayList<>());
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
}
