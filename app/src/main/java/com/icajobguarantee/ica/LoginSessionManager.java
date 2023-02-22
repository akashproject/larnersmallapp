package com.icajobguarantee.ica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class LoginSessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "icamobi";

    // All Shared Preferences Keys
    private static final String IS_MPLOG = "IsLogin";

    // User id (make variable public to access from outside)
    public static final String KEY_USERID_SES = "studentId";
    // Admin id (make variable public to access from outside)
    public static final String KEY_ADMINID_SES = "adminId";

    // Constructor
    public LoginSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(HashMap<String, String> loginMap) {
        // Storing login value as TRUE
        editor.putBoolean(IS_MPLOG, true);
        // Storing name in pref
        editor.putString(KEY_USERID_SES, loginMap.get("student_id"));
        // Storing name in pref
        editor.putString(KEY_ADMINID_SES, loginMap.get("admin_id"));
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getLoginSessionDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user id
        user.put(KEY_USERID_SES, pref.getString(KEY_USERID_SES, null));
        // admin id
        user.put(KEY_ADMINID_SES, pref.getString(KEY_ADMINID_SES, null));
        // return user
        return user;
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoginSession()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public void checkLogout(){
        if (!this.isLoginSession()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public void userLogoutSession(){
        this.clearLoginSession();
        // user is not logged in redirect him to Login Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }

    public void clearLoginSession(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoginSession(){
        return pref.getBoolean(IS_MPLOG, false);
    }
}
