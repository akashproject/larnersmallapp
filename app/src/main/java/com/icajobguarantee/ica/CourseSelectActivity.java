package com.icajobguarantee.ica;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseSelectActivity extends AppCompatActivity implements WebInterface {

    GridView gridView;
    ArrayList<String> gridViewId, gridViewString, gridViewImage, gridViewSelected;
    public String qfor, student_id, admin_id;
    LoginSessionManager loginSessionManager;
    CourseSelectSesManager courseSelectSesManager;
    Dialog pDialog;
    GridView androidGridView;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);

        init();

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }
    }

    public void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);
        admin_id = loginSessionDetails.get(LoginSessionManager.KEY_ADMINID_SES);

        courseSelectSesManager = new CourseSelectSesManager(getApplicationContext());

        gridView = (GridView) findViewById(R.id.gridView);
        gridViewId = new ArrayList<String>();
        gridViewString = new ArrayList<String>();
        gridViewImage = new ArrayList<String>();
        gridViewSelected = new ArrayList<String>();
    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/course";
        RequestParams params = new RequestParams();
        params.put("student", student_id);
        params.put("admin", admin_id);

        WebServiceController wc = new WebServiceController(CourseSelectActivity.this,CourseSelectActivity.this);
        wc.sendRequest(url, params,0);
    }

    private void setCourse(String course_id) {
        //qfor = "set_courses";
        String url = "https://learnersmall.in/android/api/v2/student-course";
        RequestParams params = new RequestParams();
        params.put("student", student_id);
        params.put("course", course_id);

        WebServiceController wc = new WebServiceController(CourseSelectActivity.this,CourseSelectActivity.this);
        wc.sendRequest(url, params,1);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String course_id = jsonObject.getString("id").trim();
                        String course_name = jsonObject.getString("course_name").trim();
                        String course_photo = jsonObject.getString("course_photo").trim();
                        String course_selected = jsonObject.getString("status").trim();

                        gridViewId.add(course_id);
                        gridViewString.add(course_name);
                        gridViewImage.add(course_photo);
                        gridViewSelected.add(course_selected);
                    }

                    CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(this.getApplicationContext(), gridViewId, gridViewString, gridViewImage, gridViewSelected);
                    androidGridView=(GridView) findViewById(R.id.gridView);
                    androidGridView.setAdapter(adapterViewAndroid);
                    androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            //Toast.makeText(AstroFragment.this.getActivity(), "GridView Item: " + gridViewId.get(+i), Toast.LENGTH_LONG).show();
                            builder = new AlertDialog.Builder(CourseSelectActivity.this);
                            builder.setTitle("Select your course!");
                            builder.setMessage("Are you sure to avail this course? After selection you can get the course module, and you can eligible to participate in exam on about this course");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(getApplicationContext(), "Yes Selected", Toast.LENGTH_SHORT).show();
                                    String course_id = gridViewId.get(+i);
                                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                                        setCourse(course_id);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                                }
                            });

                            builder.show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    if (jsonObject.getString("status").trim().equalsIgnoreCase("true")) {
                        courseSelectSesManager.createCrSelectSession();
                        //Course avail
                        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                        // Closing all the Activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("error").trim(), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(int flag) {

    }
}
