package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AssessmentResultActivity extends AppCompatActivity implements WebInterface {

    Button go_to_home;
    TextView paper_analysis, exam_name, no_question, duration, total_marks,
            your_marks, marks_percentage, rank, result_status;
    String exam_id, student_id, student_exam_id, note_text;


    String ans_qus_id, ques_text, qus_image, ques_type, ques_old_answer, ques_answer, correct_ans;

    LoginSessionManager loginSessionManager;
    ListView result_list;
    ArrayList<String> listQuesId, listQuesText, listQuesImage, listQuesType, listQuesCAns,
            listQuesGAns, listcorrectans, listnotetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_result);

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

//        paper_analysis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//                startActivity(intent);
//            }
//        });

        go_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);

        Intent intent = getIntent();
        student_exam_id = intent.getStringExtra("student_exam_id");
        exam_id = intent.getStringExtra("exam_id");

        go_to_home = (Button) findViewById(R.id.go_to_home);

//        exam_name = (TextView) findViewById(R.id.exam_name);
//        no_question = (TextView) findViewById(R.id.no_question);
//        duration = (TextView) findViewById(R.id.duration);
//        total_marks = (TextView) findViewById(R.id.total_marks);
//        your_marks = (TextView) findViewById(R.id.your_marks);
//        marks_percentage = (TextView) findViewById(R.id.marks_percentage);
//        rank = (TextView) findViewById(R.id.rank);
//        result_status = (TextView) findViewById(R.id.result_status);

        //paper_analysis = (TextView) findViewById(R.id.paper_analysis);
        result_list = (ListView) findViewById(R.id.result_list);
        listQuesId = new ArrayList<String>();
        listQuesText = new ArrayList<String>();
        listQuesImage = new ArrayList<String>();
        listQuesType = new ArrayList<String>();
        listQuesCAns = new ArrayList<String>();
        listQuesGAns = new ArrayList<String>();
        listcorrectans = new ArrayList<>();
        listnotetext = new ArrayList<String>();
    }

    private void getData(){
        String url = "https://learnersmall.in/android/api/v2/exam-result";
        RequestParams params = new RequestParams();
        params.put("exam", student_exam_id);
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(AssessmentResultActivity.this,AssessmentResultActivity.this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                //String full_marks = jsonObject.getString("full_marks").trim();
                String obtain_marks = jsonObject.getString("obtain_marks").trim();
                String total_duration = jsonObject.getString("total_duration").trim();
                int tot_dur = Integer.parseInt(total_duration);
                int min = (int) tot_dur / 60;
                int sec = (int) tot_dur % 60;

                String rank = jsonObject.getString("rank").trim();
                String rank_calculation = jsonObject.getString("rank_calculation").trim();

                JSONObject jsonObject1 = jsonObject.getJSONObject("exam");
                String ename = jsonObject1.getString("exam_name").trim();
                String efor = jsonObject1.getString("exam_for").trim();
                String etype = jsonObject1.getString("type").trim();

                JSONArray jsonQArray = jsonObject.getJSONArray("question");
                int f_marks = 0;
                for (int i = 0; i < jsonQArray.length(); i++) {
                    JSONObject jsonObject2 = jsonQArray.getJSONObject(i);
                    f_marks += Integer.parseInt(jsonObject2.getString("marks").trim());
                }

                int marks_per = Math.round(Integer.parseInt(obtain_marks) * 100 / f_marks);

                String string1 = ename;
                String string2 = String.valueOf(jsonQArray.length());
                //String string3 = String.valueOf(min) + ":" + String.valueOf(sec) + "Min.";
                String string3 = "Not Applicable";
                String string4 = String.valueOf(f_marks);
                String string5 = obtain_marks;
                String string6 = String.valueOf(marks_per) + "%";
                String string7 = efor;

                String result_text = string1 + ">=<" + string2 + ">=<" + string3 + ">=<" + string4 + ">=<" + string5 + ">=<" + string6 + ">=<" + string7;

                listQuesId.add("0");
                listQuesText.add(result_text);
                listQuesImage.add("");
                listQuesType.add("");
                listQuesCAns.add("");
                listQuesGAns.add("");
                listcorrectans.add("");
                listnotetext.add("");

                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(j);
                    ques_text = "text_blank";
                    qus_image = "image_blank";
                    ques_type = "";
                    ques_old_answer = "";
                    ques_answer = "";
                    correct_ans = "0";
                    note_text = "";

                    ans_qus_id = jsonObject2.getString("id");
                    correct_ans = jsonObject2.getString("ques_correct");

                    if (!jsonObject2.getString("ques_text").isEmpty()) {
                        ques_text = jsonObject2.getString("ques_text");
                    }
                    if (!jsonObject2.getString("qus_image").isEmpty()) {
                        qus_image = jsonObject2.getString("qus_image");
                    }
                    ques_type = jsonObject2.getString("ques_type");
                    if (ques_type.equalsIgnoreCase("accounting2")) {
                        ques_old_answer = String.valueOf(jsonObject2.getJSONArray("ques_old_answer"));
                        ques_answer = String.valueOf(jsonObject2.getJSONArray("ques_answer"));
                    } else {
                        ques_old_answer = jsonObject2.getString("ques_old_answer");
                        ques_answer = jsonObject2.getString("ques_answer");
                    }
                    if (!jsonObject2.getString("note_text").isEmpty()
                            && !jsonObject2.getString("note_text").equalsIgnoreCase("null")) {
                        note_text = jsonObject2.getString("note_text");
                    }

                    //System.out.println(ques_old_answer);
                    listQuesId.add(ans_qus_id);
                    listQuesText.add(ques_text);
                    listQuesImage.add(qus_image);
                    listQuesType.add(ques_type);
                    listQuesCAns.add(ques_old_answer);
                    listQuesGAns.add(ques_answer);
                    listcorrectans.add(correct_ans);
                    listnotetext.add(note_text);
                }

                ResultListView resultListView = new ResultListView(this, listQuesId, listQuesText, listQuesImage, listQuesType, listQuesCAns, listQuesGAns, listcorrectans, listnotetext);
                result_list.setAdapter(resultListView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(int flag) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
