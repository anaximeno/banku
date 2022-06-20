package com.groupnine.banku;

public class EnterprisePartners {
    private String fullName;
    private String CNI;



    public EnterprisePartners(String fullName, String CNI) {
        this.fullName = fullName;
        this.CNI = CNI;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCNI() {
        return CNI;
    }

    public void setCNI(String CNI) {
        this.CNI = CNI;
    }
}
