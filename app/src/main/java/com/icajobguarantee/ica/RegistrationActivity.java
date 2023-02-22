package com.icajobguarantee.ica;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements WebInterface {
    EditText name, email, mobileno, address, city, pincode;
    //Spinner state, city;
    Spinner state;
    TextView text4;
    Button button;
    public String qfor;
    Dialog pDialog;
    String state_id, state_str, city_id, city_str;
    HashMap<Integer,String> stateMap, cityMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }

    private void init(){
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobileno = (EditText) findViewById(R.id.mobileno);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        state = (Spinner) findViewById(R.id.state);
        //city = (Spinner) findViewById(R.id.city);
        city = (EditText) findViewById(R.id.city);

        text4 = (TextView) findViewById(R.id.text4);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getState();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() == 0) {
                    name.requestFocus();
                    name.setError("Name cannot be empty");
                    return;
                } else if (email.getText().toString().length() == 0) {
                    email.requestFocus();
                    email.setError("Email cannot be empty");
                    return;
                } else if (email.getText().toString().length() > 0 && !isEmailValid(email.getText().toString())) {
                    email.requestFocus();
                    email.setError("Enter a valid email");
                    return;
                } else if (mobileno.getText().toString().length() == 0) {
                    mobileno.requestFocus();
                    mobileno.setError("Mobile no cannot be empty");
                    return;
                } else if (mobileno.getText().toString().length() > 0 && mobileno.getText().toString().length() < 10) {
                    mobileno.requestFocus();
                    mobileno.setError("Enter a valid mobile no.");
                    return;
                } else if (address.getText().toString().length() == 0) {
                    address.requestFocus();
                    address.setError("Address cannot be empty");
                    return;
                } else if (state_id.equalsIgnoreCase("0")) {
                    state.requestFocus();
                    Toast.makeText(getApplicationContext(), "Please select state", Toast.LENGTH_LONG).show();
                } else if (city.getText().toString().length() == 0) {
                    city.requestFocus();
                    Toast.makeText(getApplicationContext(), "PCity cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (pincode.getText().toString().length() == 0) {
                    pincode.requestFocus();
                    pincode.setError("Pincode cannot be empty");
                    return;
                } else {
                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
//                        getData(name.getText().toString(), email.getText().toString(), mobileno.getText().toString(),
//                                address.getText().toString(), pincode.getText().toString(), state_str, city_str);
                        getData(name.getText().toString(), email.getText().toString(), mobileno.getText().toString(),
                                address.getText().toString(), pincode.getText().toString(), state_str, city.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static boolean isEmailValid(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void getState(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "getState";
        String url = "https://learnersmall.in/android/api/v2/state";
        RequestParams params = new RequestParams();
        params.put("name", "state");

        WebServiceController wc = new WebServiceController(RegistrationActivity.this,RegistrationActivity.this);
        wc.sendRequest(url, params,0);
    }

//    private void getCity(String state_id) {
//        //qfor = "getCity";
//        String url = "https://learnersmall.in/android/api/v2/city";
//        RequestParams params = new RequestParams();
//        params.put("state", state_id);
//
//        WebServiceController wc = new WebServiceController(RegistrationActivity.this,RegistrationActivity.this);
//        wc.sendRequest(url, params,1);
//    }

    private void getData(String name, String email, String mobileno, String address, String pincode, String state, String city){
        Log.d("Go for ", "Get Data " + name);
        //qfor = "registration";
        String url = "https://learnersmall.in/android/api/v2/student";
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("email", email);
        params.put("mobile", mobileno);
        params.put("state", state_str);
        params.put("city", city_str);
        params.put("centre", "asd");
        params.put("address", address);
        params.put("pincode", pincode);
        params.put("status", "1");

        WebServiceController wc = new WebServiceController(RegistrationActivity.this,RegistrationActivity.this);
        wc.sendRequest(url, params,2);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                String[] spinnerArray = new String[jsonArray.length() + 1];
                stateMap = new HashMap<Integer,String>();

                stateMap.put(0, "0");
                spinnerArray[0] = "Select state";
                int j = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id").trim();
                    String name_long = jsonObject1.getString("name_long").trim();
                    j = i + 1;
                    //Log.d("j value", String.valueOf(j));
                    stateMap.put(j, id);
                    spinnerArray[j] = name_long;
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, spinnerArray);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                state.setAdapter(adapter);

                state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v,
                                               int position, long id) {
                        // On selecting a spinner item
                        state_str = state.getSelectedItem().toString();
                        state_id = stateMap.get(state.getSelectedItemPosition());

//                        if (state_id.equalsIgnoreCase("0")) {
//                            String[] cityspinnerArray = new String[1];
//                            cityMap = new HashMap<Integer,String>();
//
//                            cityMap.put(0, "0");
//                            cityspinnerArray[0] = "Select city";
//
//                            ArrayAdapter<String> cityadapter =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, cityspinnerArray);
//                            cityadapter.setDropDownViewResource(R.layout.spinner_item);
//                            city.setAdapter(cityadapter);
//                        } else {
//                            ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
//                            if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
//                                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
//                                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
//                                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
//                                getCity(state_id);
//                            } else {
//                                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
//                            }
//                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }/* else if (flag == 1) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                String[] spinnerArray = new String[jsonArray.length() + 1];
                cityMap = new HashMap<Integer,String>();

                cityMap.put(0, "0");
                spinnerArray[0] = "Select city";
                int j = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id").trim();
                    String name = jsonObject1.getString("name").trim();
                    j = i + 1;
                    cityMap.put(j, id);
                    spinnerArray[j] = name;
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, spinnerArray);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                city.setAdapter(adapter);

                city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v,
                                               int position, long id) {
                        // On selecting a spinner item
                        city_str = city.getSelectedItem().toString();
                        city_id = cityMap.get(city.getSelectedItemPosition());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/ else if (flag == 2) {
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
                        Toast.makeText(getApplicationContext(), "Registration successful. Please check your given email to find your code. Thank you.", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (jsonObject.getString("status").trim().equalsIgnoreCase("false")) {
                        //Toast.makeText(getApplicationContext(), jsonObject.getString("error").trim(), Toast.LENGTH_LONG).show();
                        if (jsonObject.getString("efor").trim().equalsIgnoreCase("email")) {
                            email.requestFocus();
                            email.setError(jsonObject.getString("error").trim());
                        } else if (jsonObject.getString("efor").trim().equalsIgnoreCase("mobile")) {
                            mobileno.requestFocus();
                            mobileno.setError(jsonObject.getString("error").trim());
                        }
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
}
