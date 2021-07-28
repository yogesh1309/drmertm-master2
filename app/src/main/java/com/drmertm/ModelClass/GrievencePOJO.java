package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GrievencePOJO implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("forward_to_id")
    @Expose
    private String forwardToId;
    @SerializedName("forward_from_id")
    @Expose
    private String forwardFromId;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("department_id")
    @Expose
    private String departmentId;
    @SerializedName("department_is_colony")
    @Expose
    private String departmentIsColony;
    @SerializedName("working_city_id")
    @Expose
    private String workingCityId;
    @SerializedName("colony_id")
    @Expose
    private String colonyId;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("grievancetype_id")
    @Expose
    private String grievancetypeId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("attach_status")
    @Expose
    private String attachStatus;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("current_office")
    @Expose
    private String currentOffice;
    @SerializedName("station_id")
    @Expose
    private String stationId;
    @SerializedName("residential_address")
    @Expose
    private String residentialAddress;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("forward_main_id")
    @Expose
    private String forwardMainId;
    @SerializedName("return_reason_id")
    @Expose
    private String returnReasonId;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("replyattachment")
    @Expose
    private String replyattachment;
    @SerializedName("return_date")
    @Expose
    private String returnDate;
    @SerializedName("complete_date")
    @Expose
    private String completeDate;
    @SerializedName("withdrawal_date")
    @Expose
    private String withdrawalDate;
    @SerializedName("forward_date")
    @Expose
    private String forwardDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("apply_date")
    @Expose
    private String applyDate;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("my_department_name")
    @Expose
    private String myDepartmentName;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("grievancetype_name")
    @Expose
    private String grievancetypeName;
    @SerializedName("station_name")
    @Expose
    private String stationName;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("emp_mobile_number")
    @Expose
    private String empMobileNumber;
    @SerializedName("designation_name")
    @Expose
    private String designationName;

    public GrievencePOJO(String id, String uniqueId, String forwardToId, String forwardFromId, String empId, String departmentId, String departmentIsColony, String workingCityId, String colonyId, String sectionId, String grievancetypeId, String description, String attachStatus, String attachment, String currentOffice, String stationId, String residentialAddress, String stateId, String cityId, String pin, String forwardMainId, String returnReasonId, String remark, String replyattachment, String returnDate, String completeDate, String withdrawalDate, String forwardDate, String status, String applyDate, String createdOn, String updatedOn, String myDepartmentName, String departmentName, String sectionName, String grievancetypeName, String stationName, String stateName, String cityName, String empMobileNumber, String designationName) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.forwardToId = forwardToId;
        this.forwardFromId = forwardFromId;
        this.empId = empId;
        this.departmentId = departmentId;
        this.departmentIsColony = departmentIsColony;
        this.workingCityId = workingCityId;
        this.colonyId = colonyId;
        this.sectionId = sectionId;
        this.grievancetypeId = grievancetypeId;
        this.description = description;
        this.attachStatus = attachStatus;
        this.attachment = attachment;
        this.currentOffice = currentOffice;
        this.stationId = stationId;
        this.residentialAddress = residentialAddress;
        this.stateId = stateId;
        this.cityId = cityId;
        this.pin = pin;
        this.forwardMainId = forwardMainId;
        this.returnReasonId = returnReasonId;
        this.remark = remark;
        this.replyattachment = replyattachment;
        this.returnDate = returnDate;
        this.completeDate = completeDate;
        this.withdrawalDate = withdrawalDate;
        this.forwardDate = forwardDate;
        this.status = status;
        this.applyDate = applyDate;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.myDepartmentName = myDepartmentName;
        this.departmentName = departmentName;
        this.sectionName = sectionName;
        this.grievancetypeName = grievancetypeName;
        this.stationName = stationName;
        this.stateName = stateName;
        this.cityName = cityName;
        this.empMobileNumber = empMobileNumber;
        this.designationName = designationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getForwardToId() {
        return forwardToId;
    }

    public void setForwardToId(String forwardToId) {
        this.forwardToId = forwardToId;
    }

    public String getForwardFromId() {
        return forwardFromId;
    }

    public void setForwardFromId(String forwardFromId) {
        this.forwardFromId = forwardFromId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentIsColony() {
        return departmentIsColony;
    }

    public void setDepartmentIsColony(String departmentIsColony) {
        this.departmentIsColony = departmentIsColony;
    }

    public String getWorkingCityId() {
        return workingCityId;
    }

    public void setWorkingCityId(String workingCityId) {
        this.workingCityId = workingCityId;
    }

    public String getColonyId() {
        return colonyId;
    }

    public void setColonyId(String colonyId) {
        this.colonyId = colonyId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getGrievancetypeId() {
        return grievancetypeId;
    }

    public void setGrievancetypeId(String grievancetypeId) {
        this.grievancetypeId = grievancetypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachStatus() {
        return attachStatus;
    }

    public void setAttachStatus(String attachStatus) {
        this.attachStatus = attachStatus;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCurrentOffice() {
        return currentOffice;
    }

    public void setCurrentOffice(String currentOffice) {
        this.currentOffice = currentOffice;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getForwardMainId() {
        return forwardMainId;
    }

    public void setForwardMainId(String forwardMainId) {
        this.forwardMainId = forwardMainId;
    }

    public String getReturnReasonId() {
        return returnReasonId;
    }

    public void setReturnReasonId(String returnReasonId) {
        this.returnReasonId = returnReasonId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReplyattachment() {
        return replyattachment;
    }

    public void setReplyattachment(String replyattachment) {
        this.replyattachment = replyattachment;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(String withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public String getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(String forwardDate) {
        this.forwardDate = forwardDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getMyDepartmentName() {
        return myDepartmentName;
    }

    public void setMyDepartmentName(String myDepartmentName) {
        this.myDepartmentName = myDepartmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGrievancetypeName() {
        return grievancetypeName;
    }

    public void setGrievancetypeName(String grievancetypeName) {
        this.grievancetypeName = grievancetypeName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getEmpMobileNumber() {
        return empMobileNumber;
    }

    public void setEmpMobileNumber(String empMobileNumber) {
        this.empMobileNumber = empMobileNumber;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }
}
