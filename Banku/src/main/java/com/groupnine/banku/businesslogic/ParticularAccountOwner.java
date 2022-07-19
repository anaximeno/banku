package com.groupnine.banku.businesslogic;

import java.util.ArrayList;

public class ParticularAccountOwner  extends AccountOwner{
     private String lastName;
     private String nationality;
     private ArrayList<Account> accounts;
     private ArrayList<Account> associatedAccounts;

     public ParticularAccountOwner(String ownerID, String name, String NIF, String address, String lastname, String nationality,
                                   ArrayList<? extends Account>accounts, ArrayList<? extends Account>associatedAccounts ){

          super(ownerID, name, NIF, address);
          this.lastName = lastname;
          this.nationality = nationality;
          this.accounts = (ArrayList<Account>) accounts;
          this.associatedAccounts = (ArrayList<Account>) associatedAccounts;
     }

     public ParticularAccountOwner(String ownerID, String name, String NIF, String address, String lastName, String id, String nationality,
                                   ArrayList<? extends Account>accounts ) {
          this(ownerID, name, NIF, address, lastName, nationality, accounts, new ArrayList<>() );
     }

     public ParticularAccountOwner(String ownerID, String name, String NIF, String address, String lastName, String id, String nationality) {
          this(ownerID, name, NIF, address, lastName, nationality, new ArrayList<>(), new ArrayList<>() );
     }


     public Double getTotalAvailableBalance(){
          Double balance = 0d;
          for (int i = 0; i < accounts.size(); i++){
               balance += accounts.get(i).getAccountBalance();
          }
          return balance;
     }

     public void addAccount(Account account){
          accounts.add(account);
     }

     public String getLastName() {
          return lastName;
     }

     public void setLastName(String lastName) {
          this.lastName = lastName;
     }

     public String getNationality() {
          return nationality;
     }

     public void setNationality(String nationality) {
          this.nationality = nationality;
     }

     public ArrayList<Account> getAccounts() {
          return accounts;
     }

     public ArrayList<Account> getAssociatedAccounts() {
          return associatedAccounts;
     }

     public String getFullName() {
          return getName() + " " + getLastName();
     }
}