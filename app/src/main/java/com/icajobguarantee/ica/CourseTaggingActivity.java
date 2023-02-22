package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CourseTaggingActivity extends AppCompatActivity implements WebInterface {

    Button continue_button;
    CourseSelectSesManager courseSelectSesManager;
    LoginSessionManager loginSessionManager;
    public String student_id, admin_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tagging);
        init();
    }

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);
        admin_id = loginSessionDetails.get(LoginSessionManager.KEY_ADMINID_SES);

        courseSelectSesManager = new CourseSelectSesManager(getApplicationContext());

        continue_button = (Button) findViewById(R.id.continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                    //verifyVerificationCode(otp.getText().toString().trim());
                    getData();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getData(){
        //Log.d("task_start","start");
        //qfor = "submit_otp";
        String url = "https://www.learnersmall.in/android/api/v2/courseverification";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(CourseTaggingActivity.this,CourseTaggingActivity.this);
        wc.sendRequest(url, params,0);

    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    HashMap<String, String> loginIndata = new HashMap<>();
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
