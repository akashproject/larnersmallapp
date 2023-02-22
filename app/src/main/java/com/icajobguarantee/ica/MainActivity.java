package com.icajobguarantee.ica;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements WebInterface {
    EditText student_code;
    TextView text4;
    Button loginButton;
    public String qfor;
    LoginSessionManager loginSessionManager;
    Dialog pDialog;
    String androidId, android_id, device_token;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        loginSessionManager = new LoginSessionManager(getApplicationContext());
//        if (loginSessionManager.isLoginSession()) {
//            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//            finish();
//        }

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        android_id = md5(androidId);

//        Log.d("main_device_id", androidId);
        Log.d("device_id", android_id);

        student_code = (EditText) findViewById(R.id.student_code);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        device_token = task.getResult();
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, device_token);
                        Log.d(TAG, msg);
                        //Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        text4 = (TextView) findViewById(R.id.text4);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Go to Registration", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
                //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

        loginButton = (Button) findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student_code.getText().toString().length() == 0) {
                    student_code.requestFocus();
                    student_code.setError("Code cannot be empty");
                    return;
                } else {
                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                        getData(student_code.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void getData(String student_code){
        //Log.d("Go for ", "Get Data " + student_code);
        //qfor = "logIn";
        String url = "https://learnersmall.in/android/api/v2/login-device";
        RequestParams params = new RequestParams();
        params.put("code", student_code);
        params.put("device", android_id);
        params.put("device_token", device_token);

        Log.e("params_value", String.valueOf(params));

        WebServiceController wc = new WebServiceController(MainActivity.this,MainActivity.this);
        wc.sendRequest(url, params,0);
    }

//    public static boolean isEmailValid(String email){
//        Pattern pattern = Patterns.EMAIL_ADDRESS;
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    if (jsonObject.getString("status").trim().equalsIgnoreCase("true")) {
                        //loginIndata.put("student_id", jsonObject.getString("student_id").trim());
                        //loginSessionManager.createLoginSession(loginIndata);
                        //Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                        // Closing all the Activities
                        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //startActivity(i);
                        //finish();
                        Intent i = new Intent(getApplicationContext(), OTPVerificationActivity.class);
                        i.putExtra("code",jsonObject.getString("code").trim());
                        i.putExtra("mobile",jsonObject.getString("mobile").trim());
                        startActivity(i);
                        finish();
                    } else if (jsonObject.getString("status").trim().equalsIgnoreCase("false")) {
                        //Toast.makeText(getApplicationContext(), jsonObject.getString("error").trim(), Toast.LENGTH_LONG).show();
                        student_code.requestFocus();
                        student_code.setError(jsonObject.getString("error").trim());
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

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
