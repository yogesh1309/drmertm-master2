
package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeptData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("DEPTCODE")
    @Expose
    private String dEPTCODE;
    @SerializedName("DEPTLONGDESC")
    @Expose
    private String dEPTLONGDESC;
    @SerializedName("DEPTSHORTDESC")
    @Expose
    private String dEPTSHORTDESC;
    @SerializedName("SF")
    @Expose
    private String sF;
    @SerializedName("is_colony")
    @Expose
    private String isColony;
    @SerializedName("grievance_status")
    @Expose
    private String grievanceStatus;
    @SerializedName("leave_status")
    @Expose
    private String leaveStatus;
    @SerializedName("update_on_1jan")
    @Expose
    private String updateOn1jan;
    @SerializedName("update_on_1july")
    @Expose
    private String updateOn1july;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDEPTCODE() {
        return dEPTCODE;
    }

    public void setDEPTCODE(String dEPTCODE) {
        this.dEPTCODE = dEPTCODE;
    }

    public String getDEPTLONGDESC() {
        return dEPTLONGDESC;
    }

    public void setDEPTLONGDESC(String dEPTLONGDESC) {
        this.dEPTLONGDESC = dEPTLONGDESC;
    }

    public String getDEPTSHORTDESC() {
        return dEPTSHORTDESC;
    }

    public void setDEPTSHORTDESC(String dEPTSHORTDESC) {
        this.dEPTSHORTDESC = dEPTSHORTDESC;
    }

    public String getSF() {
        return sF;
    }

    public void setSF(String sF) {
        this.sF = sF;
    }

    public String getIsColony() {
        return isColony;
    }

    public void setIsColony(String isColony) {
        this.isColony = isColony;
    }

    public String getGrievanceStatus() {
        return grievanceStatus;
    }

    public void setGrievanceStatus(String grievanceStatus) {
        this.grievanceStatus = grievanceStatus;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getUpdateOn1jan() {
        return updateOn1jan;
    }

    public void setUpdateOn1jan(String updateOn1jan) {
        this.updateOn1jan = updateOn1jan;
    }

    public String getUpdateOn1july() {
        return updateOn1july;
    }

    public void setUpdateOn1july(String updateOn1july) {
        this.updateOn1july = updateOn1july;
    }

}
