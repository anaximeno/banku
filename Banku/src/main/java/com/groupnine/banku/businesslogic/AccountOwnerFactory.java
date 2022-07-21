package com.groupnine.banku.businesslogic;

import java.util.ArrayList;

public class AccountOwnerFactory extends SequentiableFactory {
    public AccountOwnerFactory() {
        super();
    }

    //public AccountOwner createAccount ()
    public EnterpriseAccountOwner createEnterpriseAccountOwner(String name, String NIF, String address,
                                       ArrayList<EnterprisePartner> partners, ArrayList<EnterpriseAccount> accounts)
    {
        return new EnterpriseAccountOwner(getEncodedSequentialId(), name, NIF, address, partners, accounts);
    }

    public EnterpriseAccountOwner createEnterpriseAccountOwner (String name, String NIF, String address,
                                                           ArrayList<EnterprisePartner> partners)
    {
        return new EnterpriseAccountOwner(getEncodedSequentialId(), name, NIF, address, partners);
    }

    //function that create an ParticularAccountOwner
    public ParticularAccountOwner createParticularAccountOwner(String name, String lastName, String address, String NIF,
                                       String nationality, ArrayList<Account> accounts, ArrayList<Account> associatedAccounts)
    {
        return  new ParticularAccountOwner(getEncodedSequentialId(), name, lastName, address, NIF, nationality,
                    accounts, associatedAccounts);
    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String lastName, String address, String NIF,
                                                                       String nationality, ArrayList<Account> accounts)
    {
        return new ParticularAccountOwner(getEncodedSequentialId(), name, lastName, address, NIF, nationality, accounts);
    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String lastName, String address, String NIF,
                                                                       String nationality)
    {
        return new ParticularAccountOwner(getEncodedSequentialId(), name, lastName, address, NIF, nationality);
    }
}
