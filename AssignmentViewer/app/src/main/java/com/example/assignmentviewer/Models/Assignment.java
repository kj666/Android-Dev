package com.example.assignmentviewer.Models;

public class Assignment {

    private int ID;
    private int courseID;
    private String title;
    private double grade;

    public Assignment(int ID, int courseID, String title, double grade) {
        this.ID = ID;
        this.courseID = courseID;
        this.title = title;
        this.grade = grade;
    }

    public Assignment(int courseID, String title, double grade) {
        this.courseID = courseID;
        this.title = title;
        this.grade = grade;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
