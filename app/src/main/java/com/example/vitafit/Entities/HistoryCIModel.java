package com.example.vitafit.Entities;

public class HistoryCIModel {
    String calories, date, time;

    public HistoryCIModel(String calories, String date, String time) {
        this.calories = calories;
        this.date = date;
        this.time = time;
    }

    public String getCalories() {
        return calories;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
