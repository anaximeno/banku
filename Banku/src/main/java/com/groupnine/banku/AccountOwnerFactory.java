package com.groupnine.banku;

import java.util.ArrayList;

public class AccountOwnerFactory {
    public EnterpriseAccount createEnterpriseAccountOwner (String name, String NIF, String address,
                                                           ArrayList<EnterprisePartners> partners,
                                                           ArrayList<ParticularAccountOwner> authorizedUsers){

    }

    public EnterpriseAccount createEnterpriseAccountOwner (String name, String NIF, String address,
                                                           ArrayList<EnterprisePartners> partners){

    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String NIF, String address,
                                                                String nationality, ArrayList<Account> accounts,
                                                                ArrayList<Account> associatedAccounts){

    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String NIF, String address,
                                                                String nationality, ArrayList<Account> accounts){

    }

    public ParticularAccountOwner createParticularAccountOwner (String name, String NIF, String address,
                                                                String nationality){

    }

}
