package com.groupnine.banku.businesslogic;
import java.util.ArrayList;
import java.util.List;

public class OrdinaryParticularAccount extends AccountOfCards {
    // minorAccountAssociate é o beneficiário da conta.
    private ParticularAccountOwner minorAccountAssociate;

    public OrdinaryParticularAccount(String number, String name, ParticularAccountOwner owner,
            double balance, double interestRate, List<Card> cards, ParticularAccountOwner associate) {
        super(number, name, owner, balance, interestRate, cards);
        this.minorAccountAssociate = associate;
    }

    public OrdinaryParticularAccount(String number, String name, ParticularAccountOwner owner,
        double balance, double interestRate, List<Card> card) {
        this(number, name, owner, balance, interestRate, card, null);
    }

    public OrdinaryParticularAccount(String number, String name, ParticularAccountOwner owner,
         double balance, double interestRate) {
        this(number, name, owner, balance, interestRate, new ArrayList<>(), null);
    }

    public ParticularAccountOwner getMinorAccountAssociate() {
        return minorAccountAssociate;
    }

    public void setMinorAccountAssociate(ParticularAccountOwner minorAccountAssociate) {
        this.minorAccountAssociate = minorAccountAssociate;
    }
}
