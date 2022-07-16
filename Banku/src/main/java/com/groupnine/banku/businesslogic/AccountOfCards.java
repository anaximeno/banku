package com.groupnine.banku.businesslogic;

import java.util.List;

public abstract class AccountOfCards extends Account {
    protected List<Card> cards;

    public AccountOfCards(String accountNumber, String accountName, AccountOwner owner, Double availableBalance, Double interestRate, List<Card> cards) {
        super(accountNumber, accountName, owner, availableBalance, interestRate);
        this.cards = cards;
    }

    /// Used to add a new card to the card list
    public void addCard(ParticularAccountOwner owner, String Id) {
        for (Card c : cards) {
            if (c.getID().equals(Id)) {
                // If card already inserted
                // return
                return;
            }
        }
        // If card was not inserted before
        // add it to the list
        this.cards.add(new Card(Id, owner, this));
    }

    /// Used to get a card from the list given its ID
    public Card getCard(String cardID) {
        for (Card c : cards) {
            // If card with ID equal with the given one
            // is found, return the card.
            if (c.getID().equals(cardID)) {
                return c;
            }
        }
        // If not found, return null
        return null;
    }

    /// Returns a reference to the card list.
    public List<Card> getAllCards() {
        return this.cards;
    }
}
