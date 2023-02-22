package com.icajobguarantee.ica;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.fragment.app.FragmentManager;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ChapterActivity extends AppCompatActivity implements WebInterface {

    ArrayList<String> listViewId, listViewImage, listViewString, listViewCode, listViewTime,
            listViewDetails, listViewVideo, listTotalChap, listReadChap;
    public String qfor, student_id, list_id, list_title, cou_id, cou_title, read_chap = "0",
            total_chap = "0", activity_for;
    LoginSessionManager loginSessionManager;
    ListView androidListView;
    Dialog pDialog;
    TextView header_title;
    ImageView back_press;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        init();

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
//                intent.putExtra("activity_for", activity_for);
//                intent.putExtra("list_id", cou_id);
//                intent.putExtra("list_title", cou_title);
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//                intent.putExtra("activity_for", activity_for);
//                intent.putExtra("list_id", cou_id);
//                intent.putExtra("list_title", cou_title);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
//                onBackPressed();
            }
        });

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

        Intent intent = getIntent();
        activity_for = intent.getStringExtra("activity_for");
        list_id = intent.getStringExtra("list_id");
        list_title = intent.getStringExtra("list_title");
//        cou_id = intent.getStringExtra("cou_id");
//        cou_title = intent.getStringExtra("cou_title");
//        Log.d("intent_data_value : ", list_id + ", " + list_title + ", " + cou_id + ", " + cou_title);

        header_title.setText(list_title);

        androidListView = (ListView) findViewById(R.id.listView);
        listViewId = new ArrayList<String>();
        listViewImage = new ArrayList<String>();
        listViewString = new ArrayList<String>();
        listViewCode = new ArrayList<String>();
        listViewTime = new ArrayList<String>();
        listViewDetails = new ArrayList<String>();
        listViewVideo = new ArrayList<String>();
        listTotalChap = new ArrayList<String>();
        listReadChap = new ArrayList<String>();
    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/subject/chapter";
        RequestParams params = new RequestParams();
        //params.put("subject_id", list_id);
        params.put("course_id", list_id);
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(ChapterActivity.this,ChapterActivity.this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
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

                        l_id = jsonObject.getString("id").trim();
                        l_code = jsonObject.getString("chapter_code").trim();
                        l_name = jsonObject.getString("chapter_name").trim();
                        l_details = jsonObject.getString("chapter_details").trim();
                        JSONObject jsonObject1 = jsonObject.getJSONObject("created_at");
                        l_created = jsonObject1.getString("date").trim();
                        total_chap = jsonObject.getString("count").trim();
                        read_chap = jsonObject.getString("read_count").trim();

                        listViewId.add(l_id);
                        listViewString.add(l_name);
                        listViewCode.add("Code : " + l_code);
                        listViewTime.add("Created On : " + l_created);
                        listViewImage.add(l_image);
                        listViewDetails.add(l_details);
                        listViewVideo.add(l_video);
                        listTotalChap.add(total_chap);
                        listReadChap.add(read_chap);

                    }

                    ChapterListView adapterViewAndroid = new ChapterListView(this, listViewId, listViewString, listViewImage, listViewCode, listViewTime, listViewDetails, listViewVideo, listTotalChap, listReadChap);
                    androidListView.setAdapter(adapterViewAndroid);
                    androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            //Toast.makeText(getActivity(), "GridView Item: " + listViewId.get(+i), Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(getApplicationContext(), ChapterDetailsActivity.class);
//                            intent.putExtra("list_id", listViewId.get(+i));
//                            intent.putExtra("list_title", listViewString.get(+i));
//                            startActivity(intent);
//                            System.out.println(i);

                            if (activity_for.equalsIgnoreCase("course")) {
                                if (i == 0) {
                                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                                    intent.putExtra("activity_for", activity_for);
                                    intent.putExtra("list_id", listViewId.get(+i));
                                    intent.putExtra("list_title", listViewString.get(+i));
                                    intent.putExtra("cou_id", list_id);
                                    intent.putExtra("cou_title", list_title);
//                                    intent.putExtra("cou_id", cou_id);
//                                    intent.putExtra("cou_title", cou_title);
//                                    intent.putExtra("sub_id", list_id);
//                                    intent.putExtra("sub_title", list_title);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    int j = i - 1;
//                                System.out.println(j);
                                    if (Integer.parseInt(listReadChap.get(j)) != 0) {
                                        int per_progress = (int) (Integer.parseInt(listTotalChap.get(j)) * 100) / Integer.parseInt(listReadChap.get(j));
                                        if (per_progress == 100) {
                                            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                                            intent.putExtra("activity_for", activity_for);
                                            intent.putExtra("list_id", listViewId.get(+i));
                                            intent.putExtra("list_title", listViewString.get(+i));
                                            intent.putExtra("cou_id", list_id);
                                            intent.putExtra("cou_title", list_title);
//                                          intent.putExtra("cou_id", cou_id);
//                                          intent.putExtra("cou_title", cou_title);
//                                          intent.putExtra("sub_id", list_id);
//                                          intent.putExtra("sub_title", list_title);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please complete the previous chapter", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please complete the previous chapter", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else if (activity_for.equalsIgnoreCase("progress")) {
                                Intent intent = new Intent(getApplicationContext(), ChapterReportActivity.class);
                                intent.putExtra("list_id", listViewId.get(+i));
                                intent.putExtra("list_title", listViewString.get(+i));
                                intent.putExtra("cou_id", list_id);
                                intent.putExtra("cou_title", list_title);
//                                intent.putExtra("sub_id", list_id);
//                                intent.putExtra("sub_title", list_title);
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
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//        intent.putExtra("activity_for", activity_for);
//        intent.putExtra("list_id", cou_id);
//        intent.putExtra("list_title", cou_title);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }
}
