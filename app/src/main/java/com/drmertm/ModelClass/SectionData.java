
package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("STATIONCODE")
    @Expose
    private String sTATIONCODE;
    @SerializedName("BILLUNIT")
    @Expose
    private String bILLUNIT;
    @SerializedName("department_id")
    @Expose
    private String departmentId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSTATIONCODE() {
        return sTATIONCODE;
    }

    public void setSTATIONCODE(String sTATIONCODE) {
        this.sTATIONCODE = sTATIONCODE;
    }

    public String getBILLUNIT() {
        return bILLUNIT;
    }

    public void setBILLUNIT(String bILLUNIT) {
        this.bILLUNIT = bILLUNIT;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
