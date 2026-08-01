package com.example.vitafit.Entities;

public class StepCountModel {
    private int steps;
    private String date;

    public StepCountModel(int steps, String date) {
        this.steps = steps;
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}