package com.example.assignmentviewer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignmentviewer.Database.DatabaseHelper;

public class InsertCourseDialogFragment extends DialogFragment {


    protected EditText courseTitleEditText;
    protected EditText courseCodeEditText;
    protected Button courseSaveButton;
    protected Button courseCancelButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_course, container, false);

        courseTitleEditText = view.findViewById(R.id.courseTitleEditText);
        courseCodeEditText = view.findViewById(R.id.courseCodeEditText);
        courseSaveButton = view.findViewById(R.id.saveCourseButton);
        courseCancelButton = view.findViewById(R.id.cancelCourseButton);

        courseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = courseTitleEditText.getText().toString();
                String code = courseCodeEditText.getText().toString();

                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                if(!(title.equals("") || code.equals(""))){
                    dbHelper.insertCourse(new Course(title, code));
                    ((MainActivity)getActivity()).loadListView();
                    getDialog().dismiss();
                }
            }
        });

        courseCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
