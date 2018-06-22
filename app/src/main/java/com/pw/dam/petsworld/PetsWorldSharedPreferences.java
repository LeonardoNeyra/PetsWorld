package com.pw.dam.petsworld;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Leonardo on 31/05/2018.
 */

public class PetsWorldSharedPreferences {
    private static String MY_PREFS_NAME = "MY_PREFS_NAME";
    public static String UID_LOGUED_USER = "UID_LOGUED_USER";

    private SharedPreferences sharedPreferences;
    public PetsWorldSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREFS_NAME,Context.MODE_PRIVATE);
    }

    public void setString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }

}
