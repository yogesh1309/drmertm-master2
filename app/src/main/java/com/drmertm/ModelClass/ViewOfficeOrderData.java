
package com.drmertm.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewOfficeOrderData implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("department_id")
    @Expose
    private String departmentId;
    @SerializedName("tbl_head_id")
    @Expose
    private String tblHeadId;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("letter_no")
    @Expose
    private String letterNo;
    @SerializedName("letter_date")
    @Expose
    private String letterDate;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("reference_date")
    @Expose
    private String referenceDate;
    @SerializedName("issue_date")
    @Expose
    private String issueDate;
    @SerializedName("document")
    @Expose
    private String document;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("head_name")
    @Expose
    private String headName;

    public ViewOfficeOrderData(String id, String empId, String departmentId, String tblHeadId, String subject, String letterNo, String letterDate, String referenceNo, String referenceDate, String issueDate, String document, String status, String createdOn, String updatedOn, String departmentName, String headName) {
        this.id = id;
        this.empId = empId;
        this.departmentId = departmentId;
        this.tblHeadId = tblHeadId;
        this.subject = subject;
        this.letterNo = letterNo;
        this.letterDate = letterDate;
        this.referenceNo = referenceNo;
        this.referenceDate = referenceDate;
        this.issueDate = issueDate;
        this.document = document;
        this.status = status;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.departmentName = departmentName;
        this.headName = headName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTblHeadId() {
        return tblHeadId;
    }

    public void setTblHeadId(String tblHeadId) {
        this.tblHeadId = tblHeadId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLetterNo() {
        return letterNo;
    }

    public void setLetterNo(String letterNo) {
        this.letterNo = letterNo;
    }

    public String getLetterDate() {
        return letterDate;
    }

    public void setLetterDate(String letterDate) {
        this.letterDate = letterDate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

}
