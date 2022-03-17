package com.example.staffmanager.Model;

public class SalaryModel {
    private String salarymonth;
    private String salaryyear;
    private String salarystatus;
    private String salarydate;
    private String salaryfixed;
    private String salarycommision;
    private String salarydeductable;
    private String totalsalary;
    private String note;
    private String createdone;

    public SalaryModel(String salarymonth, String salaryyear, String salarystatus, String salarydate, String salaryfixed, String salarycommision, String salarydeductable, String totalsalary, String note, String createdone) {
        this.salarymonth = salarymonth;
        this.salaryyear = salaryyear;
        this.salarystatus = salarystatus;
        this.salarydate = salarydate;
        this.salaryfixed = salaryfixed;
        this.salarycommision = salarycommision;
        this.salarydeductable = salarydeductable;
        this.totalsalary = totalsalary;
        this.note = note;
        this.createdone = createdone;
    }

    public String getSalarymonth() {
        return salarymonth;
    }

    public void setSalarymonth(String salarymonth) {
        this.salarymonth = salarymonth;
    }

    public String getSalaryyear() {
        return salaryyear;
    }

    public void setSalaryyear(String salaryyear) {
        this.salaryyear = salaryyear;
    }

    public String getSalarystatus() {
        return salarystatus;
    }

    public void setSalarystatus(String salarystatus) {
        this.salarystatus = salarystatus;
    }

    public String getSalarydate() {
        return salarydate;
    }

    public void setSalarydate(String salarydate) {
        this.salarydate = salarydate;
    }

    public String getSalaryfixed() {
        return salaryfixed;
    }

    public void setSalaryfixed(String salaryfixed) {
        this.salaryfixed = salaryfixed;
    }

    public String getSalarycommision() {
        return salarycommision;
    }

    public void setSalarycommision(String salarycommision) {
        this.salarycommision = salarycommision;
    }

    public String getSalarydeductable() {
        return salarydeductable;
    }

    public void setSalarydeductable(String salarydeductable) {
        this.salarydeductable = salarydeductable;
    }

    public String getTotalsalary() {
        return totalsalary;
    }

    public void setTotalsalary(String totalsalary) {
        this.totalsalary = totalsalary;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedone() {
        return createdone;
    }

    public void setCreatedone(String createdone) {
        this.createdone = createdone;
    }
}

