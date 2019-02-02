package com.example.myprofile;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceController {
    protected SharedPreferences sharedPreferences;

    public SharedPreferenceController(Context context){
        sharedPreferences = context.getSharedPreferences("ProfileFile",context.MODE_PRIVATE);
    }

    public void setProfileName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileName", name);
        editor.commit();
    }

    public String getProfileName(){
        return sharedPreferences.getString("profileName", null);
    }

    public void setProfileAge(int age){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("profileAge", age);
        editor.commit();
    }

    public int getProfileAge(){
        return sharedPreferences.getInt("profileAge", null);
    }

    public void setProfileID(int id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("profileID", id);
        editor.commit();
    }

    public int getProfileID(){
        return sharedPreferences.getInt("profileID", );
    }
}
