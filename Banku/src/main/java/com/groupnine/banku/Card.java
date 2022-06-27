package com.groupnine.banku;

public class Card {
    private String numberID;
    private String owner;
    private Account account;

    public Card(String numberID, String owner, Account account) {
        this.numberID = numberID;
        this.owner = owner;
        this.account = account;
    }
    public String getOwner(){
        return owner;
    }
    public void setOwner(ParticularAccountOwner owner){
        this.owner = owner;
    }
    public String getID(){
        return numberID;
    }
    public void setID(String id){
        this.numberID = id;
    }
    public Account getLinkedAccount(){
        return account;
    }
}
