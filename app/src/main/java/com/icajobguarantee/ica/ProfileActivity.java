package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements WebInterface {

    TextView profilename, text1, text2, text3, text4, text5, text6, text7, text8, course_text,
            batch_text;
    LoginSessionManager loginSessionManager;
    String student_id, course_string = "", StudentCode = "";
    ImageView image_edit, user_image, view_details, more_view;
    Button edit_profile_button;
    LinearLayout batch_layout, result_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        more_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result_view.getVisibility() == View.VISIBLE) {
                    result_view.setVisibility(View.GONE);
                    more_view.setImageResource(R.drawable.ic_add_box);
                } else {
                    result_view.setVisibility(View.VISIBLE);
                    more_view.setImageResource(R.drawable.ic_minus_box);
                    if (course_string.isEmpty()) {
                        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                            getStudentCourse(StudentCode);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageUploadActivity.class);
                startActivity(intent);
            }
        });

        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StudentCode.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), ProfileDetailsActivity.class);
                    intent.putExtra("StudentCode", StudentCode);
                    startActivity(intent);
                }
            }
        });
    }

    protected void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);

        profilename = (TextView) findViewById(R.id.profilename);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);
        text7 = (TextView) findViewById(R.id.text7);
        text8 = (TextView) findViewById(R.id.text8);
        course_text = (TextView) findViewById(R.id.course_text);

        image_edit = (ImageView) findViewById(R.id.image_edit);
        user_image = (ImageView) findViewById(R.id.user_image);
        view_details = (ImageView) findViewById(R.id.view_details);

        batch_layout = (LinearLayout) findViewById(R.id.batch_layout);
        batch_text = (TextView) findViewById(R.id.batch_text);

        edit_profile_button = (Button) findViewById(R.id.edit_profile_button);

        more_view = (ImageView) findViewById(R.id.more_view);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result_view.setVisibility(View.GONE);
    }

    private void getData(){
        String url = "https://learnersmall.in/android/api/v2/student-details";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(ProfileActivity.this,ProfileActivity.this);
        wc.sendRequest(url, params,0);
    }

    private void getStudentCourse(String StudentCode) {
        String url = "https://learnersmall.in/android/api/v2/find-student";
        RequestParams params = new RequestParams();
        //params.put("StudentCode", "A00010707");
        params.put("StudentCode", StudentCode);

        WebServiceController wc = new WebServiceController(ProfileActivity.this,ProfileActivity.this);
        wc.sendRequest(url, params,1);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
//                JSONArray jsonArray = new JSONArray(response);
//                if (jsonArray.length()>0) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                        profilename.setText(jsonObject.getString("name").trim());
//                        text1.setText(jsonObject.getString("code").trim());
//                        text2.setText(jsonObject.getString("email").trim());
//                        text3.setText(jsonObject.getString("mobile").trim());
//                        text4.setText(jsonObject.getString("state").trim());
//                        text5.setText(jsonObject.getString("city").trim());
//                        text6.setText(jsonObject.getString("centre").trim());
//                        text7.setText(jsonObject.getString("pincode").trim());
//                        text8.setText(jsonObject.getString("address").trim());
//
//                        StudentCode = jsonObject.getString("code").trim();
//
//                        getStudentCourse(StudentCode);
//
//                        if (!jsonObject.getString("profile_image").isEmpty()
//                                && !jsonObject.getString("profile_image").equalsIgnoreCase("null")) {
//                            String uimg = jsonObject.getString("profile_image");
//                            Picasso.with(this).invalidate(uimg);
//                            Picasso.with(this).load(uimg).into(user_image);
//                        }
//                    }
//                }
                JSONObject jsonObject = new JSONObject(response);
                JSONObject student = jsonObject.getJSONObject("students");

                profilename.setText(student.getString("name").trim());
                text1.setText(student.getString("code").trim());
                text2.setText(student.getString("email").trim());
                text3.setText(student.getString("mobile").trim());
                text4.setText(student.getString("state").trim());
                text5.setText(student.getString("city").trim());
                text6.setText(student.getString("centre").trim());
                text7.setText(student.getString("pincode").trim());
                text8.setText(student.getString("address").trim());

                StudentCode = student.getString("code").trim();

                if (!student.getString("profile_image").isEmpty()
                        && !student.getString("profile_image").equalsIgnoreCase("null")) {
                    String uimg = student.getString("profile_image");
                    Picasso.with(this).invalidate(uimg);
                    Picasso.with(this).load(uimg).into(user_image);
                }

                if (student.getString("batch_id").equalsIgnoreCase("0")) {
                    batch_layout.setVisibility(View.GONE);
                } else {
                    batch_layout.setVisibility(View.VISIBLE);

                    JSONObject batchObject = jsonObject.getJSONObject("batch");
                    String batch = batchObject.getString("batch").trim();
                    String studentclass = batchObject.getString("studentclass").trim();
                    String classyear = batchObject.getString("classyear").trim();

                    JSONObject tutorObject = jsonObject.getJSONObject("tutor");
                    String tutorName = tutorObject.getString("name").trim();


                    batch_text.setText("Tutor : " + tutorName + "\nBatch : " + batch + "\nClass : " + studentclass + "\nYear : " + classyear);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String course_name = jsonObject.getString("course_name").trim();
                        String admission = jsonObject.getString("admission").trim();
                        String lag = jsonObject.getString("lag").trim();
                        System.out.println(lag);
                        String span_color;
                        if (course_string.isEmpty()) {
//                            course_string += course_name + " (Admission Date : " + admission + ")";
                            if (lag.equalsIgnoreCase("")) {
                                course_string += "<p>" + course_name + "<br>(Admission Date : " + admission + ")</p>";
                            } else {
                                if (Math.floor(Float.parseFloat(lag))<=2) {
                                    span_color = "#00574B";
                                } else if (Math.floor(Float.parseFloat(lag))<4) {
                                    span_color = "#FFD700";
                                } else {
                                    span_color = "#FF0000";
                                }
                                course_string += "<p>" + course_name + "<br>(Admission Date : " + admission + ")<br>Lag : " + Math.floor(Float.parseFloat(lag)) + "<br>Status : <span style=\"background-color:" + span_color + ";\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p>";
                                //course_string += "<p>" + course_name + "<br>(Admission Date : " + admission + ")<br>Lag : " + Math.floor(Float.parseFloat(lag)) + "</p>";
                            }
                        } else {
//                            course_string += "\n\n" + course_name + " (Admission Date : " + admission + ")";
                            if (lag.equalsIgnoreCase("")) {
                                course_string += "<p>" + course_name + "<br>(Admission Date : " + admission + ")</p>";
                            } else {
                                if (Math.floor(Float.parseFloat(lag))<=2) {
                                    span_color = "#00574B";
                                } else if (Math.floor(Float.parseFloat(lag))<4) {
                                    span_color = "#FFD700";
                                } else {
                                    span_color = "#FF0000";
                                }
                                course_string += "<p>" + course_name + "<br>(Admission Date : " + admission + ")<br>Lag : " + Math.floor(Float.parseFloat(lag)) + "<br>Status : <span style=\"background-color:" + span_color + ";\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p>";
                                //course_string += "<p>" + course_name + "<br>(Admission Date : " + admission + ")<br>Lag : " + Math.floor(Float.parseFloat(lag)) + "</p>";
                            }
                        }
                        //course_text.setText(course_string);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            course_text.setText(Html.fromHtml(course_string, Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            course_text.setText(Html.fromHtml(course_string));
                        }
                        //course_text.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.circle_background_green,0);
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
