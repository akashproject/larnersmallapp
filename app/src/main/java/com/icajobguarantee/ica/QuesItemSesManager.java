package com.icajobguarantee.ica;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class QuesItemSesManager {
    // Shared Preferences
    SharedPreferences quespref;

    // Editor for Shared preferences
    SharedPreferences.Editor queseditor;

    // Context
    Context _examcontext;

    // Shared pref mode
    int PRIVATE_MODE_COURSE = 0;

    // Sharedpref file name
    private static final String PREF_NAME_QUES = "icamobiques";

    // All Shared Preferences Keys
    private static final String IS_QUELOG = "IsQues";

    // User id (make variable public to access from outside)
    public static final String KEY_QUES_SES = "examId";

    // Constructor
    public QuesItemSesManager(Context context) {
        this._examcontext = context;
        quespref = _examcontext.getSharedPreferences(PREF_NAME_QUES, PRIVATE_MODE_COURSE);
        queseditor = quespref.edit();
    }

    /**
     * Create login session
     * */
    public void createQuesSession(String ques_item) {
        // Storing login value as TRUE
        queseditor.putBoolean(IS_QUELOG, true);
        // Storing exam in pref
        queseditor.putString(KEY_QUES_SES, ques_item);
        // commit changes
        queseditor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getQuesSessionDetails() {
        HashMap<String, String> examstart = new HashMap<String, String>();
        // user id
        examstart.put(KEY_QUES_SES, quespref.getString(KEY_QUES_SES, null));
        // return user
        return examstart;
    }

    public void clearQuesSession(){
        // Clearing all data from Shared Preferences
        queseditor.clear();
        queseditor.commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isQuesSession(){
        return quespref.getBoolean(IS_QUELOG, false);
    }
}
