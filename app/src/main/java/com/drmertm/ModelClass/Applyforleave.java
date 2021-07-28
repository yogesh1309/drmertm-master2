package com.drmertm.ModelClass;

public class Applyforleave {

    public String leavetype;
    public String closeblance;
    public String action;
    public String id;

    public Applyforleave(String leavetype, String closeblance, String action, String id) {
        this.leavetype = leavetype;
        this.closeblance = closeblance;
        this.action = action;
        this.id = id;
    }

    public String getLeavetype() {
        return leavetype;
    }

    public void setLeavetype(String leavetype) {
        this.leavetype = leavetype;
    }

    public String getCloseblance() {
        return closeblance;
    }

    public void setCloseblance(String closeblance) {
        this.closeblance = closeblance;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
