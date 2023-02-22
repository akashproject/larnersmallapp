package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportCourse extends AppCompatActivity implements WebInterface {

    ArrayList<String> listViewId, listViewName, listViewRProgress, listViewAProgress,
            listViewTProgress;
    public String qfor, student_id;
    LoginSessionManager loginSessionManager;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_course);

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

        listView = (ListView) findViewById(R.id.listView);
        listViewId = new ArrayList<String>();
        listViewName = new ArrayList<String>();
        listViewRProgress = new ArrayList<String>();
        listViewAProgress = new ArrayList<String>();
        listViewTProgress = new ArrayList<String>();
    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/allcourseprogress";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        Log.e("params_value", String.valueOf(params));

        WebServiceController wc = new WebServiceController(ReportCourse.this,ReportCourse.this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("all_course");
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String course_id = jsonObject1.getString("course_id").trim();
                        String course_name = jsonObject1.getString("course_name").trim();
                        String per_read = jsonObject1.getString("per_read").trim();
                        String per_assess = jsonObject1.getString("per_assess").trim();
                        String per_tot = jsonObject1.getString("per_tot").trim();

                        listViewId.add(course_id);
                        listViewName.add(course_name);
                        listViewRProgress.add(per_read);
                        listViewAProgress.add(per_assess);
                        listViewTProgress.add(per_tot);

                    }

                    ListCustom listCustom = new ListCustom(this, listViewId, listViewName, listViewRProgress, listViewAProgress, listViewTProgress);
                    listView.setAdapter(listCustom);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            Intent intent = new Intent(getApplicationContext(), ReportTopic.class);
                            intent.putExtra("course", listViewId.get(+i));
                            intent.putExtra("course_name", listViewName.get(+i));
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
