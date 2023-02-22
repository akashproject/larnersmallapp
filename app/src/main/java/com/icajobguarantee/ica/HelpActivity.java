package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity implements WebInterface  {
    TextView textView;
    EditText edit1, edit2;
    Button send_button;
    public String qfor, student_id;
    LoginSessionManager loginSessionManager;
    ArrayList<String> listId, listType, listString, listDetails;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit1.getText().toString().length() == 0) {
                    edit1.requestFocus();
                    edit1.setError("Subject cannot be empty");
                    return;
                } else if (edit2.getText().toString().length() == 0) {
                    edit2.requestFocus();
                    edit2.setError("Message cannot be empty");
                    return;
                } else {
                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                        sendData(edit1.getText().toString(), edit2.getText().toString());
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

        listview = (ListView) findViewById(R.id.listview);
        //listview.setClickable(false);
        listId = new ArrayList<String>();
        listType = new ArrayList<String>();
        listString = new ArrayList<String>();
        listDetails = new ArrayList<String>();

        //textView = (TextView) findViewById(R.id.text_help);

        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

        send_button = (Button) findViewById(R.id.send_button);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void getData(){
        String url = "https://learnersmall.in/android/api/v2/get-help";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(HelpActivity.this,HelpActivity.this);
        wc.sendRequest(url, params,0);
    }

    private void sendData(String subject, String message) {
        String url = "https://learnersmall.in/android/api/v2/post-help";
        RequestParams params = new RequestParams();
        params.put("student", student_id);
        params.put("subject", subject);
        params.put("message", message);

        WebServiceController wc = new WebServiceController(HelpActivity.this,HelpActivity.this);
        wc.sendRequest(url, params,1);
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

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    if (jsonObject.getString("status").trim().equalsIgnoreCase("success")) {
                        listview.setVisibility(View.VISIBLE);

                        JSONArray jsonArray = jsonObject.getJSONArray("reply");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String l_id = jsonObject1.getString("id").trim();
                            String l_type = jsonObject1.getString("help_type").trim();
                            String l_name = jsonObject1.getString("subject").trim();
                            String l_details = jsonObject1.getString("message").trim();

                            listId.add(l_id);
                            listType.add(l_type);
                            listString.add(l_name);
                            listDetails.add(l_details);
                        }

                        ReplyList replyList = new ReplyList(getApplicationContext(), listId, listType, listString, listDetails);
                        listview.setAdapter(replyList);
                        setListViewHeightBasedOnChildren(listview);

                    } else {
                        listview.setVisibility(View.GONE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    edit1.setText("");
                    edit1.setHint("Subject");
                    edit2.setText("");
                    edit2.setHint("Message");

                    Toast.makeText(HelpActivity.this, jsonObject.getString("error").trim(), Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(int flag) {

    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight() + 14;

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }
}
