package com.example.vitafit.Entities;

public class HistoryBPModel {
    String systolic, diastolic, pulse, date, time;

    public HistoryBPModel(String systolic, String diastolic, String pulse, String date, String time) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.date = date;
        this.time = time;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getSystolic() {
        return systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public String getPulse() {
        return pulse;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
