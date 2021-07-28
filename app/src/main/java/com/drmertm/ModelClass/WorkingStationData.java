
package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkingStationData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("STNCODE")
    @Expose
    private String sTNCODE;
    @SerializedName("STNSHORTDESC")
    @Expose
    private String sTNSHORTDESC;
    @SerializedName("STNLONGDSCP")
    @Expose
    private String sTNLONGDSCP;
    @SerializedName("STATE")
    @Expose
    private String sTATE;
    @SerializedName("DIVISIONCODE")
    @Expose
    private String dIVISIONCODE;
    @SerializedName("RLYCODE")
    @Expose
    private String rLYCODE;
    @SerializedName("OFFICECODE")
    @Expose
    private String oFFICECODE;
    @SerializedName("STNINDEX")
    @Expose
    private String sTNINDEX;
    @SerializedName("SF")
    @Expose
    private String sF;
    @SerializedName("BILLUNIT")
    @Expose
    private String bILLUNIT;
    @SerializedName("AU")
    @Expose
    private String aU;
    @SerializedName("DEPTCODE")
    @Expose
    private String dEPTCODE;
    @SerializedName("department_id")
    @Expose
    private String departmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSTNCODE() {
        return sTNCODE;
    }

    public void setSTNCODE(String sTNCODE) {
        this.sTNCODE = sTNCODE;
    }

    public String getSTNSHORTDESC() {
        return sTNSHORTDESC;
    }

    public void setSTNSHORTDESC(String sTNSHORTDESC) {
        this.sTNSHORTDESC = sTNSHORTDESC;
    }

    public String getSTNLONGDSCP() {
        return sTNLONGDSCP;
    }

    public void setSTNLONGDSCP(String sTNLONGDSCP) {
        this.sTNLONGDSCP = sTNLONGDSCP;
    }

    public String getSTATE() {
        return sTATE;
    }

    public void setSTATE(String sTATE) {
        this.sTATE = sTATE;
    }

    public String getDIVISIONCODE() {
        return dIVISIONCODE;
    }

    public void setDIVISIONCODE(String dIVISIONCODE) {
        this.dIVISIONCODE = dIVISIONCODE;
    }

    public String getRLYCODE() {
        return rLYCODE;
    }

    public void setRLYCODE(String rLYCODE) {
        this.rLYCODE = rLYCODE;
    }

    public String getOFFICECODE() {
        return oFFICECODE;
    }

    public void setOFFICECODE(String oFFICECODE) {
        this.oFFICECODE = oFFICECODE;
    }

    public String getSTNINDEX() {
        return sTNINDEX;
    }

    public void setSTNINDEX(String sTNINDEX) {
        this.sTNINDEX = sTNINDEX;
    }

    public String getSF() {
        return sF;
    }

    public void setSF(String sF) {
        this.sF = sF;
    }

    public String getBILLUNIT() {
        return bILLUNIT;
    }

    public void setBILLUNIT(String bILLUNIT) {
        this.bILLUNIT = bILLUNIT;
    }

    public String getAU() {
        return aU;
    }

    public void setAU(String aU) {
        this.aU = aU;
    }

    public String getDEPTCODE() {
        return dEPTCODE;
    }

    public void setDEPTCODE(String dEPTCODE) {
        this.dEPTCODE = dEPTCODE;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

}
