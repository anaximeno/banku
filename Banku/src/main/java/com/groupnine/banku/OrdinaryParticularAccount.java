package com.groupnine.banku;
import java.util.ArrayList;

public class OrdinaryParticularAccount extends ParticularAccount{
    private ArrayList<Card> cards;
    private ParticularAccountOwner minorAccountAssociate;


    public OrdinaryParticularAccount(ParticularAccountOwner owner, ParticularAccountOwner minorAccountAssociate) {
        super(owner);
        this.cards = cards;
        this.minorAccountAssociate = minorAccountAssociate;
        this.ArrayList<Card> cards = new ArrayList<Card>();
    }

    public ParticularAccountOwner getMinorAccountAssociate() {
        return minorAccountAssociate;
    }

    public void setMinorAccountAssociate(ParticularAccountOwner minorAccountAssociate) {
        this.minorAccountAssociate = minorAccountAssociate;
    }
}
