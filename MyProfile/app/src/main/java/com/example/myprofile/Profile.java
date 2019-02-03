package com.example.myprofile;

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
                boolean nameB = false, ageB = false, idB = false;
                boolean ageIn = false;

                //validate Empty name
                if(!editName.getText().toString().matches("")){
                    nameB = true;
                }
                else {
                    nameB = false;
                }

                //validate Empty age
                if(!editAge.getText().toString().matches("")){
                    int ageValue = Integer.valueOf(editAge.getText().toString());
                    ageB = true;
                    //Validate Age Value
                    if(ageValue > 17 && ageValue < 100){
                        ageIn = true;
                    }
                    else {
                        ageIn = false;
                        Toast.makeText(getApplicationContext(), "Invalid Age, must be between 18-99", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    ageB = false;
                }

                //validate empty id
                if(!editID.getText().toString().matches("")){
                    idB = true;
                }
                else {
                    idB = false;
                }

                //Save profile if validation success
                if(idB && nameB && ageB && ageIn){
                    controller.saveProfile(new ProfileObj(
                                    editName.getText().toString(),
                                    Integer.parseInt(editAge.getText().toString()),
                                    Integer.parseInt(editID.getText().toString()))
                            );
                    disableEdit();
                    Toast.makeText(getApplicationContext(), "Profile Saved!", Toast.LENGTH_LONG).show();
                }
                else if(!(idB && nameB && ageB)){
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_LONG).show();
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

    protected void disableEdit(){
        editName.setEnabled(false);
        editAge.setEnabled(false);
        editID.setEnabled(false);
        saveButton.setVisibility(View.GONE);
        editable = true;
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
                    disableEdit();
                    item.setChecked(false);
                }
                return true;
            //reset the profile
            case R.id.resetMenu:
                controller.resetProfile();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
