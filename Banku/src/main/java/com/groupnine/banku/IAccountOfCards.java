package com.groupnine.banku;

import java.util.List;

public interface IAccountOfCards {
    public void addCard(Card card);
    public void getCard(String cardID);
    public void getAllCards();
}
