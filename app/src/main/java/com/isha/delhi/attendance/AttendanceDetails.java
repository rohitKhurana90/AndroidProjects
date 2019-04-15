package com.isha.delhi.attendance;

import java.util.Date;


public class AttendanceDetails {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String pincode;
    private String purposeOfVisit;
    private long visitDate;
    private String isFrequent;

    public AttendanceDetails(String name, String email, String phone, String pincode, String purposeOfVisit,String isFrequent) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pincode = pincode;
        this.purposeOfVisit = purposeOfVisit;
        this.isFrequent = isFrequent;
        Date date = new Date();
        visitDate = date.getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(long visitDate) {
        this.visitDate = visitDate;
    }

    public String getIsFrequent() {
        return isFrequent;
    }

    public void setIsFrequent(String isFrequent) {
        this.isFrequent = isFrequent;
    }
}
