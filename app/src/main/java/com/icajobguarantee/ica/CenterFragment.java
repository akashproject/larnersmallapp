package com.icajobguarantee.ica;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CenterFragment extends Fragment implements WebInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<String> listViewId, listViewImage, listViewString, listViewCode, listViewTime, listViewDetails, listViewVideo;
    public String qfor, student_id;
    LoginSessionManager loginSessionManager;
    ListView listView;
    //EditText search_key;
    CustomListView centerAdapterView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CenterFragment() {
        // Required empty public constructor
    }

    public static CenterFragment newInstance(String param1, String param2) {
        CenterFragment fragment = new CenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void init(View v) {
        loginSessionManager = new LoginSessionManager(getActivity());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);

        listView = (ListView) v.findViewById(R.id.listView);
        listViewId = new ArrayList<String>();
        listViewImage = new ArrayList<String>();
        listViewString = new ArrayList<String>();
        listViewCode = new ArrayList<String>();
        listViewTime = new ArrayList<String>();
        listViewDetails = new ArrayList<String>();
        listViewVideo = new ArrayList<String>();

        //search_key = (EditText) v.findViewById(R.id.search_key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_center, container, false);

        init(v);

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getData();
        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
        }

//        search_key.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                centerAdapterView.getFilter().filter(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        return v;
    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/center";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(getActivity(),this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                listViewId.clear();
                listViewString.clear();
                listViewCode.clear();
                listViewTime.clear();
                listViewImage.clear();
                listViewDetails.clear();
                listViewVideo.clear();


                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String l_id = "";
                        String l_code = "";
                        String l_name = "";
                        String l_image = "";
                        String l_details = "";
                        String l_created = "";
                        String l_video = "";

                        l_id = jsonObject.getString("id").trim();
                        l_name = jsonObject.getString("Center_name").trim();
                        l_code = jsonObject.getString("Center_code").trim();
                        l_created = jsonObject.getString("Center_pin").trim();
                        l_details = jsonObject.getString("Center_address").trim();

                        String city_name = jsonObject.getString("cityName").trim();
                        String state_name = jsonObject.getString("stateName").trim();

                        if (jsonObject.getString("Active_flag").equalsIgnoreCase("Y")) {
                            listViewId.add(l_id);
                            listViewString.add(l_name + ", " + city_name + ", " + state_name);
                            listViewCode.add("Code : " + l_code);
                            listViewTime.add("Pin Code : " + l_created);
                            listViewImage.add(l_image);
                            listViewDetails.add("Address : " + l_details);
                            listViewVideo.add(l_video);
                        }
                    }

                    centerAdapterView = new CustomListView(getActivity(), listViewId, listViewString, listViewImage, listViewCode, listViewTime, listViewDetails, listViewVideo);
                    listView.setAdapter(centerAdapterView);
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
