package com.groupnine.banku;

public abstract class ParticularAccount extends Account{
    private ParticularAccountOwner owner;

    public ParticularAccount(ParticularAccountOwner owner) {
        this.owner = owner;
    }

    public ParticularAccountOwner getOwner() {
        return owner;
    }

    public void setOwner(ParticularAccountOwner owner) {
        this.owner = owner;
    }
}
