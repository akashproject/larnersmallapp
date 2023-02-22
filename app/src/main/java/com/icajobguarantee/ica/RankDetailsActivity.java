package com.icajobguarantee.ica;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.icajobguarantee.ica.ImageUploadActivity.IMAGE_DIRECTORY_NAME;

public class RankDetailsActivity extends AppCompatActivity implements WebInterface {

    TextView go_to_share, header_title;
    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Learnersmall";
    TextView paper_analysis, exam_name, no_question, duration, total_marks,
            your_marks, marks_percentage, rank, result_status;
    String exam_id, student_id, student_exam_id, note_text, student_name, ename;


    String ans_qus_id, ques_text, qus_image, ques_type, ques_old_answer, ques_answer, correct_ans;

    LoginSessionManager loginSessionManager;
    ListView result_list;
    ArrayList<String> listQuesId, listQuesText, listQuesImage, listQuesType, listQuesCAns,
            listQuesGAns, listcorrectans, listnotetext;
    File imagePath;
    ImageView back_press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRuntimePermission();
        setContentView(R.layout.activity_rank_details);

        init();

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getData();
            studentData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

        go_to_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
                shareIt();
            }
        });

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 onBackPressed();
            }
        });
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                // return null;
            }
        }

        imagePath = new File(mediaStorageDir.getPath() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "My achievement in  " + ename;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My achievement in " + ename);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);

        Intent intent = getIntent();
        student_exam_id = intent.getStringExtra("student_exam_id");
//        exam_id = intent.getStringExtra("exam_id");

        header_title = (TextView) findViewById(R.id.header_title);
        back_press = (ImageView) findViewById(R.id.back_press);
        go_to_share = (TextView) findViewById(R.id.go_to_share);

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

        WebServiceController wc = new WebServiceController(RankDetailsActivity.this,RankDetailsActivity.this);
        wc.sendRequest(url, params,0);
    }

    private void studentData(){
        String url = "https://learnersmall.in/android/api/v2/student-details";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(RankDetailsActivity.this,RankDetailsActivity.this);
        wc.sendRequest(url, params,1);
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

                String created_at = jsonObject.getString("created_at").trim();
                String fdatearr[] = created_at.split(" ");
                String datearr[] = fdatearr[0].split("-");
                String examdate = datearr[2] + "/" + datearr[1] + "/" + datearr[0];

                JSONObject jsonObject1 = jsonObject.getJSONObject("exam");
                ename = jsonObject1.getString("exam_name").trim();
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
                String string3 = String.valueOf(min) + ":" + String.valueOf(sec) + "Min.";
                String string4 = String.valueOf(f_marks);
                String string5 = obtain_marks;
                String string6 = String.valueOf(marks_per) + "%";
                String string7 = efor;
                String string8 = examdate;

                String result_text = string1 + ">=<" + string2 + ">=<" + string3 + ">=<" + string4
                        + ">=<" + string5 + ">=<" + string6 + ">=<" + rank + ">=<"
                        + rank_calculation + ">=<" + string7 + ">=<" + string8;

                listQuesId.add("0");
                listQuesText.add(result_text);
                listQuesImage.add("");
                listQuesType.add("");
                listQuesCAns.add("");
                listQuesGAns.add("");
                listcorrectans.add("");
                listnotetext.add("");
//                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                for (int j = 0; j < jsonArray.length(); j++) {
//                    JSONObject jsonObject2 = jsonArray.getJSONObject(j);
//                    ques_text = "text_blank";
//                    qus_image = "image_blank";
//                    ques_type = "";
//                    ques_old_answer = "";
//                    ques_answer = "";
//                    correct_ans = "0";
//                        note_text = "";
//
//                    ans_qus_id = jsonObject2.getString("id");
//                    correct_ans = jsonObject2.getString("ques_correct");
//
//                    if (!jsonObject2.getString("ques_text").isEmpty()) {
//                        ques_text = jsonObject2.getString("ques_text");
//                    }
//                    if (!jsonObject2.getString("qus_image").isEmpty()) {
//                        qus_image = jsonObject2.getString("qus_image");
//                    }
//                    ques_type = jsonObject2.getString("ques_type");
//                    if (ques_type.equalsIgnoreCase("accounting2")) {
//                        ques_old_answer = String.valueOf(jsonObject2.getJSONArray("ques_old_answer"));
//                        ques_answer = String.valueOf(jsonObject2.getJSONArray("ques_answer"));
//                    } else {
//                        ques_old_answer = jsonObject2.getString("ques_old_answer");
//                        ques_answer = jsonObject2.getString("ques_answer");
//                    }
//                        if (!jsonObject2.getString("note_text").isEmpty()
//                                && !jsonObject2.getString("note_text").equalsIgnoreCase("null")) {
//                            note_text = jsonObject2.getString("note_text");
//                        }
//
//                    //System.out.println(ques_old_answer);
//                    listQuesId.add(ans_qus_id);
//                    listQuesText.add(ques_text);
//                    listQuesImage.add(qus_image);
//                    listQuesType.add(ques_type);
//                    listQuesCAns.add(ques_old_answer);
//                    listQuesGAns.add(ques_answer);
//                    listcorrectans.add(correct_ans);
//                        listnotetext.add(note_text);
//                }

                RankListView rankListView = new RankListView(this, listQuesId, listQuesText, listQuesImage, listQuesType, listQuesCAns, listQuesGAns, listcorrectans, listnotetext);
                result_list.setAdapter(rankListView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject student = jsonObject.getJSONObject("students");
                student_name = student.getString("name").trim();
                header_title.setText(student_name);
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

    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // Toast.makeText(RankDetailsActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    // requestRuntimePermission();
                    go_to_share.setVisibility(View.GONE);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
