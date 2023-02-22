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

public class RankHistoryActivity extends AppCompatActivity implements WebInterface {

    ListView rank_list;
    LoginSessionManager loginSessionManager;
    String student_id;
    static ArrayList<HashMap<String, String>> dataList;
    RankAdapter adapter;
    public static final String TAG_RANK_ID = "rankId";
    public static final String TAG_EXAM_NAME = "examName";
    public static final String TAG_EXAM_FOR = "examFor";
    public static final String TAG_OBTAIN_MARKS = "obtainMarks";
    public static final String TAG_TIME_TAKEN = "timeTaken";
    public static final String TAG_DATE_TIME = "dateTime";
    public static final String TAG_RANK = "rank";
    public static final String TAG_STATUS = "rankStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_history);

        init();
    }

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);


        rank_list = (ListView) findViewById(R.id.rank_list);
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
        String url = "https://learnersmall.in/android/api/v2/rank-view";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(RankHistoryActivity.this,RankHistoryActivity.this);
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
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id").trim();
                        String exam_name = jsonObject.getString("exam_name").trim();
                        String exam_for = jsonObject.getString("exam_for").trim();
                        String obtain_marks = jsonObject.getString("marks_percent").trim();
                        String rank = jsonObject.getString("rank").trim();
                        String time_taken = jsonObject.getString("time_taken").trim();
                        String date_time = jsonObject.getString("date_time").trim();
                        String rank_status = jsonObject.getString("status").trim();

                        int tot_dur = Integer.parseInt(time_taken);
                        int min = (int) tot_dur / 60;
                        int sec = (int) tot_dur % 60;

                        HashMap<String, String> catdata = new HashMap<>();
                        catdata.put(TAG_RANK_ID, id);
                        catdata.put(TAG_EXAM_NAME, exam_name);
                        catdata.put(TAG_EXAM_FOR, exam_for);
                        catdata.put(TAG_OBTAIN_MARKS, obtain_marks);
                        catdata.put(TAG_RANK, rank);
                        catdata.put(TAG_STATUS, rank_status);
                        catdata.put(TAG_TIME_TAKEN, String.valueOf(min) + ":" + String.valueOf(sec) + "Min.");
                        catdata.put(TAG_DATE_TIME,date_time);
                        dataList.add(catdata);
                    }

                    adapter = new RankAdapter(RankHistoryActivity.this, dataList);
                    rank_list.setAdapter(adapter);
                    rank_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final String student_exam_id = ((TextView) view.findViewById(R.id.rank_id)).getText().toString();
                            final String exam_for = ((TextView) view.findViewById(R.id.exam_for)).getText().toString();

                            if (exam_for.equalsIgnoreCase("2")) {
                                Intent intent = new Intent(getApplicationContext(), RankDetailsActivity.class);
                                intent.putExtra("student_exam_id", student_exam_id);
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
}
