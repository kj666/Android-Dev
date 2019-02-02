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
    private ProfileObj profile;
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

                int ageValue = Integer.valueOf(editAge.getText().toString());
                int idValue = Integer.valueOf(editID.getText().toString());
                //validate age
                if(ageValue>17 && ageValue <100){
                    //validate ID
                    if(idValue<1000000) {
                        //Use controller to save new profile
                        controller.saveProfile(new ProfileObj(
                                editName.getText().toString(),
                                Integer.parseInt(editAge.getText().toString()),
                                Integer.parseInt(editID.getText().toString()))
                        );

                        Toast toast = Toast.makeText(getApplicationContext(), "Profile Saved!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        Toast idToast = Toast.makeText(getApplicationContext(), "Invalid ID, maximum 6 digits", Toast.LENGTH_LONG);
                        idToast.show();
                    }
                }
                else {
                    Toast ageToast = Toast.makeText(getApplicationContext(), "Invalid Age, must be between 18-99", Toast.LENGTH_LONG);
                    ageToast.show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        retrieve();

    }

    //retrieve profile
    protected void retrieve(){
        //retrieve profile data
        profile = controller.retrieveProfile();
        if(profile.getName() != null){
            editName.setText(profile.getName());
            editAge.setText(profile.getAge()+"");
            editID.setText(profile.getId()+"");
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

        switch(item.getItemId()){
            case R.id.enableEditButton:
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
                return true;
            //reset the profile
            case R.id.resetMenu:
                controller.resetProfile();
                retrieve();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
