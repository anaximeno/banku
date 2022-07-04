package com.groupnine.banku;
import java.util.ArrayList;
import java.util.List;

public class OrdinaryParticularAccount extends ParticularAccount implements IAccountOfCards{
    private ArrayList<Card> cards;
    private ParticularAccountOwner minorAccountAssociate;


    public OrdinaryParticularAccount(ParticularAccountOwner owner, ParticularAccountOwner minorAccountAssociate) {
        super(owner);
        this.cards = cards;
        this.minorAccountAssociate = minorAccountAssociate;
        this.ArrayList<Card> cards = new ArrayList<>();
    }

    public ParticularAccountOwner getMinorAccountAssociate() {

        return minorAccountAssociate;
    }

    public void setMinorAccountAssociate(ParticularAccountOwner minorAccountAssociate) {
        this.minorAccountAssociate = minorAccountAssociate;
    }

    public OrdinaryParticularAccount(String number, String name, Double balance, Double interestRate, List<Card> cards, ParticularAccountOwner associate){
        super(owner);

    }

    @Override
    public void addCard(Card card) {

    }

    @Override
    public void getCard(String cardID) {

    }

    @Override
    public void getAllCards() {

    }
}
