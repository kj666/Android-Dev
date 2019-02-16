package com.example.assignmentviewer.Models;

import java.util.List;

public class Course {

    private int ID;
    private String Title;
    private String Code;
    private List<Assignment> assignments;

    public Course(int ID, String title, String code) {
        this.ID = ID;
        Title = title;
        Code = code;
    }

    public Course(String title, String code) {
        Title = title;
        Code = code;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void addAssignment(Assignment ass){
        assignments.add(ass);
    }
}
