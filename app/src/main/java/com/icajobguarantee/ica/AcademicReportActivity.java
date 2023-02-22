package com.icajobguarantee.ica;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AcademicReportActivity extends AppCompatActivity implements WebInterface {

    ListView academic_list, academic_list_ano, academic_alist_ano;
    LoginSessionManager loginSessionManager;
    String student_id, StudentCode = "", CourseId = "", pass_status = "";
    static ArrayList<HashMap<String, String>> dataList, dataList_ano, dataList_aano;
    AcademicReportAdapter adapter;
    AcademicDetReportAdapter adapter1, adapter2;
    public static final String TAG_SUBJECT_NAME = "subjectName";
    public static final String TAG_SUBJECT_FLAG = "subjectFlag";
    public static final String TAG_SUB_NAME = "subName";
    public static final String TAG_SUB_MARKS = "subMarksPer";
    public static final String TAG_SUB_ATTEND = "subAttendance";
    public static final String TAG_SUB_PASS = "subPass";
    public static final String TAG_MODULE_STATUS = "moduleStatus";
    public static final String TAG_BATCH_STDATE = "batchStart";
    public static final String TAG_BATCH_ENDATE = "batchEnd";
    public static final String TAG_VIEW_FOR = "viewFor";

    ImageView back_press;
    TextView details_block, report_block, attendance_block;
    LinearLayout details_res_block, report_res_block, attent_res_block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_report);

        init();

        details_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details_block.setBackgroundResource(R.drawable.border_bottom_bgray);
                report_block.setBackgroundResource(R.drawable.border_bottom_trans);
                attendance_block.setBackgroundResource(R.drawable.border_bottom_trans);
                details_res_block.setVisibility(View.VISIBLE);
                report_res_block.setVisibility(View.GONE);
                attent_res_block.setVisibility(View.GONE);
            }
        });

        report_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details_block.setBackgroundResource(R.drawable.border_bottom_trans);
                report_block.setBackgroundResource(R.drawable.border_bottom_bgray);
                attendance_block.setBackgroundResource(R.drawable.border_bottom_trans);
                details_res_block.setVisibility(View.GONE);
                report_res_block.setVisibility(View.VISIBLE);
                attent_res_block.setVisibility(View.GONE);
            }
        });

        attendance_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details_block.setBackgroundResource(R.drawable.border_bottom_trans);
                report_block.setBackgroundResource(R.drawable.border_bottom_trans);
                attendance_block.setBackgroundResource(R.drawable.border_bottom_bgray);
                details_res_block.setVisibility(View.GONE);
                report_res_block.setVisibility(View.GONE);
                attent_res_block.setVisibility(View.VISIBLE);
            }
        });

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);


        Intent intent = getIntent();
        StudentCode = intent.getStringExtra("StudentCode");
        CourseId = intent.getStringExtra("CourseId");

        academic_list = (ListView) findViewById(R.id.academic_list);
        academic_list_ano = (ListView) findViewById(R.id.academic_list_ano);
        academic_alist_ano = (ListView) findViewById(R.id.academic_alist_ano);

        details_block = (TextView) findViewById(R.id.details_block);
        report_block = (TextView) findViewById(R.id.report_block);
        attendance_block = (TextView) findViewById(R.id.attendance_block);

        details_res_block = (LinearLayout) findViewById(R.id.details_res_block);
        report_res_block = (LinearLayout) findViewById(R.id.report_res_block);
        attent_res_block = (LinearLayout) findViewById(R.id.attent_res_block);

        dataList = new ArrayList<HashMap<String,String>>();
        dataList_ano = new ArrayList<HashMap<String,String>>();
        dataList_aano = new ArrayList<HashMap<String,String>>();
        back_press = (ImageView) findViewById(R.id.back_press);

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

        ConnectivityManager connec1 = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec1.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec1.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec1.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec1.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getDataAno();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void getData(){
        String url = "https://new.icaerp.com/api/Data/subjectstatus";
        RequestParams params = new RequestParams();
        params.put("StudentCode", StudentCode);
        params.put("CourseId", CourseId);

        WebServiceController wc = new WebServiceController(AcademicReportActivity.this,AcademicReportActivity.this);
        wc.sendRequest(url, params,0);

    }

    private void getDataAno(){
        String url = "https://new.icaerp.com/api/Data/subjectDetailStatus";
        RequestParams params = new RequestParams();
        params.put("StudentCode", StudentCode);
        params.put("CourseId", CourseId);

        WebServiceController wc = new WebServiceController(AcademicReportActivity.this,AcademicReportActivity.this);
        wc.sendRequest(url, params,1);

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
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HashMap<String, String> catdata = new HashMap<>();
                        catdata.put(TAG_SUBJECT_NAME, jsonObject1.getString("Subject_name").trim());
                        catdata.put(TAG_SUBJECT_FLAG, jsonObject1.getString("flag").trim());
                        dataList.add(catdata);
                    }
                    adapter = new AcademicReportAdapter(AcademicReportActivity.this, dataList);
                    academic_list.setAdapter(adapter);

