package com.groupnine.banku;

import java.util.ArrayList;

public class EnterpriseAccountOwner extends AccountOwner{
    private String enterpriseName;
    private ArrayList<EnterprisePartners> partners;
    private ArrayList<EnterpriseAccount> accounts;

    //get a list of accounts that the Enterprise own
    public ArrayList<EnterpriseAccount> getAccounts(){
        return this.accounts;
    }

    //add an account to the Enterprise list accounts
    public void addAccount (EnterpriseAccount account){
        this.accounts.add(account);
    }

    //remove an account from the Enterprise list accounts
    public boolean removeAccount (EnterpriseAccount account){
        boolean result = false;
        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).equals(account) ){
                accounts.remove(account);
                return result;
            }
        }
        return result;
    }


    //add a partner to the list of the Enterprise' partners
    public void addPartner (EnterprisePartners partner){
        if (partner != NULL){
            partners.add(partner);
        }else{
            System.out.println("Partner object does not exist");
        }

    }
    //remove a partner from the Enterprise list partners
    public boolean removePartner (String partnerCNI){
        boolean result = false;
        for (int i = 0; i < partners.size(); i++){
            if (partners.get(i).getCNI().equals(partnerCNI)){
                partners.remove(partners.get(i));
                return result;
            }
        }
        return result;
    }
    //get a list of the Enterprise's partners
    public ArrayList<EnterprisePartners> getPartners(){
        return this.partners;
    }


    public EnterpriseAccountOwner(String name, String NIF, String address, String enterpriseName) {
        super(name, NIF, address);
        this.enterpriseName = enterpriseName;
        this.partners = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
