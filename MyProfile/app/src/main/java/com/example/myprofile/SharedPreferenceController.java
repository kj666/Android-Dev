package com.example.myprofile;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceController {
    protected SharedPreferences sharedPreferences;

    public SharedPreferenceController(Context context) {
        sharedPreferences = context.getSharedPreferences("ProfileFile", context.MODE_PRIVATE);
    }

    //Save profile
    public void saveProfile(ProfileObj profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileName", profile.getName());
        editor.putInt("profileAge", profile.getAge());
        editor.putInt("profileID", profile.getId());
        editor.commit();
    }

    //Retrieve Profile
    public ProfileObj retrieveProfile() {
        return new ProfileObj(
                sharedPreferences.getString("profileName", null),
                sharedPreferences.getInt("profileAge", 0),
                sharedPreferences.getInt("profileID", 0));
    }

    //get name only to use in Main activity button
    public String getProfileName(){
        return sharedPreferences.getString("profileName", null);
    }

    public void resetProfile(){
        saveProfile(new ProfileObj(null, 0, 0));
    }
}