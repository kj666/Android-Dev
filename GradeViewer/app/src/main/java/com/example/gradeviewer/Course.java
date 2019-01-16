package com.example.karth.courseapp;

import java.util.ArrayList;
import java.util.Random;

public class Course {
    private static int courseID = 0;

    private String courseTitle;
    private ArrayList<Assignment> assignments;

    private Course(String title, ArrayList<Assignment> assign){
        courseTitle = title;
        assignments = assign;
        courseID++;
    }

    static public Course generateRandomCourse(){
        Random rand = new Random();
        int assignmentNo = rand.nextInt(5);
        ArrayList<Assignment> tempAssign = new ArrayList<Assignment>();

        for(int i = 0; i< assignmentNo; i++){
            tempAssign.add(Assignment.generateRandomAssignment());
        }
        return new Course("Course " + courseID, tempAssign);
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
}
