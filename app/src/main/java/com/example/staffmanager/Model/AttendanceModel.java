package com.example.staffmanager.Model;

public class AttendanceModel {

    private String attendancemonth;
    private String salaryattendancemonth;
    private String salaryattendanceyear;
    private String salarycheckin;
    private String salaryleaves;

    public AttendanceModel(String attendancemonth, String salaryattendancemonth, String salaryattendanceyear, String salarycheckin, String salaryleaves) {
        this.attendancemonth = attendancemonth;
        this.salaryattendancemonth = salaryattendancemonth;
        this.salaryattendanceyear = salaryattendanceyear;
        this.salarycheckin = salarycheckin;
        this.salaryleaves = salaryleaves;
    }

    public String getAttendancemonth() {
        return attendancemonth;
    }

    public void setAttendancemonth(String attendancemonth) {
        this.attendancemonth = attendancemonth;
    }

    public String getSalaryattendancemonth() {
        return salaryattendancemonth;
    }

    public void setSalaryattendancemonth(String salaryattendancemonth) {
        this.salaryattendancemonth = salaryattendancemonth;
    }

    public String getSalaryattendanceyear() {
        return salaryattendanceyear;
    }

    public void setSalaryattendanceyear(String salaryattendanceyear) {
        this.salaryattendanceyear = salaryattendanceyear;
    }

    public String getSalarycheckin() {
        return salarycheckin;
    }

    public void setSalarycheckin(String salarycheckin) {
        this.salarycheckin = salarycheckin;
    }

    public String getSalaryleaves() {
        return salaryleaves;
    }

    public void setSalaryleaves(String salaryleaves) {
        this.salaryleaves = salaryleaves;
    }
}
