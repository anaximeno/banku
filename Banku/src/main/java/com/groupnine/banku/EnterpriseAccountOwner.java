package com.groupnine.banku;

import java.util.ArrayList;

public class EnterpriseAccountOwner extends AccountOwner{
    private String enterpriseName;
    private ArrayList<EnterprisePartners> partners;
    private ArrayList<EnterpriseAccounts> accounts;

    //get a list of accounts that the Enterprise own
    public ArrayList<EnterpriseAccount> getAccounts(){

    }

    //add an account to the Enterprise list accounts
    public void addAccount (EnterpriseAccount account){

    }

    //remove an account from the Enterprise list accounts
    public void removeAccount (EnterpriseAccount){

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
    public void removePartner (EnterprisePartners){

    }
    //get a clone list of the Enterprise's partners
    /*public ArrayList<EnterprisePartners> getPartners(){
        ArrayList<EnterprisePartners> partnersList = new ArrayList<>();
        EnterprisePartners partner;
        for(int i = 0; i < partners.size(); i++){ //for(EnterprisePartner partnerList : partners)
            partner = (EnterprisePartners) partnersList.get(i).clone(partners);
            partnersList.add(partner);
        }
        return partnersList;
    }
    */

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
