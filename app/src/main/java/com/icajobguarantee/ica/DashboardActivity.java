package com.icajobguarantee.ica;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import androidx.fragment.app.FragmentManager;
//import androidx.core.content.ContextCompat;
//import androidx.viewpager.widget.ViewPager;
//import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
//import com.google.android.material.navigation.NavigationView;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager.widget.ViewPager;
//import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WebInterface {

    LoginSessionManager loginSessionManager;

    CourseSelectSesManager courseSelectSesManager;
    ExamStartSesManager examStartSesManager;

    LinearLayout bottom_icon_home, bottom_icon_course, bottom_icon_exam, bottom_icon_center,
            bottom_icon_setings, mGallery, progress_layout, achievement_layout;

    public static final String B_HOME_IMAGE = "b_home_image";
    public static final String B_COURSES_IMAGE = "b_courses_image";
    public static final String B_EXAM_IMAGE = "b_exam_image";
    public static final String B_CENTER_IMAGE = "b_center_image";
    public static final String B_SETTINGS_IMAGE = "b_settings_image";

    public static final String B_HOME_TEXT = "b_home_text";
    public static final String B_COURSES_TEXT = "b_courses_text";
    public static final String B_EXAM_TEXT = "b_exam_text";
    public static final String B_CENTER_TEXT = "b_center_text";
    public static final String B_SETTINGS_TEXT = "b_settings_text";

    ImageView b_home_image, b_courses_image, b_exam_image, b_center_image, b_settings_image;
    TextView b_home_text, b_courses_text, b_exam_text, b_center_text, b_settings_text,
            course_add_more, exam_heading_right, centre_heading_right;
    HorizontalScrollView horizontal_course;
//    private LayoutInflater mInflater;
    ArrayList<String> cViewId, cViewString, cViewImage, cViewSelected;
    ArrayList<String> listViewId, listViewImage, listViewString, listViewCode, listViewTime,
            listViewDetails, listViewVideo;
    ArrayList<String> clistViewId, clistViewImage, clistViewString, clistViewCode, clistViewTime,
            clistViewDetails, clistViewVideo;
    public String qfor, student_id, admin_id, StudentCode, device_token;
    private static final String TAG = "DashboardActivity";

    ListView exam_list_main, center_list_main;
    private static ViewPager mPager, sldPager;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    CirclePageIndicator indicator;

    ImageView logo_imageView;
    TextView logo_text, dashboard_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();

        bottom_icon_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                    onBackPressed();
//                }
//
//                setBottomImage("bHome");
//                setBottomText("bHome");

                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();

            }
        });

        bottom_icon_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Click On Course Icon", Toast.LENGTH_LONG).show();
                setBottomImage("bCourse");
                setBottomText("bCourse");

                CoursesFragment coursesFragment = CoursesFragment.newInstance("val1", "val2");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment, coursesFragment, coursesFragment.getTag()).addToBackStack(null).commit();
            }
        });

        bottom_icon_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Click On Exam Icon", Toast.LENGTH_LONG).show();
                setBottomImage("bExam");
                setBottomText("bExam");

//                ExamFragment examFragment = ExamFragment.newInstance("val1", "val2");
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment, examFragment, examFragment.getTag()).addToBackStack(null).commit();
                Intent intent = new Intent(getApplicationContext(), ExamListActivity.class);
                startActivity(intent);
            }
        });

        bottom_icon_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        bottom_icon_setings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Click On Settings Icon", Toast.LENGTH_LONG).show();
                setBottomImage("bSettings");
                setBottomText("bSettings");

                SettingsFragment settingsFragment = SettingsFragment.newInstance("val1", "val2");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment, settingsFragment, settingsFragment.getTag()).addToBackStack(null).commit();
            }
        });

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getSlider();
            getData();
            getExamData();
            getAdminData();
//            getCenterData();
            getfindData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

//        course_add_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), CourseAddMoreActivity.class);
//                // Closing all the Activities
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                // Add new Flag to start new Activity
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//            }
//        });

        exam_heading_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomImage("bExam");
                setBottomText("bExam");

//                ExamFragment examFragment = ExamFragment.newInstance("val1", "val2");
//                FragmentManager manager = getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.relativelayout_for_fragment, examFragment, examFragment.getTag()).addToBackStack(null).commit();

                Intent intent = new Intent(getApplicationContext(), ExamListActivity.class);
                startActivity(intent);
            }
        });

