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
    protected EditText editName;
    protected Button saveButton;
    protected MenuItem editItem;

    protected boolean editable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        controller = new SharedPreferenceController(Profile.this);

        editName = findViewById(R.id.nameEditText);
        saveButton = findViewById(R.id.saveButton);
        editItem = findViewById(R.id.enableEditButton);

        saveButton.setVisibility(View.GONE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = controller.getProfileName();
                editName.setText(name);
                controller.setProfileName(editName.getText().toString());

                Toast toast = Toast.makeText(getApplicationContext(), "Name Saved !", Toast.LENGTH_LONG);
                toast.show();
            }
        });
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
            if(editable){
                //Enable edit mode
                saveButton.setVisibility(View.VISIBLE);
                editName.setFocusable(true);
                item.setChecked(true);
                editable = false;
            }
            else{
                //Disable edit mode
                saveButton.setVisibility(View.GONE);
                editName.setFocusable(false);
                item.setChecked(false);
                editable = true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
