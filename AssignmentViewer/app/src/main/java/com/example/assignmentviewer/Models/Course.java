package com.example.assignmentviewer.Models;

public class Course {

    private int ID;
    private String title;
    private String code;
    private Double average;
    private boolean empty;

    public Course(int ID, String title, String code, Double average, boolean empty) {
        this.ID = ID;
        this.title = title;
        this.code = code;
        this.average = average;
        this.empty = empty;

    }
    public Course(int ID, String title, String code) {
        this.ID = ID;
        this.title = title;
        this.code = code;
        this.average = null;
    }

    public Course(String title, String code) {
        this.title = title;
        this.code = code;
        this.average = null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
