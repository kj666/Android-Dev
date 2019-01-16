package com.example.gradeviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewGrade extends AppCompatActivity {

    String[] courses ={
            "Course 1",
            "Course 2",
            "Course 3"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grade);
        setup();
    }

    protected void setup(){
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_view_grade, courses);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
