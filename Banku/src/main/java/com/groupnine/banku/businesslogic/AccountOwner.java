package com.groupnine.banku.businesslogic;

public class AccountOwner {
    private String name;
    private String NIF;
    private String address;


    public AccountOwner(String name, String NIF, String address) {
        this.name = name;
        this.NIF = NIF;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
