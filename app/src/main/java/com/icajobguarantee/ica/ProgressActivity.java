package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgressActivity extends AppCompatActivity implements WebInterface {

    BarChart barChart, barChart1, barChart2;
    BarDataSet set1, set2, set3;
    List<GradientColor> gradientColors;
    TextView header_title;
    ImageView back_press;
    LoginSessionManager loginSessionManager;
    String student_id;
    ArrayList<BarEntry> exam_values, assment_values, compe_values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        init();

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

        header_title = (TextView) findViewById(R.id.header_title);
        back_press = (ImageView) findViewById(R.id.back_press);

        header_title.setText("Your Progress");

        barChart = (BarChart) findViewById(R.id.exambargraph);
        barChart1 = (BarChart) findViewById(R.id.exambargraph1);
        barChart2 = (BarChart) findViewById(R.id.exambargraph2);


        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }
    }

    public void barColor() {
        int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
        int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
        int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
        int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
        int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
        int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
        int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
        int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
        int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
        int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);

        gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(startColor1, endColor1));
        gradientColors.add(new GradientColor(startColor2, endColor2));
        gradientColors.add(new GradientColor(startColor3, endColor3));
        gradientColors.add(new GradientColor(startColor4, endColor4));
        gradientColors.add(new GradientColor(startColor5, endColor5));
    }

    private void examBarChart() {
        set1 = new BarDataSet(exam_values, "Exam Progress");

        set1.setDrawIcons(false);

        barColor();
        set1.setGradientColors(gradientColors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData barData = new BarData(dataSets);
        barChart.setData(barData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
    }

    private void assesmentBarChart() {
        set2 = new BarDataSet(assment_values, "CIAE Course, The year 2019");

        set2.setDrawIcons(false);

        barColor();
        set2.setGradientColors(gradientColors);

        ArrayList<IBarDataSet> dataSets1 = new ArrayList<>();
        dataSets1.add(set2);

        BarData barData1 = new BarData(dataSets1);
        barChart1.setData(barData1);

        barChart1.setTouchEnabled(true);
        barChart1.setDragEnabled(true);
        barChart1.setScaleEnabled(true);
    }

    private void competitionBarChart() {
        set3 = new BarDataSet(compe_values, "Competition Progress");

        set3.setDrawIcons(false);

        barColor();
        set3.setGradientColors(gradientColors);

        ArrayList<IBarDataSet> dataSets2 = new ArrayList<>();
        dataSets2.add(set3);

        BarData barData2 = new BarData(dataSets2);
        barChart2.setData(barData2);

        barChart2.setTouchEnabled(true);
        barChart2.setDragEnabled(true);
        barChart2.setScaleEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData(){
        String url = "https://learnersmall.in/android/api/v2/progress";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(ProgressActivity.this,ProgressActivity.this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONArray jsonQArray = jsonObject.getJSONArray("exam_progress");
                if (jsonQArray.length() > 0) {
                    exam_values = new ArrayList<>();
                    for (int i = 0, j = 1; i < jsonQArray.length(); i++, j++) {

                        JSONObject jsonObject1 = jsonQArray.getJSONObject(i);
                        double percentge_marks = Double.parseDouble((jsonObject1.getString("percentge_marks")));
                        exam_values.add(new BarEntry(j, (float) percentge_marks));
                    }

                    barChart.invalidate();
                    barChart.refreshDrawableState();
                    examBarChart();
                }

                JSONArray jsonQArray1 = jsonObject.getJSONArray("assess_progress");
                if (jsonQArray1.length() > 0) {
                    assment_values = new ArrayList<>();
                    for (int i = 0, j = 1; i < jsonQArray1.length(); i++, j++) {

                        JSONObject jsonObject2 = jsonQArray1.getJSONObject(i);
                        double percentge_marks = Double.parseDouble((jsonObject2.getString("percentge_total")));
                        assment_values.add(new BarEntry(j, (float) percentge_marks));
                    }

                    barChart1.invalidate();
                    barChart1.refreshDrawableState();
                    assesmentBarChart();
                }

                JSONArray jsonQArray2 = jsonObject.getJSONArray("compe_progress");
                if (jsonQArray2.length() > 0) {
                    compe_values = new ArrayList<>();
                    for (int i = 0, j = 1; i < jsonQArray2.length(); i++, j++) {

                        JSONObject jsonObject3 = jsonQArray2.getJSONObject(i);
                        double percentge_marks = Double.parseDouble((jsonObject3.getString("percentge_marks")));
                        compe_values.add(new BarEntry(j, (float) percentge_marks));
                    }

                    barChart2.invalidate();
                    barChart2.refreshDrawableState();
                    competitionBarChart();
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
