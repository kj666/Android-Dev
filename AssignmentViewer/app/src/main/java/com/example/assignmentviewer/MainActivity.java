package com.example.assignmentviewer;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.assignmentviewer.Database.DatabaseHelper;
import com.example.assignmentviewer.Models.Course;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected ListView courseListView;
    protected FloatingActionButton addCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        dbHelper.insertCourse(new Course("TitleTest", "CodeTest"));

        courseListView = findViewById(R.id.courseListView);
        addCourseButton = findViewById(R.id.addCourseButton);

        loadListView();
        addCourseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                InsertCourseDialogFragment dialog = new InsertCourseDialogFragment();
                dialog.show(getSupportFragmentManager(), "InsertCourseFragment");
            }
        });
    }

    protected void loadListView(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Course> courses = dbHelper.getAllCourses();

        ArrayList<String> coursesListText = new ArrayList<>();

        for( Course c : courses){
            String temp = "" ;
            temp+= c.getTitle() +"\n"+ c.getCode();

            coursesListText.add(temp);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coursesListText);

        courseListView.setAdapter(arrayAdapter);
    }
}
