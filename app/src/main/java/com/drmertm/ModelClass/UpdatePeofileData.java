
package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePeofileData {

    @SerializedName("SRNO")
    @Expose
    private String sRNO;
    @SerializedName("BILLUNIT")
    @Expose
    private String bILLUNIT;
    @SerializedName("EMPNO")
    @Expose
    private String eMPNO;
    @SerializedName("EMPNAME")
    @Expose
    private String eMPNAME;
    @SerializedName("FATHERNAME")
    @Expose
    private String fATHERNAME;
    @SerializedName("SEX")
    @Expose
    private String sEX;
    @SerializedName("DESIGCODE")
    @Expose
    private String dESIGCODE;
    @SerializedName("DEPTCODE")
    @Expose
    private String dEPTCODE;
    @SerializedName("PAN")
    @Expose
    private String pAN;
    @SerializedName("BIRTHDATE")
    @Expose
    private String bIRTHDATE;
    @SerializedName("RLYJOINDATE")
    @Expose
    private String rLYJOINDATE;
    @SerializedName("RETIREMENTDATE")
    @Expose
    private String rETIREMENTDATE;
    @SerializedName("PAYRATE")
    @Expose
    private String pAYRATE;
    @SerializedName("STATIONCODE")
    @Expose
    private String sTATIONCODE;
    @SerializedName("SERVICESTATUS")
    @Expose
    private String sERVICESTATUS;
    @SerializedName("pay_category")
    @Expose
    private String payCategory;
    @SerializedName("staff")
    @Expose
    private String staff;
    @SerializedName("signup_otp")
    @Expose
    private String signupOtp;
    @SerializedName("designation_name")
    @Expose
    private String designationName;

    public String getSRNO() {
        return sRNO;
    }

    public void setSRNO(String sRNO) {
        this.sRNO = sRNO;
    }

    public String getBILLUNIT() {
        return bILLUNIT;
    }

    public void setBILLUNIT(String bILLUNIT) {
        this.bILLUNIT = bILLUNIT;
    }

    public String getEMPNO() {
        return eMPNO;
    }

    public void setEMPNO(String eMPNO) {
        this.eMPNO = eMPNO;
    }

    public String getEMPNAME() {
        return eMPNAME;
    }

    public void setEMPNAME(String eMPNAME) {
        this.eMPNAME = eMPNAME;
    }

    public String getFATHERNAME() {
        return fATHERNAME;
    }

    public void setFATHERNAME(String fATHERNAME) {
        this.fATHERNAME = fATHERNAME;
    }

    public String getSEX() {
        return sEX;
    }

    public void setSEX(String sEX) {
        this.sEX = sEX;
    }

    public String getDESIGCODE() {
        return dESIGCODE;
    }

    public void setDESIGCODE(String dESIGCODE) {
        this.dESIGCODE = dESIGCODE;
    }

    public String getDEPTCODE() {
        return dEPTCODE;
    }

    public void setDEPTCODE(String dEPTCODE) {
        this.dEPTCODE = dEPTCODE;
    }

    public String getPAN() {
        return pAN;
    }

    public void setPAN(String pAN) {
        this.pAN = pAN;
    }

    public String getBIRTHDATE() {
        return bIRTHDATE;
    }

    public void setBIRTHDATE(String bIRTHDATE) {
        this.bIRTHDATE = bIRTHDATE;
    }

    public String getRLYJOINDATE() {
        return rLYJOINDATE;
    }

    public void setRLYJOINDATE(String rLYJOINDATE) {
        this.rLYJOINDATE = rLYJOINDATE;
    }

    public String getRETIREMENTDATE() {
        return rETIREMENTDATE;
    }

    public void setRETIREMENTDATE(String rETIREMENTDATE) {
        this.rETIREMENTDATE = rETIREMENTDATE;
    }

    public String getPAYRATE() {
        return pAYRATE;
    }

    public void setPAYRATE(String pAYRATE) {
        this.pAYRATE = pAYRATE;
    }

    public String getSTATIONCODE() {
        return sTATIONCODE;
    }

    public void setSTATIONCODE(String sTATIONCODE) {
        this.sTATIONCODE = sTATIONCODE;
    }

    public String getSERVICESTATUS() {
        return sERVICESTATUS;
    }

    public void setSERVICESTATUS(String sERVICESTATUS) {
        this.sERVICESTATUS = sERVICESTATUS;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getSignupOtp() {
        return signupOtp;
    }

    public void setSignupOtp(String signupOtp) {
        this.signupOtp = signupOtp;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

}
