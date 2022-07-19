package com.groupnine.banku.businesslogic;

import java.util.ArrayList;

public class AccountOwnerFactory {

    private static int ownerIDCounter = 0;

    //public AccountOwner createAccount ()
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
