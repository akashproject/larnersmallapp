package com.icajobguarantee.ica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class CourseSelectSesManager {
    // Shared Preferences
    SharedPreferences coursepref;

    // Editor for Shared preferences
    SharedPreferences.Editor courseeditor;

    // Context
    Context _coursecontext;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "icamobi";

    // All Shared Preferences Keys
    private static final String IS_CRLOG = "IsCourse";

    // Constructor
    public CourseSelectSesManager(Context context) {
        this._coursecontext = context;
        coursepref = _coursecontext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        courseeditor = coursepref.edit();
    }

    /**
     * Create login session
     * */
    public void createCrSelectSession() {
        // Storing login value as TRUE
        courseeditor.putBoolean(IS_CRLOG, true);
        // commit changes
        courseeditor.commit();
    }

    public void clearCrSelectSession(){
        // Clearing all data from Shared Preferences
        courseeditor.clear();
        courseeditor.commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isCrSelectSession(){
        return coursepref.getBoolean(IS_CRLOG, false);
    }
}
