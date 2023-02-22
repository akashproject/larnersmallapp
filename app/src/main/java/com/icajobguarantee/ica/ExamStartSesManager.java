package com.icajobguarantee.ica;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class ExamStartSesManager {
    // Shared Preferences
    SharedPreferences exampref;

    // Editor for Shared preferences
    SharedPreferences.Editor exameditor;

    // Context
    Context _examcontext;

    // Shared pref mode
    int PRIVATE_MODE_COURSE = 0;

    // Sharedpref file name
    private static final String PREF_NAME_COURSE = "icamobiexam";

    // All Shared Preferences Keys
    private static final String IS_EXSLOG = "IsExamStart";

    // User id (make variable public to access from outside)
    public static final String KEY_EXAMID_SES = "examId";
    public static final String KEY_COURID_SES = "courseId";
    public static final String KEY_SUBJID_SES = "subjectId";

    // Constructor
    public ExamStartSesManager(Context context) {
        this._examcontext = context;
        exampref = _examcontext.getSharedPreferences(PREF_NAME_COURSE, PRIVATE_MODE_COURSE);
        exameditor = exampref.edit();
    }

    /**
     * Create login session
     * */
    public void createEStartSession(String exam_id, String course_id, String subject_id) {
        // Storing login value as TRUE
        exameditor.putBoolean(IS_EXSLOG, true);
        // Storing exam in pref
        exameditor.putString(KEY_EXAMID_SES, exam_id);
        // Storing exam in pref
        exameditor.putString(KEY_COURID_SES, course_id);
        // Storing exam in pref
        exameditor.putString(KEY_SUBJID_SES, subject_id);
        // commit changes
        exameditor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getEStartSessionDetails() {
        HashMap<String, String> examstart = new HashMap<String, String>();
        // user id
        examstart.put(KEY_EXAMID_SES, exampref.getString(KEY_EXAMID_SES, null));
        // user id
        examstart.put(KEY_COURID_SES, exampref.getString(KEY_COURID_SES, null));
        // user id
        examstart.put(KEY_SUBJID_SES, exampref.getString(KEY_SUBJID_SES, null));
        // return user
        return examstart;
    }

    public void clearEStartSession(){
        // Clearing all data from Shared Preferences
        exameditor.clear();
        exameditor.commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isEStartSession(){
        return exampref.getBoolean(IS_EXSLOG, false);
    }
}
