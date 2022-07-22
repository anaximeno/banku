package com.groupnine.banku.businesslogic;

import java.util.ArrayList;

// Uma classe factory ajuda na organizacao na construcao de clientes
// Esta classe e abstrata visto que so uma AccountOwnerFactory sera usada para todas os clientes
// por isso nao e necessario uma instanciacao desta classe

public class AccountOwnerFactory extends SequentiableFactory {
    public AccountOwnerFactory() {
        super();
    }


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
