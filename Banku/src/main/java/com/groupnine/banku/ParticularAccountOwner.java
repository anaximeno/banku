package com.groupnine.banku;

import java.util.ArrayList;
import java.util.List;

public class ParticularAccountOwner  extends AccountOwner{
     private String lastName;
     private String id;
     private String nationality;
     private ArrayList<Account> accounts;
     private ArrayList<Account> associatedAccounts;

     public ParticularAccountOwner(String name, String NIF, String address, String lastname, String id, String nationality,
                                   ArrayList<? extends Account>accounts, ArrayList<? extends Account>associatedAccounts ){
          super(name, NIF, address);
          this.lastName = lastname;
          this.id = id;
          this.nationality = nationality;
          this.accounts = (ArrayList<Account>) accounts;
          this.associatedAccounts = (ArrayList<Account>) associatedAccounts;
     }

     public ParticularAccountOwner(String name, String NIF, String address, String lastName, String id, String nationality,
                                   ArrayList<? extends Account>accounts ) {
          this(name, NIF, address, lastName, id, nationality, accounts, new ArrayList<>() );
     }

     public ParticularAccountOwner(String name, String NIF, String address, String lastName, String id, String nationality) {
          this(name, NIF, address, lastName, id, nationality, new ArrayList<>(), new ArrayList<>() );
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