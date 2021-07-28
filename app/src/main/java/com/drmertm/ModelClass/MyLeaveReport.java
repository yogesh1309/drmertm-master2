package com.drmertm.ModelClass;

public class MyLeaveReport {

    public String leavedesk;
    public String places;
    public String reasone;
    public String fromdate;
    public String enddate;
    public String status;

    public MyLeaveReport(String leavedesk, String places, String reasone, String fromdate, String enddate, String status) {
        this.leavedesk = leavedesk;
        this.places = places;
        this.reasone = reasone;
        this.fromdate = fromdate;
        this.enddate = enddate;
        this.status = status;
    }

    public String getLeavedesk() {
        return leavedesk;
    }

    public void setLeavedesk(String leavedesk) {
        this.leavedesk = leavedesk;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getReasone() {
        return reasone;
    }

    public void setReasone(String reasone) {
        this.reasone = reasone;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
