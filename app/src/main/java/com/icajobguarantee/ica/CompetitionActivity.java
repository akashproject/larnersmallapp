package com.icajobguarantee.ica;

import android.app.Dialog;
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

public class CompetitionActivity extends AppCompatActivity implements WebInterface {

    ArrayList<String> listViewId, listViewImage, listViewString, listViewCode, listViewTime,
            listViewDetails, listViewVideo;
    public String qfor, student_id, list_id, list_title;
    LoginSessionManager loginSessionManager;
    ListView androidListView;
    Dialog pDialog;
    TextView header_title;
    ImageView back_press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        init();
        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        header_title = (TextView) findViewById(R.id.header_title);
        back_press = (ImageView) findViewById(R.id.back_press);

        androidListView = (ListView) findViewById(R.id.listView);
        listViewId = new ArrayList<String>();
        listViewImage = new ArrayList<String>();
        listViewString = new ArrayList<String>();
        listViewCode = new ArrayList<String>();
        listViewTime = new ArrayList<String>();
        listViewDetails = new ArrayList<String>();
        listViewVideo = new ArrayList<String>();
    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/exam";
        RequestParams params = new RequestParams();
        params.put("student", student_id);
        params.put("exam_for", "2");

        WebServiceController wc = new WebServiceController(CompetitionActivity.this,CompetitionActivity.this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
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
//                        l_code = jsonObject.getString("exam_code").trim();
//                        String exam_type = jsonObject.getString("type").trim();
                        if (!jsonObject.isNull("exam_details")) {
                            l_code = jsonObject.getString("exam_details").trim();
                        }

                        listViewId.add(l_id);
                        listViewString.add(l_name);
                        listViewCode.add(l_code);
                        listViewTime.add(exam_type);
                        listViewImage.add(l_image);
                        listViewDetails.add(l_details);
                        listViewVideo.add(l_video);
                    }

                    CustomListView adapterViewAndroid = new CustomListView(getApplicationContext(), listViewId, listViewString, listViewImage, listViewCode, listViewTime, listViewDetails, listViewVideo);
                    androidListView.setAdapter(adapterViewAndroid);
                    androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            //Toast.makeText(getApplicationContext(), "Exam - " + listViewId.get(+i), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), CompetitionDetailsActivity.class);
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
