package com.icajobguarantee.ica;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PrivacyActivity extends AppCompatActivity implements WebInterface  {
    TextView textView;
    public String qfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        init();
        getData();
    }

    protected void init() {
        textView = (TextView) findViewById(R.id.text_privacy);
    }

    private void getData(){
        qfor = "privacy";
        String url = "http://kreativemachinez.us/ica_edu/page.php";
        RequestParams params = new RequestParams();
        params.put("con", qfor);

        WebServiceController wc = new WebServiceController(PrivacyActivity.this,PrivacyActivity.this);
        wc.sendRequest(url, params,0);
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
                    HashMap<String, String> loginIndata = new HashMap<>();
                    if (jsonObject.getString("status").trim().equalsIgnoreCase("success")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            textView.setText(Html.fromHtml(jsonObject.getString("content").trim(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            textView.setText(Html.fromHtml(jsonObject.getString("content").trim()));
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
