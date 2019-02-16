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
import com.example.assignmentviewer.Models.Assignment;
import com.example.assignmentviewer.Models.Course;

public class InsertAssignmentDialogFragment extends DialogFragment {

    protected EditText assTitleEditText;
    protected EditText assGradeEditText;
    protected Button assSaveButton;
    protected Button assCancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_assignment, container, false);

        assTitleEditText = view.findViewById(R.id.assignmentTitleEditText);
        assGradeEditText = view.findViewById(R.id.assignmentGradeEditText);
        assSaveButton = view.findViewById(R.id.saveAssignmentButton);
        assCancelButton = view.findViewById(R.id.cancelAssignmentButton);

        assSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentActivity activity = (assignmentActivity) getActivity();
                int courseID = activity.getCourseID();
                String title = assTitleEditText.getText().toString();
                Double grade = Double.parseDouble(assGradeEditText.getText().toString());

                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                if(!(title.equals("") || grade.equals(""))){
                    dbHelper.insertAssignment(new Assignment(courseID,title,grade));
                    activity.getAssignmentData();
                    getDialog().dismiss();
                }
            }
        });

        assCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
}
