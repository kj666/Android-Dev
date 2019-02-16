package com.example.assignmentviewer;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assignmentviewer.Database.DatabaseHelper;
import com.example.assignmentviewer.Models.Assignment;
import com.example.assignmentviewer.Models.Course;
import com.example.assignmentviewer.R;

import java.util.List;

public class assignmentActivity extends AppCompatActivity {


    protected String courseTitle;
    protected String courseCode;
    protected int courseID;

    protected TextView courseTitleTextView;
    protected TextView courseCodeTextView;
    protected FloatingActionButton addAssButton;
    protected ListView assListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        courseTitleTextView = findViewById(R.id.AssCourseTextView);
        courseCodeTextView = findViewById(R.id.AssCourseCodeTextView);
        addAssButton = findViewById(R.id.addAssingmentButton);
        assListView = findViewById(R.id.AssListView);

        getData();
        courseTitleTextView.setText(courseTitle);
        courseCodeTextView.setText(courseCode);

        loadList();
        addAssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertAssignmentDialogFragment dialog = new InsertAssignmentDialogFragment();
                dialog.show(getSupportFragmentManager(),"InsertAssignmentFragment");
            }
        });

    }

    public int getCourseID(){
        return courseID;
    }

    //get course data from previous intent
    protected void getData(){
        courseTitle = getIntent().getStringExtra("CourseTitle");
        courseCode = getIntent().getStringExtra("CourseCode");
        courseID = getIntent().getIntExtra("CourseID", 0);
    }

    protected void loadList(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        final List<Assignment> assignments = dbHelper.getAllAssignmentsByCourse(courseID);

        AssListAdapter assListAdapter = new AssListAdapter(assignments);
        assListView.setAdapter(assListAdapter);
    }

    //Adapter for Assignment List View
    class AssListAdapter extends BaseAdapter{

        private List<Assignment> assignments;

        public AssListAdapter(List<Assignment> assignments) {
            this.assignments = assignments;
        }

        @Override
        public int getCount() {
            return assignments.size();
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
            convertView = getLayoutInflater().inflate(R.layout.assignment_item, null);
            TextView assTitleTextView = (TextView) convertView.findViewById(R.id.AssTitleTextView);
            TextView assGradeTextView = (TextView) convertView.findViewById(R.id.AssGradeTextView);

            assTitleTextView.setText(assignments.get(position).getTitle());
            assGradeTextView.setText(assignments.get(position).getGrade()+"");
            return convertView;
        }
    }
}
