package com.groupnine.banku.businesslogic;

import java.util.List;

public abstract class AccountOfCards extends Account {
    protected List<Card> cards;

    public AccountOfCards(String accountNumber, String accountName, AccountOwner owner, Double availableBalance, Double interestRate, List<Card> cards) {
        super(accountNumber, accountName, owner, availableBalance, interestRate);
        this.cards = cards;
    }

    /// Adicionar um novo cartao a lista
    public void addCard(ParticularAccountOwner owner, String Id) {
        for (Card c : cards) {
            if (c.getID().equals(Id)) {
                //  Se o cartao ja foi inserido
                // return
                return;
            }
        }
        // Se o cartao nao esta inserido na lista ainda
        // add it to the list
        this.cards.add(new Card(Id, owner, this));
    }

    /// Obter um cartao da lista atraves do seu id
    public Card getCard(String cardID) {
        for (Card c : cards) {
            // Se o id do cartao e igual ao id dado
            // e encontrado, return o cartao.
            if (c.getID().equals(cardID)) {
                return c;
            }
        }
        // se nao foi encontrado retornar null
        return null;
    }

    /// Retorna a lista de cartoes
    public List<Card> getAllCards() {
        return this.cards;
    }
}
