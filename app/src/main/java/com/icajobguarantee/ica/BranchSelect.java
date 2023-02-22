package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BranchSelect extends AppCompatActivity implements WebInterface  {

    ListView branches;
    public String qfor;
    LoginSessionManager loginSessionManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    static ArrayList<HashMap<String, String>> dataList;
    BranchlistAdapter adapter;
    public static final String TAG_BRANCH_ID = "branchId";
    public static final String TAG_BRANCH_NAME = "branchName";
    public static final String TAG_CONTACT_NO = "contactNo";
    public static final String TAG_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_select);

        init();
    }

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        if (loginSessionManager.isLoginSession()) {
            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(i);
            finish();
        }
        branches = (ListView) findViewById(R.id.branches);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        dataList = new ArrayList<HashMap<String,String>>();

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

        mSwipeRefreshLayout.setColorSchemeResources(R.color.Gold);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shuffle();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData(){
        //qfor = "branchSelect";
        String url = "http://kreativemachinez.us/ica_edu/branch_list.php";
        WebServiceController wc = new WebServiceController(BranchSelect.this,BranchSelect.this);
        wc.getRequest(url,0);

    }

    public void shuffle(){
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

    /**
     * react to the user tapping the back/up icon in the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                        JSONArray jsonArray = jsonObject.getJSONArray("branch");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> catdata = new HashMap<>();
                            catdata.put(TAG_BRANCH_ID, jsonObject1.getString("branch_id").trim());
                            catdata.put(TAG_BRANCH_NAME, jsonObject1.getString("branch_name").trim());
                            catdata.put(TAG_CONTACT_NO, jsonObject1.getString("contact_no").trim());
                            catdata.put(TAG_ADDRESS, jsonObject1.getString("address").trim());
                            dataList.add(catdata);
                        }
                        adapter = new BranchlistAdapter(BranchSelect.this, dataList);
                        branches.setAdapter(adapter);

                        branches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final String branch_id = ((TextView) view.findViewById(R.id.branch_id)).getText().toString();
                                final String branch_name = ((TextView) view.findViewById(R.id.branch_name)).getText().toString();
                                final String contact_no = ((TextView) view.findViewById(R.id.contact_no)).getText().toString();
                                final String address = ((TextView) view.findViewById(R.id.address)).getText().toString();

                                //Toast.makeText(getApplicationContext(), branch_id + " " + branch_name + " " + contact_no + " " + address, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), EmailVerification.class);
                                startActivity(i);
                            }
                        });
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
