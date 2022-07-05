package com.groupnine.banku;
import java.util.ArrayList;
import java.util.List;

public class OrdinaryParticularAccount extends ParticularAccount implements IAccountOfCards{
    private List<Card> cards;
    // minorAccountAssociate é o beneficiário da conta.
    private ParticularAccountOwner minorAccountAssociate;


    public OrdinaryParticularAccount(String number, String name, ParticularAccountOwner owner,
            double balance, double interestRate, List<Card> cards, ParticularAccountOwner associate) {
        super(number, name, balance, interestRate, owner);
        this.minorAccountAssociate = associate;
        this.cards = cards;
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

    @Override
    public void addCard(Card card) {
        for (Card c : cards) {
            if (c.getID().equals(card.getID())) {
                return;
            }
        }
        this.cards.add(card);
    }

    @Override
    public Card getCard(String cardID) {
        for (Card c : cards) {
            if (c.getID().equals(cardID)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Card> getAllCards() {
        return this.cards;
    }
}
