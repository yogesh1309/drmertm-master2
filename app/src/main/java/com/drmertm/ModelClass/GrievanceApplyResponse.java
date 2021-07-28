
package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrievanceApplyResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("grievance_number")
    @Expose
    private String grievanceNumber;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGrievanceNumber() {
        return grievanceNumber;
    }

    public void setGrievanceNumber(String grievanceNumber) {
        this.grievanceNumber = grievanceNumber;
    }

}
