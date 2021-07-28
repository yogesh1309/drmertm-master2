
package com.drmertm.ModelClass;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdCardStatusData implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("serial_no")
    @Expose
    private String serialNo;
    @SerializedName("emp_no")
    @Expose
    private String empNo;
    @SerializedName("emp_name")
    @Expose
    private String empName;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("billunit_id")
    @Expose
    private String billunitId;
    @SerializedName("station_id")
    @Expose
    private String stationId;
    @SerializedName("department_id")
    @Expose
    private String departmentId;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("complexion")
    @Expose
    private String complexion;
    @SerializedName("identification_mark")
    @Expose
    private String identificationMark;
    @SerializedName("issuing_authority")
    @Expose
    private String issuingAuthority;
    @SerializedName("authority_sign_image")
    @Expose
    private String authoritySignImage;
    @SerializedName("emp_image_id_card")
    @Expose
    private String empImageIdCard;
    @SerializedName("emp_sign_image")
    @Expose
    private String empSignImage;
    @SerializedName("railway_id_proof")
    @Expose
    private String railwayIdProof;
    @SerializedName("paylevel")
    @Expose
    private String paylevel;
    @SerializedName("card_color_code")
    @Expose
    private String cardColorCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("submit_status")
    @Expose
    private String submitStatus;
    @SerializedName("clerk_status")
    @Expose
    private String clerkStatus;
    @SerializedName("clerk_emp_no")
    @Expose
    private String clerkEmpNo;
    @SerializedName("clerk_forward_date")
    @Expose
    private String clerkForwardDate;
    @SerializedName("reject_reason")
    @Expose
    private String rejectReason;
    @SerializedName("reject_by")
    @Expose
    private String rejectBy;
    @SerializedName("id_status")
    @Expose
    private String idStatus;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("railway_id_proof_array")
    @Expose
    private List<String> railwayIdProofArray = null;
    @SerializedName("billunit_name")
    @Expose
    private String billunitName;
    @SerializedName("station_name")
    @Expose
    private String stationName;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getBillunitId() {
        return billunitId;
    }

    public void setBillunitId(String billunitId) {
        this.billunitId = billunitId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getComplexion() {
        return complexion;
    }

    public void setComplexion(String complexion) {
        this.complexion = complexion;
    }

    public String getIdentificationMark() {
        return identificationMark;
    }

    public void setIdentificationMark(String identificationMark) {
        this.identificationMark = identificationMark;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getAuthoritySignImage() {
        return authoritySignImage;
    }

    public void setAuthoritySignImage(String authoritySignImage) {
        this.authoritySignImage = authoritySignImage;
    }

    public String getEmpImageIdCard() {
        return empImageIdCard;
    }

    public void setEmpImageIdCard(String empImageIdCard) {
        this.empImageIdCard = empImageIdCard;
    }

    public String getEmpSignImage() {
        return empSignImage;
    }

    public void setEmpSignImage(String empSignImage) {
        this.empSignImage = empSignImage;
    }

    public String getRailwayIdProof() {
        return railwayIdProof;
    }

    public void setRailwayIdProof(String railwayIdProof) {
        this.railwayIdProof = railwayIdProof;
    }

    public String getPaylevel() {
        return paylevel;
    }

    public void setPaylevel(String paylevel) {
        this.paylevel = paylevel;
    }

    public String getCardColorCode() {
        return cardColorCode;
    }

    public void setCardColorCode(String cardColorCode) {
        this.cardColorCode = cardColorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getClerkStatus() {
        return clerkStatus;
    }

    public void setClerkStatus(String clerkStatus) {
        this.clerkStatus = clerkStatus;
    }

    public String getClerkEmpNo() {
        return clerkEmpNo;
    }

    public void setClerkEmpNo(String clerkEmpNo) {
        this.clerkEmpNo = clerkEmpNo;
    }

    public String getClerkForwardDate() {
        return clerkForwardDate;
    }

    public void setClerkForwardDate(String clerkForwardDate) {
        this.clerkForwardDate = clerkForwardDate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRejectBy() {
        return rejectBy;
    }

    public void setRejectBy(String rejectBy) {
        this.rejectBy = rejectBy;
    }

    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
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

    public List<String> getRailwayIdProofArray() {
        return railwayIdProofArray;
    }

    public void setRailwayIdProofArray(List<String> railwayIdProofArray) {
        this.railwayIdProofArray = railwayIdProofArray;
    }

    public String getBillunitName() {
        return billunitName;
    }

    public void setBillunitName(String billunitName) {
        this.billunitName = billunitName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
