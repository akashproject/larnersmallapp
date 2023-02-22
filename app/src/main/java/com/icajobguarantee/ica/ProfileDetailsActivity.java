package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileDetailsActivity extends AppCompatActivity implements WebInterface {

    ListView academic_list;
    LoginSessionManager loginSessionManager;
    String student_id, StudentCode = "";
    static ArrayList<HashMap<String, String>> dataList;
    AcademicAdapter adapter;
    public static final String TAG_COURSE_ID = "courseId";
    public static final String TAG_COURSE_NAME = "courseName";
    public static final String TAG_ADMISSION_DATE = "admissionDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        init();
    }

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);


        Intent intent = getIntent();
        StudentCode = intent.getStringExtra("StudentCode");

        academic_list = (ListView) findViewById(R.id.academic_list);
        dataList = new ArrayList<HashMap<String,String>>();

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

    private void getData(){
        String url = "https://new.icaerp.com/api/Data/searchstudent";
        RequestParams params = new RequestParams();
        params.put("StudentCode", StudentCode);

        WebServiceController wc = new WebServiceController(ProfileDetailsActivity.this,ProfileDetailsActivity.this);
        wc.sendRequest(url, params,0);

    }

    /**
     * react to the user tapping the back/up icon in the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {

                    JSONArray jsonArray = jsonObject.getJSONArray("courses");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HashMap<String, String> catdata = new HashMap<>();
                        catdata.put(TAG_COURSE_ID, jsonObject1.getString("courseid").trim());
                        catdata.put(TAG_COURSE_NAME, jsonObject1.getString("coursename").trim());
                        catdata.put(TAG_ADMISSION_DATE, jsonObject1.getString("admission").trim());
                        dataList.add(catdata);
                    }
                    adapter = new AcademicAdapter(ProfileDetailsActivity.this, dataList);
                    academic_list.setAdapter(adapter);

                    academic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final String courseid = ((TextView) view.findViewById(R.id.courseid)).getText().toString();

                            //Toast.makeText(getApplicationContext(), courseid, Toast.LENGTH_LONG).show();
                            //Intent i = new Intent(getApplicationContext(), EmailVerification.class);
                            //startActivity(i);
                            Intent intent = new Intent(getApplicationContext(), AcademicReportActivity.class);
                            intent.putExtra("StudentCode", StudentCode);
                            intent.putExtra("CourseId", courseid);
                            startActivity(intent);
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
}
