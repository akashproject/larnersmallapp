package com.icajobguarantee.ica;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity implements WebInterface {

    TextView profilename, text1;
    EditText etext2, etext3, etext4, etext5, etext6, etext7, etext8;
    LoginSessionManager loginSessionManager;
    String student_id;
    ImageView image_edit, user_image;
    Button update_button;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etext2.getText().toString().length() == 0
                        && etext3.getText().toString().length() == 0
                        && etext4.getText().toString().length() == 0
                        && etext5.getText().toString().length() == 0
                        && etext6.getText().toString().length() == 0
                        && etext7.getText().toString().length() == 0
                        && etext8.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill up any one data of the list", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                        saveData(etext2.getText().toString(), etext3.getText().toString(), etext4.getText().toString(), etext5.getText().toString(), etext6.getText().toString(), etext7.getText().toString(), etext8.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    }
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

        etext2 = (EditText) findViewById(R.id.etext2);
        etext3 = (EditText) findViewById(R.id.etext3);
        etext4 = (EditText) findViewById(R.id.etext4);
        etext5 = (EditText) findViewById(R.id.etext5);
        etext6 = (EditText) findViewById(R.id.etext6);
        etext7 = (EditText) findViewById(R.id.etext7);
        etext8 = (EditText) findViewById(R.id.etext8);

        image_edit = (ImageView) findViewById(R.id.image_edit);
        user_image = (ImageView) findViewById(R.id.user_image);

        update_button = (Button) findViewById(R.id.update_button);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void getData() {
        String url = "https://learnersmall.in/android/api/v2/student-details";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(EditProfileActivity.this,EditProfileActivity.this);
        wc.sendRequest(url, params,0);
    }

    private void saveData(String email, String mobile, String state, String city, String centre, String pincode, String address) {
        String url = "https://learnersmall.in/android/api/v2/edit-profile";
        RequestParams params = new RequestParams();
        params.put("student", student_id);
        params.put("StudentEmail", email);
        params.put("StudentMobile", mobile);
        params.put("StudentState", state);
        params.put("StudentCity", city);
        params.put("StudentCentre", centre);
        params.put("StudentPincode", pincode);
        params.put("StudentAddress", address);

        WebServiceController wc = new WebServiceController(EditProfileActivity.this,EditProfileActivity.this);
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

//                    }
//                }
                JSONObject jsonObject1 = new JSONObject(response);
                JSONObject jsonObject = jsonObject1.getJSONObject("students");

                profilename.setText(jsonObject.getString("name").trim());
                text1.setText(jsonObject.getString("code").trim());

                if (!jsonObject.getString("email").isEmpty()
                        && !jsonObject.getString("email").equals("null")) {
                    etext2.setText(jsonObject.getString("email").trim());
                    etext2.setSelection(etext2.getText().length());
                }
                etext2.requestFocus();

                if (!jsonObject.getString("mobile").isEmpty()
                        && !jsonObject.getString("mobile").equals("null")) {
                    etext3.setText(jsonObject.getString("mobile").trim());
                }

                if (!jsonObject.getString("state").isEmpty()
                        && !jsonObject.getString("state").equals("null")) {
                    etext4.setText(jsonObject.getString("state").trim());
                }

                if (!jsonObject.getString("city").isEmpty()
                        && !jsonObject.getString("city").equals("null")) {
                    etext5.setText(jsonObject.getString("city").trim());
                }

                if (!jsonObject.getString("centre").isEmpty()
                        && !jsonObject.getString("centre").equals("null")) {
                    etext6.setText(jsonObject.getString("centre").trim());
                }

                if (!jsonObject.getString("pincode").isEmpty()
                        && !jsonObject.getString("pincode").equals("null")) {
                    etext7.setText(jsonObject.getString("pincode").trim());
                }

                if (!jsonObject.getString("address").isEmpty()
                        && !jsonObject.getString("address").equals("null")) {
                    etext8.setText(jsonObject.getString("address").trim());
                }

                if (!jsonObject.getString("profile_image").isEmpty()
                        && !jsonObject.getString("profile_image").equalsIgnoreCase("null")) {
                    String uimg = jsonObject.getString("profile_image");
                    Picasso.with(this).invalidate(uimg);
                    Picasso.with(this).load(uimg).into(user_image);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").trim().equalsIgnoreCase("true")) {
                    builder = new AlertDialog.Builder(EditProfileActivity.this);
                    builder.setTitle("Edit Successfully!!!");
                    builder.setMessage("Your profile update successfully.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.show();
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
