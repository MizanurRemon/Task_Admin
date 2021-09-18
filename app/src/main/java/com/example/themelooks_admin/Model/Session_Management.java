package com.example.themelooks_admin.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Session_Management {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "SESSION_ID";

    public Session_Management(Context con) {
        sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String userID) {
        String id = userID;
        editor.putString(SESSION_KEY, id);
        editor.commit();
        Log.d(SESSION_KEY, id);
    }

    public String getSession() {
        return sharedPreferences.getString(SESSION_KEY, "-1");
    }

    public void removeSession() {
        editor.putString(SESSION_KEY, "-1").commit();
    }
}
