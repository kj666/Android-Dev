package com.example.assignmentviewer;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assignmentviewer.Database.DatabaseHelper;
import com.example.assignmentviewer.Models.Course;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected ListView courseListView;
    protected FloatingActionButton addCourseButton;
    protected TextView avgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseListView = findViewById(R.id.courseListView);
        addCourseButton = findViewById(R.id.addCourseButton);
        avgTextView = findViewById(R.id.assAverageTextView);

        loadListView();
        addCourseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                InsertCourseDialogFragment dialog = new InsertCourseDialogFragment();
                dialog.show(getSupportFragmentManager(), "InsertCourseFragment");
            }
        });
    }

    //Use to refresh coming from another activity
    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }

    //Fill in listView
    protected void loadListView(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        final List<Course> courses = dbHelper.getAllCourses();
        final double average = dbHelper.getAverageAllAssignment();

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int courseID = courses.get(position).getID();
                viewCourseAssignment(courseID,courses.get(position).getTitle(), courses.get(position).getCode() );
            }
        });

        avgTextView.setText("Average of All Assignments: "+String.format("%.2f", average));
        CourseListAdapter courseListAdapter = new CourseListAdapter(courses);
        courseListView.setAdapter(courseListAdapter);
    }

    protected void viewCourseAssignment(int courseID, String title, String code){
        Intent intent = new Intent(getApplicationContext(), assignmentActivity.class);
        intent.putExtra("CourseID", courseID);
        intent.putExtra("CourseTitle", title);
        intent.putExtra("CourseCode", code);
        startActivity(intent);
    }

    //Adapter for Course List View
    class CourseListAdapter extends BaseAdapter{
        private List<Course> courses;

        public CourseListAdapter(List<Course> courses) {
            this.courses = courses;
        }

        @Override
        public int getCount() {
            return courses.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.course_item, null);
            TextView courseTitle = (TextView) convertView.findViewById(R.id.itemCourseTitleTextView);
            TextView courseCode = (TextView) convertView.findViewById(R.id.itemCourseCodeTextView);
            TextView courseAvg = (TextView) convertView.findViewById(R.id.itemCourseAvgTextView);

            courseTitle.setText(courses.get(position).getTitle());
            courseCode.setText(courses.get(position).getCode());

            if(courses.get(position).isEmpty())
                courseAvg.setText("Assignments Average: NA");
            else
                courseAvg.setText("Assignments Average: "+ courses.get(position).getAverage()+"");
            return convertView;
        }
    }
}
