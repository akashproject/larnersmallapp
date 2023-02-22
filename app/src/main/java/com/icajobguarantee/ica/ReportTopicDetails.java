package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
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

public class ReportTopicDetails extends AppCompatActivity implements WebInterface {

    TextView chapter_title;
    ArrayList<String> listViewId, listViewName, listViewRProgress, listViewAProgress,
            listViewTProgress;
    public String qfor, student_id, chapter_id;
    LoginSessionManager loginSessionManager;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_topic_details);

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

        chapter_title = (TextView) findViewById(R.id.chapter_title);
        Intent intent = getIntent();
        chapter_id = intent.getStringExtra("chapter");
        chapter_title.setText(intent.getStringExtra("chapter_name"));

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

        WebServiceController wc = new WebServiceController(ReportTopicDetails.this,ReportTopicDetails.this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("assessment_details");
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String id = jsonObject1.getString("id").trim();
                        String chapter = jsonObject1.getString("chapter").trim();
                        String exam_name = jsonObject1.getString("exam_name").trim();
                        String mark_per = jsonObject1.getString("mark_per").trim();

                        if (chapter_id.equalsIgnoreCase(chapter)) {
                            listViewId.add(id);
                            listViewName.add(exam_name);
                            listViewRProgress.add("");
                            listViewAProgress.add("");
                            listViewTProgress.add(mark_per);
                        }

                    }

                    ListCustom listCustom = new ListCustom(this, listViewId, listViewName, listViewRProgress, listViewAProgress, listViewTProgress);
                    listView.setAdapter(listCustom);
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
