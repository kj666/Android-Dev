package com.example.myprofile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Create a controller object
    protected SharedPreferenceController controller;
    protected Button profileButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the controller
        controller = new SharedPreferenceController(MainActivity.this);

        profileButton = findViewById(R.id.profileButton);

        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToProfileActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String name = controller.getProfileName();

        if(name == null){
            goToProfileActivity();
        }
        else {
            profileButton.setText(name);
        }
    }

    protected void goToProfileActivity(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}

