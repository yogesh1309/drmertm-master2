
package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailData {

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
    @SerializedName("HRMSID")
    @Expose
    private String hRMSID;
    @SerializedName("pay_category")
    @Expose
    private String payCategory;
    @SerializedName("staff")
    @Expose
    private String staff;
    @SerializedName("PAYLEVEL")
    @Expose
    private String PAYLEVEL;



    @SerializedName("signup_otp")
    @Expose
    private String signupOtp;
    @SerializedName("designation_name")
    @Expose
    private String designationName;

    @SerializedName("department_name")
    @Expose
    private String department_name;


    @SerializedName("department_id")
    @Expose
    private String department_id;

    @SerializedName("station_name")
    @Expose
    private String station_name;

    @SerializedName("station_id")
    @Expose
    private String station_id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("marque_text1")
    @Expose
    private String marqueText1;
    @SerializedName("marque_text2")
    @Expose
    private String marqueText2;

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

    public String getHRMSID() {
        return hRMSID;
    }

    public void setHRMSID(String hRMSID) {
        this.hRMSID = hRMSID;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMarqueText1() {
        return marqueText1;
    }

    public void setMarqueText1(String marqueText1) {
        this.marqueText1 = marqueText1;
    }

    public String getMarqueText2() {
        return marqueText2;
    }

    public void setMarqueText2(String marqueText2) {
        this.marqueText2 = marqueText2;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }
    public String getPAYLEVEL() {
        return PAYLEVEL;
    }

    public void setPAYLEVEL(String PAYLEVEL) {
        this.PAYLEVEL = PAYLEVEL;
    }
}
