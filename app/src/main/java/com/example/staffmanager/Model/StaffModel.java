package com.example.staffmanager.Model;

import java.io.Serializable;

public class StaffModel implements Serializable {

    private String id;
    private String profileimage;
    private String firstname;
    private String lastname;
    private String contact;
    private String address;
    private String emergencycontact;
    private String emergencyname;
    private String designation;
    private String emailid;
    private String note;
    private String stafftype;

    public StaffModel(String id, String profileimage, String firstname, String lastname, String contact, String address, String emergencycontact, String emergencyname, String designation, String emailid, String note, String stafftype) {
        this.id = id;
        this.profileimage = profileimage;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.address = address;
        this.emergencycontact = emergencycontact;
        this.emergencyname = emergencyname;
        this.designation = designation;
        this.emailid = emailid;
        this.note = note;
        this.stafftype = stafftype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencycontact() {
        return emergencycontact;
    }

    public void setEmergencycontact(String emergencycontact) {
        this.emergencycontact = emergencycontact;
    }

    public String getEmergencyname() {
        return emergencyname;
    }

    public void setEmergencyname(String emergencyname) {
        this.emergencyname = emergencyname;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }
}
