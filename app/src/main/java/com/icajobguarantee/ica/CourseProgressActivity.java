package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseProgressActivity extends AppCompatActivity implements WebInterface {

    ArrayList<String> gridViewId, gridViewString, gridViewImage, gridViewSelected;
    public String qfor, student_id;
    LoginSessionManager loginSessionManager;
    GridView androidGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_progress);

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

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);

        Intent intent = getIntent();
        qfor = intent.getStringExtra("activity_for");

        androidGridView = (GridView) findViewById(R.id.gridView);
        gridViewId = new ArrayList<String>();
        gridViewString = new ArrayList<String>();
        gridViewImage = new ArrayList<String>();
        gridViewSelected = new ArrayList<String>();
    }

    private void getData(){
        String url = "https://learnersmall.in/android/api/v2/course";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(CourseProgressActivity.this,CourseProgressActivity.this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                System.out.print("responseBody " + response);
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String course_id = jsonObject.getString("id").trim();
                        String course_name = jsonObject.getString("course_name").trim();
                        String course_photo = jsonObject.getString("course_photo").trim();
                        String course_selected = jsonObject.getString("status").trim();



                        if (course_selected.equalsIgnoreCase("1")) {
                            gridViewId.add(course_id);
                            gridViewString.add(course_name);
                            gridViewImage.add(course_photo);
                            gridViewSelected.add(course_selected);
                        }
                    }

                    CustomGridViewFragment adapterViewAndroid = new CustomGridViewFragment(getApplicationContext(), gridViewId, gridViewString, gridViewImage, gridViewSelected);
                    androidGridView=(GridView) findViewById(R.id.gridView);
                    androidGridView.setAdapter(adapterViewAndroid);
                    androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            //Toast.makeText(getActivity(), "GridView Item: " + gridViewId.get(+i), Toast.LENGTH_LONG).show();
                            if (qfor.equalsIgnoreCase("course")) {
                                Intent intent = new Intent(getApplicationContext(), CourseReportActivity.class);
                                intent.putExtra("course_id", gridViewId.get(+i));
                                intent.putExtra("course_title", gridViewString.get(+i));
                                startActivity(intent);
                            } else if (qfor.equalsIgnoreCase("chapter")) {
                                Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
                                intent.putExtra("activity_for", "progress");
                                intent.putExtra("list_id", gridViewId.get(+i));
                                intent.putExtra("list_title", gridViewString.get(+i));
                                startActivity(intent);
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(int flag) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
