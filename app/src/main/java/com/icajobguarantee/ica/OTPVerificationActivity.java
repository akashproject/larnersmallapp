package com.icajobguarantee.ica;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
//import android.support.design.widget.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity implements WebInterface {
    EditText otp;
    TextView text4;
    Button loginButton;
    public String qfor;
    LoginSessionManager loginSessionManager;
    CourseSelectSesManager courseSelectSesManager;
    Dialog pDialog;
    String code, mobile;

    //These are the objects needed
    //It is the verification id that will be sent to the user
    //private String mVerificationId;
    ////firebase auth object
    //private FirebaseAuth mAuth;
    //PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        init();
    }

    private void init(){
        //initializing objects
        //mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        code = i.getStringExtra("code");
        mobile = i.getStringExtra("mobile");

        loginSessionManager = new LoginSessionManager(getApplicationContext());
        courseSelectSesManager = new CourseSelectSesManager(getApplicationContext());

        otp = (EditText) findViewById(R.id.otp);

        //StartFirebaseLogin();
        //sendVerificationCode(mobile);

        text4 = (TextView) findViewById(R.id.text4);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                    resendData(code);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                }
            }
        });

        loginButton = (Button) findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().length() == 0) {
                    otp.requestFocus();
                    otp.setError("OTP cannot be empty");
                    return;
//                } else if (!otp.getText().toString().equalsIgnoreCase("otp_string")) {
//                    otp.requestFocus();
//                    otp.setError("OTP is invalid");
//                    return;
                } else {
                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                        //verifyVerificationCode(otp.getText().toString().trim());
                        getData(code, otp.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void resendData(String code){
        //Log.d("task_start","start");
        //qfor = "resend";
        String url = "https://learnersmall.in/android/api/v2/login";
        RequestParams params = new RequestParams();
        params.put("code", code);
        params.put("resend", "resend");

        WebServiceController wc = new WebServiceController(OTPVerificationActivity.this,OTPVerificationActivity.this);
        wc.sendRequest(url, params,1);
    }

    private void getData(String code, String otp){
        //Log.d("task_start","start");
        //qfor = "submit_otp";
        String url = "https://www.learnersmall.in/android/api/v2/otp/verification";
        RequestParams params = new RequestParams();
        params.put("code", code);
        params.put("otp", otp);

        WebServiceController wc = new WebServiceController(OTPVerificationActivity.this,OTPVerificationActivity.this);
        wc.sendRequest(url, params,0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    HashMap<String, String> loginIndata = new HashMap<>();
                    if (jsonObject.getString("status").trim().equalsIgnoreCase("true")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String student_id = jsonObject1.getString("id");
                        String admin_id = jsonObject1.getString("created_by");
                        //String student_email = jsonObject1.getString("email");
                        loginIndata.put("student_id", student_id);
                        loginIndata.put("admin_id", admin_id);

                        loginSessionManager.createLoginSession(loginIndata);

                        //Course avail
                        Intent i = new Intent(getApplicationContext(), CourseTaggingActivity.class);
                        // Closing all the Activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();

                        /*if (jsonObject.getString("course").trim().equalsIgnoreCase("0")) {
                            //No course avail
                            Intent i = new Intent(getApplicationContext(), CourseSelectActivity.class);
                            // Closing all the Activities
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        } else {*/
                            //courseSelectSesManager.createCrSelectSession();
//                            //Course avail
//                            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
//                            // Closing all the Activities
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            // Add new Flag to start new Activity
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(i);
//                            finish();
                        /* } */
                    } else if (jsonObject.getString("status").trim().equalsIgnoreCase("false")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("error").trim(), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    if (jsonObject.getString("status").trim().equalsIgnoreCase("true")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("error").trim(), Toast.LENGTH_LONG).show();
                    } else if (jsonObject.getString("status").trim().equalsIgnoreCase("false")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("error").trim(), Toast.LENGTH_LONG).show();
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

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
//    private void sendVerificationCode(String mobile) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+91" + mobile,
//                60,
//                TimeUnit.SECONDS,
//                this,
//                mCallbacks);
//    }
//
//    private void StartFirebaseLogin() {
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                Toast.makeText(OTPVerificationActivity.this, "verification completed", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Toast.makeText(OTPVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                mVerificationId = s;
//            }
//        };
//    }
//
//    private void verifyVerificationCode(String code) {
//        //creating the credential
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
//
//        //signing the user
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//            .addOnCompleteListener(OTPVerificationActivity.this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        //verification successful we will start the profile activity
//                        Intent intent = new Intent(OTPVerificationActivity.this, DashboardActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//
//                    } else {
//
//                        //verification unsuccessful.. display an error message
//
//                        String message = "Somthing is wrong, we will fix it soon...";
//
//                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                            message = "Invalid code entered...";
//                        }
//
//                        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
//                        snackbar.setAction("Dismiss", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        });
//                        snackbar.show();
//                    }
//                }
//            });
//    }
}
