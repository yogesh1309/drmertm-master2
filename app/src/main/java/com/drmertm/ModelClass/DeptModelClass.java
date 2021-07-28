package com.drmertm.ModelClass;

public class DeptModelClass {

    public  String deptname;
    public  String deptid;

    public DeptModelClass(String deptname, String deptid) {
        this.deptname = deptname;
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
}