//        centre_heading_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setBottomImage("bCentre");
//                setBottomText("bCentre");
//
//                CenterFragment centerFragment = CenterFragment.newInstance("val1", "val2");
//                FragmentManager manager = getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.relativelayout_for_fragment, centerFragment, centerFragment.getTag()).addToBackStack(null).commit();
//            }
//        });

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ProgressActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(getApplicationContext(), ReportCourse.class);
                startActivity(intent);
            }
        });

        achievement_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AwardActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(getApplicationContext(), RankHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);
        admin_id = loginSessionDetails.get(LoginSessionManager.KEY_ADMINID_SES);
//        if (!loginSessionManager.isLoginSession()) {
//            loginSessionManager.checkLogin();
//            finish();
//        }

        courseSelectSesManager = new CourseSelectSesManager(getApplicationContext());
        examStartSesManager = new ExamStartSesManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide App Label Title Programatically
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        //Disable Menu
        Menu menuNav=navigationView.getMenu();
        //MenuItem nav_item1 = menuNav.findItem(R.id.nav_college);
        //nav_item1.setEnabled(false);

        MenuItem nav_item2 = menuNav.findItem(R.id.nav_terms_condition);
        nav_item2.setEnabled(false);

        MenuItem nav_item3 = menuNav.findItem(R.id.nav_privacy);
        nav_item3.setEnabled(false);

        //Bottom Menu
        bottom_icon_home = (LinearLayout) findViewById(R.id.bottom_icon_home);
        bottom_icon_course = (LinearLayout) findViewById(R.id.bottom_icon_course);
        bottom_icon_exam = (LinearLayout) findViewById(R.id.bottom_icon_exam);
        bottom_icon_center = (LinearLayout) findViewById(R.id.bottom_icon_center);
        bottom_icon_setings = (LinearLayout) findViewById(R.id.bottom_icon_setings);

        b_home_image = (ImageView) findViewById(R.id.b_home_image);
        b_courses_image = (ImageView) findViewById(R.id.b_courses_image);
        b_exam_image = (ImageView) findViewById(R.id.b_exam_image);
        b_center_image = (ImageView) findViewById(R.id.b_center_image);
        b_settings_image = (ImageView) findViewById(R.id.b_settings_image);

        b_home_text = (TextView) findViewById(R.id.b_home_text);
        b_courses_text = (TextView) findViewById(R.id.b_courses_text);
        b_exam_text = (TextView) findViewById(R.id.b_exam_text);
        b_center_text = (TextView) findViewById(R.id.b_center_text);
        b_settings_text = (TextView) findViewById(R.id.b_settings_text);

        progress_layout = (LinearLayout) findViewById(R.id.progress_layout);
        achievement_layout = (LinearLayout) findViewById(R.id.achievement_layout);


//        horizontal_course = (HorizontalScrollView) findViewById(R.id.horizontal_course);
//        mInflater = LayoutInflater.from(this);
//        mGallery = (LinearLayout) findViewById(R.id.course_gallery);
        cViewId = new ArrayList<String>();
        cViewString = new ArrayList<String>();
        cViewImage = new ArrayList<String>();
        cViewSelected = new ArrayList<String>();

        exam_list_main = (ListView) findViewById(R.id.exam_list_main);
        listViewId = new ArrayList<String>();
        listViewImage = new ArrayList<String>();
        listViewString = new ArrayList<String>();
        listViewCode = new ArrayList<String>();
        listViewTime = new ArrayList<String>();
        listViewDetails = new ArrayList<String>();
        listViewVideo = new ArrayList<String>();

//        center_list_main = (ListView) findViewById(R.id.center_list_main);
//        clistViewId = new ArrayList<String>();
//        clistViewImage = new ArrayList<String>();
//        clistViewString = new ArrayList<String>();
//        clistViewCode = new ArrayList<String>();
//        clistViewTime = new ArrayList<String>();
//        clistViewDetails = new ArrayList<String>();
//        clistViewVideo = new ArrayList<String>();

        //course_add_more = (TextView) findViewById(R.id.course_heading_right);
        exam_heading_right = (TextView) findViewById(R.id.exam_heading_right);
//        centre_heading_right = (TextView) findViewById(R.id.centre_heading_right);

        mPager = (ViewPager) findViewById(R.id.pager);

        logo_imageView = (ImageView) headerView.findViewById(R.id.logo_imageView);
        logo_text = (TextView) headerView.findViewById(R.id.logo_text);
        dashboard_title = (TextView) findViewById(R.id.dashboard_title);

        sldPager = (ViewPager) findViewById(R.id.sliderpager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        device_token = task.getResult();
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, device_token);
                        Log.d(TAG, msg);
                        //Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        Log.d("Fragment Count", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
