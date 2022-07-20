package com.groupnine.banku.businesslogic;

import java.util.ArrayList;
// A Factory Class that helps the organization of the accountsOwner's construction
// This class is abstract because only one AccountFactory will be used for all accounts
// so there's no need for instantiation on this class
public class AccountOwnerFactory {

    private static int ownerIDCounter = 0;

    //functions that create an EnterpriseAccountOwner
    public static EnterpriseAccountOwner createEnterpriseAccountOwner (String name, String NIF, String address,
                                                           ArrayList<EnterprisePartners> partners,
                                                           ArrayList<EnterpriseAccount> accounts){
        EnterpriseAccountOwner eAOwner = new EnterpriseAccountOwner(getNewOwnerIDEncoded(), name, NIF, address, partners, accounts);
        return eAOwner;
    }

    public static EnterpriseAccountOwner createEnterpriseAccountOwner (String name, String NIF, String address,
                                                           ArrayList<EnterprisePartners> partners){
        EnterpriseAccountOwner eAOwner =  new EnterpriseAccountOwner(getNewOwnerIDEncoded(), name, NIF, address, partners);
        return eAOwner;
    }

    //function that create an ParticularAccountOwner
    public static ParticularAccountOwner createParticularAccountOwner (String name, String lastName, String address, String NIF,
                                                                       String nationality, ArrayList<Account> accounts,
                                                                ArrayList<Account> associatedAccounts){
        ParticularAccountOwner pAOwner = new ParticularAccountOwner(getNewOwnerIDEncoded(), name, lastName, address, NIF, nationality,
                accounts, associatedAccounts);
        return  pAOwner;
    }

    public static ParticularAccountOwner createParticularAccountOwner (String name, String lastName, String address, String NIF,
                                                                       String nationality, ArrayList<Account> accounts){
        ParticularAccountOwner pAOwner = new ParticularAccountOwner(getNewOwnerIDEncoded(), name, lastName, address, NIF, nationality, accounts);
        return pAOwner;

    }

    public static ParticularAccountOwner createParticularAccountOwner (String name, String lastName, String address, String NIF,
                                                                       String nationality){
        ParticularAccountOwner pAOwner = new ParticularAccountOwner(getNewOwnerIDEncoded(), name, lastName, address, NIF, nationality);
        return pAOwner;
    }

    private static void increaseCounter() {
        ownerIDCounter += 1;
    }

    //function that put a counter with 13 digits
    private static String getNewOwnerIDEncoded() {
        // Increase the counter
        increaseCounter();
        
        String num = String.valueOf(ownerIDCounter);

        // accu - is the accumulator, it accumulates the number of decimal places
        // which is then reduced until the value from accu is less than the accountNumberCounter
        double accu = Math.pow(10d, 12d);
        for (int x = ownerIDCounter ; x < accu ; accu = accu / 10) {
            num = "0" + num;
        }

        return num;
    }

}
