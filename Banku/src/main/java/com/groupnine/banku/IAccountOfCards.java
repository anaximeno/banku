package com.groupnine.banku;

import java.util.List;

public interface IAccountOfCards {
    public void addCard(Card card);
    public Card getCard(String cardID);
    public List<Card> getAllCards();
}
