package com.groupnine.banku.businesslogic;

import java.util.ArrayList;

public class AccountOwnerFactory extends SequentiableFactory {
    public AccountOwnerFactory() {
        super();
    }

    //public AccountOwner createAccount ()
    public EnterpriseAccountOwner createEnterpriseAccountOwner (String name, String NIF, String address,
                                                           ArrayList<EnterprisePartner> partners,
                                                           ArrayList<EnterpriseAccount> accounts){
        EnterpriseAccountOwner eAOwner = new EnterpriseAccountOwner(getEncodedSequentialId(), name, NIF, address, partners, accounts);
        return eAOwner;
    }

    public EnterpriseAccountOwner createEnterpriseAccountOwner (String name, String NIF, String address,
                                                           ArrayList<EnterprisePartner> partners){
        EnterpriseAccountOwner eAOwner =  new EnterpriseAccountOwner(getEncodedSequentialId(), name, NIF, address, partners);
        return eAOwner;
    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String NIF, String address, String lastname,
                                                                String id, String nationality, ArrayList<Account> accounts,
                                                                ArrayList<Account> associatedAccounts){
        ParticularAccountOwner pAOwner = new ParticularAccountOwner(getEncodedSequentialId(), name, NIF, address, lastname,nationality,
                accounts, associatedAccounts);
        return  pAOwner;
    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String NIF, String address, String lastname,
                                                                String id, String nationality, ArrayList<Account> accounts){
        ParticularAccountOwner pAOwner = new ParticularAccountOwner(getEncodedSequentialId(), name, NIF, address, lastname, id, nationality, accounts);
        return pAOwner;

    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String NIF, String address, String lastName,
                                                                String id, String nationality){
        ParticularAccountOwner pAOwner = new ParticularAccountOwner(getEncodedSequentialId(), name, NIF, address, lastName, id, nationality);
        return pAOwner;
    }
}
