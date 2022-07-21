package com.groupnine.banku.businesslogic;

public class EnterprisePartner {
    private String fullName;
    private String CNI;

    public EnterprisePartner(String fullName, String CNI) {
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
