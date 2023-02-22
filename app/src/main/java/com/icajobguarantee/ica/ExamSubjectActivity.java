package com.icajobguarantee.ica;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamSubjectActivity extends AppCompatActivity implements WebInterface {

    ArrayList<String> listViewId, listViewImage, listViewString, listViewCode, listViewTime, listViewDetails, listViewVideo;
    public String qfor, student_id, list_id, list_title;
    LoginSessionManager loginSessionManager;
    ListView androidListView;
    Dialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_subject);

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
        list_id = intent.getStringExtra("list_id");
        list_title = intent.getStringExtra("list_title");

        androidListView = (ListView) findViewById(R.id.listView);
        listViewId = new ArrayList<String>();
        listViewImage = new ArrayList<String>();
        listViewString = new ArrayList<String>();
        listViewCode = new ArrayList<String>();
        listViewTime = new ArrayList<String>();
        listViewDetails = new ArrayList<String>();
        listViewVideo = new ArrayList<String>();
    }

    private void getData() {
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/course/subject";
        RequestParams params = new RequestParams();
        params.put("course_id", list_id);

        WebServiceController wc = new WebServiceController(ExamSubjectActivity.this, ExamSubjectActivity.this);
        wc.sendRequest(url, params, 0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String l_id = "";
                        String l_code = "";
                        String l_name = "";
                        String l_image = "";
                        String l_details = "";
                        String l_created = "";
                        String l_video = "";

                        l_id = jsonObject.getString("id").trim();
                        l_code = jsonObject.getString("subject_code").trim();
                        l_name = jsonObject.getString("subject_name").trim();
                        l_created = jsonObject.getString("created_at").trim();

                        listViewId.add(l_id);
                        listViewString.add(l_name);
                        listViewCode.add("Code : " + l_code);
                        listViewTime.add("Created On : " + l_created);
                        listViewImage.add(l_image);
                        listViewDetails.add(l_details);
                        listViewVideo.add(l_video);
                    }

                    CustomListView adapterViewAndroid = new CustomListView(this, listViewId, listViewString, listViewImage, listViewCode, listViewTime, listViewDetails, listViewVideo);
                    androidListView.setAdapter(adapterViewAndroid);
                    androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            //Toast.makeText(getActivity(), "GridView Item: " + listViewId.get(+i), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), ExamListActivity.class);
                            intent.putExtra("list_id", listViewId.get(+i));
                            intent.putExtra("list_title", listViewString.get(+i));
                            intent.putExtra("course_id", list_id);
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
