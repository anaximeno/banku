package com.groupnine.banku.businesslogic;

import com.groupnine.banku.miscellaneous.Misc;

import java.util.ArrayList;

public class EnterpriseAccountOwner extends AccountOwner {
    final private ArrayList<EnterprisePartner> partners;
    final private ArrayList<EnterpriseAccount> accounts;

    public EnterpriseAccountOwner(String ownerID, String name, String NIF, String address, ArrayList<EnterprisePartner> partners, ArrayList<EnterpriseAccount> accounts) {
        super(ownerID, name, NIF, address);
        this.partners = partners;
        this.accounts = accounts;
    }

    public EnterpriseAccountOwner(String  ownerID, String name, String NIF, String address, ArrayList<EnterprisePartner> partners) {
        this(ownerID, name, NIF, address, partners, new ArrayList<EnterpriseAccount>());
    }
    
    public EnterpriseAccountOwner(String ownerID, String name, String NIF, String address) {
        super(ownerID, name, NIF, address);
        this.partners = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }


    //get a list of accounts the Enterprise own
    public ArrayList<EnterpriseAccount> getAccounts() {
        return this.accounts;
    }

    //add an account to the Enterprise list accounts
    public void addAccount (EnterpriseAccount account) {
        this.accounts.add(account);
    }

    //remove an account from the Enterprise list accounts
    public boolean removeAccount (String accountNumber) {
        boolean result = false;
        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getAccountNumber().equals(accountNumber)){
                accounts.remove(i);
                return result;
            }
        }
        return result;
    }

    public void addPartner (EnterprisePartner partner)
    /* Adiciona um novo parceiro para a lista de EnterprisePartners */
    {
        if (partner != null) {
            for (EnterprisePartner p : partners) {
                if (p.getCNI().equals(partner.getCNI())) {
                    Misc.log("Trying to add partner with ID " + p.getCNI() + " more than once.", Misc.LogType.ERROR);
                    return;
                }
            }
            partners.add(partner);
        }
    }

    //remove a partner from the Enterprise list partners
    public boolean removePartner (String partnerCNI) {
        boolean result = false;
        for (int i = 0; i < partners.size(); i++){
            if (partners.get(i).getCNI().equals(partnerCNI)){
                partners.remove(partners.get(i));
                result = true;
            }
        }
        return result;
    }

    //get a list of the Enterprise's partners
    public ArrayList<EnterprisePartner> getPartners(){
        return this.partners;
    }
}
