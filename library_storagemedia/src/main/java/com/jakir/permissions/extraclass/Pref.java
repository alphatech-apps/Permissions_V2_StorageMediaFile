package com.jakir.permissions.extraclass;


import android.content.Context;
import android.content.SharedPreferences;

public class Pref {


    public static void setState(boolean data, String key, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(key,Context.MODE_PRIVATE).edit();
        editor.putBoolean(key,data);
        editor.apply();
    }
    public static boolean getState(String key,Context context){
        SharedPreferences preferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        return  preferences.getBoolean(key,false);
    }

}