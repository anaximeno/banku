package com.groupnine.banku;

import java.util.ArrayList;

public class ParticularAccountOwner  extends AccountOwner{
     private String lastName;
     private String id;
     private String nationality;
     private ArrayList<ParticularsAccount> accounts;
     private ArrayList<OrdinaryParticularAccount> associatedAccounts;

     public ParticularAccountOwner(String name, String NIF, String address, String lastName, String id, String nationality) {
          super(name, NIF, address);
          this.lastName = lastName;
          this.id = id;
          this.nationality = nationality;
          this.accounts = new ArrayList<>();
          this.associatedAccounts = new ArrayList<>();
     }

     public String getLastName() {
          return lastName;
     }

     public void setLastName(String lastName) {
          this.lastName = lastName;
     }

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getNationality() {
          return nationality;
     }

     public void setNationality(String nationality) {
          this.nationality = nationality;
     }
}