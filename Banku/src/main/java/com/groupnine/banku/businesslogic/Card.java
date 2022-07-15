package com.groupnine.banku.businesslogic;

public class Card {
    private String numberID;
    private ParticularAccountOwner owner;
    private Account account;

    public Card(String numberID, ParticularAccountOwner owner, Account account) {
        if (numberID.isEmpty() != true && owner != null && account != null){
            this.numberID = numberID;
            this.owner = owner;
            this.account = account;
        }
    }
    public ParticularAccountOwner getOwner(){
        return owner;
    }
    public void setOwner(ParticularAccountOwner owner) {
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
