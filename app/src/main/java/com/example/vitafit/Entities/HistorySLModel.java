package com.example.vitafit.Entities;

public class HistorySLModel {
    String gly_index, status, date, time;

    public HistorySLModel(String gly_index, String status, String date, String time) {
        this.gly_index = gly_index;
        this.status = status;
        this.date = date;
        this.time = time;
    }

    public String getGly_index() {
        return gly_index;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setGly_index(String gly_index) {
        this.gly_index = gly_index;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