//        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            //FragmentManager fm = getSupportFragmentManager();
//            for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
//                getSupportFragmentManager().popBackStack();
//            }
//
//            b_home_image.setColorFilter(getResources().getColor(R.color.HeaderBg));
//            b_courses_image.setColorFilter(getResources().getColor(R.color.DarkGray));
//            b_exam_image.setColorFilter(getResources().getColor(R.color.DarkGray));
//            b_center_image.setColorFilter(getResources().getColor(R.color.DarkGray));
//            b_settings_image.setColorFilter(getResources().getColor(R.color.DarkGray));
//
//            b_home_text.setTextColor(getResources().getColor(R.color.HeaderBg));
//            b_courses_text.setTextColor(getResources().getColor(R.color.DarkGray));
//            b_exam_text.setTextColor(getResources().getColor(R.color.DarkGray));
//            b_center_text.setTextColor(getResources().getColor(R.color.DarkGray));
//            b_settings_text.setTextColor(getResources().getColor(R.color.DarkGray));

        } else {
            super.onBackPressed();
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(i);
//            finish();
//            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/course";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(DashboardActivity.this,DashboardActivity.this);
        wc.sendRequest(url, params,0);
    }

    private void getExamData() {
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_exam";
        String url = "https://learnersmall.in/android/api/v2/exam";
        RequestParams params = new RequestParams();
        params.put("student", student_id);
        params.put("exam_for", "1");

        WebServiceController wc = new WebServiceController(DashboardActivity.this,DashboardActivity.this);
        wc.sendRequest(url, params,1);
    }

    private void getAdminData() {
        String url = "https://learnersmall.in/android/api/v2/admin-details";
        RequestParams params = new RequestParams();
        params.put("admin", admin_id);

        WebServiceController wc = new WebServiceController(DashboardActivity.this,DashboardActivity.this);
        wc.sendRequest(url, params,2);
    }

    private void getSlider() {
        String url = "https://learnersmall.in/android/api/v2/slider";
        RequestParams params = new RequestParams();
        params.put("createdby", admin_id);

        WebServiceController wc = new WebServiceController(DashboardActivity.this,DashboardActivity.this);
        wc.sendRequest(url, params,3);
    }

    private void getfindData() {
        String url = "https://learnersmall.in/android/api/v2/student-details";
        RequestParams params = new RequestParams();
        params.put("student", student_id);
        params.put("device_token", device_token);

        WebServiceController wc = new WebServiceController(DashboardActivity.this,DashboardActivity.this);
        wc.sendRequest(url, params,4);
    }

    private void initView() {
//        for (int i = 0; i < cViewId.size(); i++) {
//            View view = mInflater.inflate(R.layout.course_gallery_list, mGallery, false);
//            final TextView txt_id = (TextView) view.findViewById(R.id.course_id);
//            txt_id.setText(cViewId.get(i));
//
//            final TextView txt = (TextView) view.findViewById(R.id.course_title);
//            txt.setText(cViewString.get(i));
//
//            final ImageView img = (ImageView) view.findViewById(R.id.course_image);
//            Picasso.with(this).invalidate(cViewImage.get(i));
//            Picasso.with(this).load(cViewImage.get(i)).into(img);
//
////            txt.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Toast.makeText(getApplicationContext(), "Course select " + (cViewId.get(i)), Toast.LENGTH_LONG).show();
////                }
////            });
////
//
//            mGallery.addView(view);
//
////            img.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Toast.makeText(getApplicationContext(), "Image select " + (cViewId.get(i)), Toast.LENGTH_LONG).show();
////                }
////            });
//        }
//
//        mGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < cViewId.size(); i++) {
//                    Toast.makeText(getApplicationContext(), "Course select " + (cViewId.get(i)), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_share) {
            commonSocialShare();
        } else if (id == R.id.action_account) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void commonSocialShare() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
        String shareBody = "My ICA, Hey please check this application ";
        String shareSub = "My ICA";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            finish();
            //setBottomImage("bHome");
            //setBottomText("bHome");

        } else if (id == R.id.nav_courses) {
            setBottomImage("bCourse");
            setBottomText("bCourse");

            CoursesFragment coursesFragment = CoursesFragment.newInstance("val1", "val2");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment, coursesFragment, coursesFragment.getTag()).addToBackStack(null).commit();
        } else if (id == R.id.nav_exam) {
//            setBottomImage("bExam");
//            setBottomText("bExam");
//
//            ExamFragment examFragment = ExamFragment.newInstance("val1", "val2");
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.relativelayout_for_fragment, examFragment, examFragment.getTag()).addToBackStack(null).commit();
            Intent intent = new Intent(getApplicationContext(), ExamListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_competition) {
            Intent intent = new Intent(getApplicationContext(), CompetitionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_report_card) {
            Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_college) {
            Intent intent = new Intent(getApplicationContext(), ProfileDetailsActivity.class);
            intent.putExtra("StudentCode", StudentCode);
            startActivity(intent);
        } else if (id == R.id.nav_rank_his) {
            Intent intent = new Intent(getApplicationContext(), RankHistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_terms_condition) {
            Intent i = new Intent(getApplicationContext(), TermsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_privacy) {
            Intent i = new Intent(getApplicationContext(), PrivacyActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_help) {
            Intent i = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_rate_us) {
            rateApp();
        } else if (id == R.id.nav_version) {
            Intent i = new Intent(getApplicationContext(), VersionActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            if (loginSessionManager.isLoginSession()) {
                courseSelectSesManager.clearCrSelectSession();
                //examStartSesManager.clearEStartSession();
                loginSessionManager.userLogoutSession();
                finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setBottomImage(String bottomImage) {
        if (bottomImage.equalsIgnoreCase("bHome")) {

            b_home_image.setColorFilter(getResources().getColor(R.color.HeaderBg));
            b_courses_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_exam_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_center_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_settings_image.setColorFilter(getResources().getColor(R.color.DarkGray));


        } else if (bottomImage.equalsIgnoreCase("bCourse")) {

            b_home_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_courses_image.setColorFilter(getResources().getColor(R.color.HeaderBg));
            b_exam_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_center_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_settings_image.setColorFilter(getResources().getColor(R.color.DarkGray));

        } else if (bottomImage.equalsIgnoreCase("bExam")) {

            b_home_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_courses_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_exam_image.setColorFilter(getResources().getColor(R.color.HeaderBg));
            b_center_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_settings_image.setColorFilter(getResources().getColor(R.color.DarkGray));

        } else if (bottomImage.equalsIgnoreCase("bCentre")) {

            b_home_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_courses_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_exam_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_center_image.setColorFilter(getResources().getColor(R.color.HeaderBg));
            b_settings_image.setColorFilter(getResources().getColor(R.color.DarkGray));

        } else if (bottomImage.equalsIgnoreCase("bSettings")) {

            b_home_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_courses_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_exam_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_center_image.setColorFilter(getResources().getColor(R.color.DarkGray));
            b_settings_image.setColorFilter(getResources().getColor(R.color.HeaderBg));

        }
    }


    private void setBottomText(String bottomText) {
        if (bottomText.equalsIgnoreCase("bHome")) {

            b_home_text.setTextColor(getResources().getColor(R.color.HeaderBg));
            b_courses_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_exam_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_center_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_settings_text.setTextColor(getResources().getColor(R.color.DarkGray));

        } else if (bottomText.equalsIgnoreCase("bCourse")) {

            b_home_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_courses_text.setTextColor(getResources().getColor(R.color.HeaderBg));
            b_exam_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_center_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_settings_text.setTextColor(getResources().getColor(R.color.DarkGray));

        } else if (bottomText.equalsIgnoreCase("bExam")) {

            b_home_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_courses_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_exam_text.setTextColor(getResources().getColor(R.color.HeaderBg));
            b_center_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_settings_text.setTextColor(getResources().getColor(R.color.DarkGray));

        } else if (bottomText.equalsIgnoreCase("bCentre")) {

            b_home_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_courses_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_exam_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_center_text.setTextColor(getResources().getColor(R.color.HeaderBg));
            b_settings_text.setTextColor(getResources().getColor(R.color.DarkGray));

        } else if (bottomText.equalsIgnoreCase("bSettings")) {

            b_home_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_courses_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_exam_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_center_text.setTextColor(getResources().getColor(R.color.DarkGray));
            b_settings_text.setTextColor(getResources().getColor(R.color.HeaderBg));

        }
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                cViewId.clear();
                cViewString.clear();
                cViewImage.clear();
                cViewSelected.clear();
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String course_id = jsonObject.getString("id").trim();
                        String course_name = jsonObject.getString("course_name").trim();
                        String course_photo = jsonObject.getString("course_photo").trim();
                        String course_selected = jsonObject.getString("status").trim();

                        if (course_selected.equalsIgnoreCase("1")) {
                            cViewId.add(course_id);
                            cViewString.add(course_name);
                            cViewImage.add(course_photo);
                            cViewSelected.add(course_selected);
                        }
                    }

                    //initView();
                    CourseAdapter courseAdapter = new CourseAdapter(this, cViewId, cViewString, cViewImage, cViewSelected);
                    mPager.setAdapter(courseAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                listViewId.clear();
                listViewString.clear();
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String l_id = "";
                        String l_code = "";
                        String l_name = "";
                        String l_image = "";
                        String l_details = "";
                        String l_created = "";
                        String l_video = "";
                        String exam_type = "";

                        l_id = jsonObject.getString("id").trim();
                        l_name = jsonObject.getString("exam_name").trim();
                        if (!jsonObject.isNull("exam_details")) {
                            l_code = jsonObject.getString("exam_details").trim();
                        }
                        //l_code = jsonObject.getString("exam_code").trim();
                        //exam_type = jsonObject.getString("type").trim();

                        listViewId.add(l_id);
                        listViewString.add(l_name);
                        listViewCode.add(l_code);
                        listViewTime.add(exam_type);
                        listViewImage.add(l_image);
                        listViewDetails.add(l_details);
                        listViewVideo.add(l_video);
                    }

                    CustomHListView adapterViewAndroid = new CustomHListView(getApplicationContext(), listViewId, listViewString, listViewImage, listViewCode, listViewTime, listViewDetails, listViewVideo);
                    exam_list_main.setAdapter(adapterViewAndroid);
                    exam_list_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            //Toast.makeText(getApplicationContext(), "Exam - " + listViewId.get(+i), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), ExamDetailsActivity.class);
                            intent.putExtra("exam_id", listViewId.get(+i));
                            intent.putExtra("exam_title", listViewString.get(+i));
                            intent.putExtra("exam_code", listViewCode.get(+i));
                            intent.putExtra("exam_type", listViewTime.get(+i));
                            //intent.putExtra("course_id", course_id);
                            startActivity(intent);

                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 2) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        System.out.println(name);
                        //logo_text.setText(name);
                        dashboard_title.setText(name);

                        String admin_images;
                        if (!jsonObject.getString("admin_images").isEmpty()
                                && !jsonObject.getString("admin_images").equalsIgnoreCase("null")) {
                            admin_images = jsonObject.getString("admin_images");
                            Picasso.with(this).invalidate(admin_images);
                            Picasso.with(this).load(admin_images).into(logo_imageView);
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 3) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ImagesArray.add(jsonObject.getString("img"));
                    }

                    sldPager.setAdapter(new SlidingImageAdapter(DashboardActivity.this, ImagesArray));
                    indicator.setViewPager(sldPager);
                    final float density = getResources().getDisplayMetrics().density;
                    //Set circle indicator radius
                    indicator.setRadius(5 * density);
                    NUM_PAGES =jsonArray.length();
                    // Auto start of viewpager
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == NUM_PAGES) {
                                currentPage = 0;
                            }
                            sldPager.setCurrentItem(currentPage++, true);
                        }
                    };
                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 3000, 3000);

                    // Pager listener over indicator
                    indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageSelected(int position) {
                            currentPage = position;

                        }
                        @Override
                        public void onPageScrolled(int pos, float arg1, int arg2) {

                        }
                        @Override
                        public void onPageScrollStateChanged(int pos) {

                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 4) {
            try {
//                JSONArray jsonArray = new JSONArray(response);
//                if (jsonArray.length()>0) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        StudentCode = jsonObject.getString("code").trim();
//                    }
//                }
                JSONObject jsonObject = new JSONObject(response);
                JSONObject student = jsonObject.getJSONObject("students");

                StudentCode = student.getString("code").trim();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(int flag) {

    }

    /*
     * Start with rating the app
     * Determine if the Play Store is installed on the device
     *
     * */
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

//    public static final int getColor(Context context, int id) {
//        final int version = Build.VERSION.SDK_INT;
//        if (version >= 23) {
//            return ContextCompat.getColor(context, id);
//        } else {
//            return context.getResources().getColor(id);
//        }
//    }
}
