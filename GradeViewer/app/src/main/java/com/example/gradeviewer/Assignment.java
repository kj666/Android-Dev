package com.example.karth.courseapp;

import java.util.Random;

public class Assignment {
    private static int assID = 0;

    private String assignmentTitle;
    private int assignmentGrade;

    private Assignment(String title, int grade){
        assignmentTitle = title;
        assignmentGrade = grade;
        assID++;
    }

    static public Assignment generateRandomAssignment(){
        Random rand = new Random();
        String tempTitle = "Assignment "+ assID;
        int tempGrade = rand.nextInt(100) +1;

        return new Assignment(tempTitle, tempGrade);
    }

    public String getAssignmentTitle(){
        return assignmentTitle;
    }

    public static int getAssID() {
        return assID;
    }
}
