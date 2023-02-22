package com.icajobguarantee.ica;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.HashMap;

public class LaunchscreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Handler handler;
    LoginSessionManager loginSessionManager;
    CourseSelectSesManager courseSelectSesManager;
    ExamStartSesManager examStartSesManager;
//    AnimationDrawable manimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= 19) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        } else {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        setContentView(R.layout.activity_launchscreen);
        init();
        prepareJson();
    }

    private void init(){
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        courseSelectSesManager = new CourseSelectSesManager(getApplicationContext());
        examStartSesManager = new ExamStartSesManager(getApplicationContext());

//        ImageView mImageViewFilling = (ImageView) findViewById(R.id.imageview_animation_list_filling);
//        //((AnimationDrawable) mImageViewFilling.getBackground()).start();
//        manimationDrawable = (AnimationDrawable) mImageViewFilling.getBackground();

    }

    public void prepareJson()
    {
        /****** Create Thread that will sleep for 5 seconds **********/
//        Thread background = new Thread() {
//            public void run() {
//                try {
//                    manimationDrawable.start();
//                    // Thread will sleep for 5 seconds
//                    sleep(SPLASH_TIME_OUT);
//                    // After 5 seconds redirect to another intent
//                    if (loginSessionManager.isLoginSession()){
//                        if (examStartSesManager.isEStartSession()) {
//                            HashMap<String, String> eStartSessionDetails = examStartSesManager.getEStartSessionDetails();
//                            String exam_id = eStartSessionDetails.get(ExamStartSesManager.KEY_EXAMID_SES);
//                            String course_id = eStartSessionDetails.get(ExamStartSesManager.KEY_COURID_SES);
//                            String subject_id = eStartSessionDetails.get(ExamStartSesManager.KEY_SUBJID_SES);
//
//                            Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
//                            intent.putExtra("exam_id", exam_id);
//                            intent.putExtra("course_id", course_id);
//                            intent.putExtra("subject_id", subject_id);
//                            startActivity(intent);
//                            finish();
//                        } /*else if (courseSelectSesManager.isCrSelectSession()) {
//                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), CourseSelectActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }*/
//                        else {
//                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                } catch (Exception e) {
//                }
//            }
//        };
//        // start thread
//        background.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginSessionManager.isLoginSession()){
                    if (examStartSesManager.isEStartSession()) {
                        HashMap<String, String> eStartSessionDetails = examStartSesManager.getEStartSessionDetails();
                        String exam_id = eStartSessionDetails.get(ExamStartSesManager.KEY_EXAMID_SES);
                        String course_id = eStartSessionDetails.get(ExamStartSesManager.KEY_COURID_SES);
                        String subject_id = eStartSessionDetails.get(ExamStartSesManager.KEY_SUBJID_SES);

                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.putExtra("exam_id", exam_id);
                        intent.putExtra("course_id", course_id);
                        intent.putExtra("subject_id", subject_id);
                        startActivity(intent);
                        finish();
                    } else if (courseSelectSesManager.isCrSelectSession()) {
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } /*else {
                        Intent intent = new Intent(getApplicationContext(), CourseSelectActivity.class);
                        startActivity(intent);
                        finish();
                    }*/
                    else {
                        Intent intent = new Intent(getApplicationContext(), CourseTaggingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}