//                    academic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            final String courseid = ((TextView) view.findViewById(R.id.courseid)).getText().toString();
//
//                            //Toast.makeText(getApplicationContext(), courseid, Toast.LENGTH_LONG).show();
//                            //Intent i = new Intent(getApplicationContext(), EmailVerification.class);
//                            //startActivity(i);
//                        }
//                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HashMap<String, String> catdata = new HashMap<>();
                        catdata.put(TAG_SUB_NAME, jsonObject1.getString("SUBJECT_GROUP_NAME").trim());
                        catdata.put(TAG_SUB_MARKS, jsonObject1.getString("MARKSPERCENT").trim());
                        catdata.put(TAG_SUB_ATTEND, jsonObject1.getString("ATTENPERCENT").trim());
                        catdata.put(TAG_MODULE_STATUS, jsonObject1.getString("SUBJECT_STATUS").trim());
                        catdata.put(TAG_BATCH_STDATE, jsonObject1.getString("ATTENPERCENT").trim());
                        catdata.put(TAG_BATCH_ENDATE, jsonObject1.getString("SUBJECT_STATUS").trim());

                        if (!jsonObject1.getString("PASS").trim().isEmpty()) {
                            pass_status = "Pass";
                        } else if (!jsonObject1.getString("FAIL").trim().isEmpty()) {
                            pass_status = "Fail";
                        }
                        catdata.put(TAG_SUB_PASS, pass_status);
                        catdata.put(TAG_VIEW_FOR, "Marks");

                        dataList_ano.add(catdata);
                    }
                    adapter1 = new AcademicDetReportAdapter(AcademicReportActivity.this, dataList_ano);
                    academic_list_ano.setAdapter(adapter1);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject12 = jsonArray.getJSONObject(i);
                        HashMap<String, String> catdata1 = new HashMap<>();
                        catdata1.put(TAG_SUB_NAME, jsonObject12.getString("SUBJECT_GROUP_NAME").trim());
                        catdata1.put(TAG_SUB_MARKS, jsonObject12.getString("MARKSPERCENT").trim());
                        catdata1.put(TAG_SUB_ATTEND, jsonObject12.getString("ATTENPERCENT").trim());
                        catdata1.put(TAG_MODULE_STATUS, jsonObject12.getString("SUBJECT_STATUS").trim());
                        catdata1.put(TAG_BATCH_STDATE, jsonObject12.getString("BATCHSTARTDATE").trim());
                        catdata1.put(TAG_BATCH_ENDATE, jsonObject12.getString("BATCHENDDATE").trim());

                        if (!jsonObject12.getString("PASS").trim().isEmpty()) {
                            pass_status = "Pass";
                        } else if (!jsonObject12.getString("FAIL").trim().isEmpty()) {
                            pass_status = "Fail";
                        }
                        catdata1.put(TAG_SUB_PASS, pass_status);
                        catdata1.put(TAG_VIEW_FOR, "Attendance");

                        dataList_aano.add(catdata1);
                    }
                    adapter2 = new AcademicDetReportAdapter(AcademicReportActivity.this, dataList_aano);
                    academic_alist_ano.setAdapter(adapter2);
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