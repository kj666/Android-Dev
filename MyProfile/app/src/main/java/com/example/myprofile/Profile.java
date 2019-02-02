package com.example.myprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private SharedPreferenceController controller;

    protected EditText editName, editAge, editID;
    protected Button saveButton;
    protected boolean editable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        controller = new SharedPreferenceController(Profile.this);

        //intitialize layout items
        editName = findViewById(R.id.nameEditText);
        editAge = findViewById(R.id.ageEditText);
        editID = findViewById(R.id.idEditText);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setVisibility(View.GONE);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controller.setProfileName(editName.getText().toString());
                controller.setProfileID(Integer.parseInt(editID.getText().toString()));
                controller.setProfileAge(Integer.parseInt(editAge.getText().toString()));

                Toast toast = Toast.makeText(getApplicationContext(), "Name Saved !", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String name = controller.getProfileName();
        int age = controller.getProfileAge();
        int id = controller.getProfileID();
        if(name == null){
            editName.setText("Name");
        }
        else {
            editName.setText(name);
            editAge.setText(age+"");
            editID.setText(id+"");
        }
    }

    //Create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Menu clicking
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.enableEditButton){
            if(editable) {
                //Enable edit mode
                editName.setEnabled(true);
                editAge.setEnabled(true);
                editID.setEnabled(true);
                saveButton.setVisibility(View.VISIBLE);
                item.setChecked(true);
                editable = false;
            }
            else{
                //Disable edit mode
                editName.setEnabled(false);
                editAge.setEnabled(false);
                editID.setEnabled(false);
                saveButton.setVisibility(View.GONE);
                item.setChecked(false);
                editable = true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
