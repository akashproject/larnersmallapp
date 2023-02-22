package com.icajobguarantee.ica;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
//import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamFragment extends Fragment implements WebInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<String> gridViewId, gridViewString, gridViewImage, gridViewSelected;
    public String qfor, student_id;
    LoginSessionManager loginSessionManager;
    GridView androidGridView;
    Dialog pDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExamFragment() {
        // Required empty public constructor
    }

    public static ExamFragment newInstance(String param1, String param2) {
        ExamFragment fragment = new ExamFragment();
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

        androidGridView = (GridView) v.findViewById(R.id.gridView);
        gridViewId = new ArrayList<String>();
        gridViewString = new ArrayList<String>();
        gridViewImage = new ArrayList<String>();
        gridViewSelected = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exam, container, false);

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

        return v;
    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/course";
        RequestParams params = new RequestParams();
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(getActivity(),this);
        wc.sendRequest(url, params,0);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String course_id = jsonObject.getString("id").trim();
                        String course_name = jsonObject.getString("course_name").trim();
                        String course_photo = jsonObject.getString("course_photo").trim();
                        String course_selected = jsonObject.getString("status").trim();

                        if (course_selected.equalsIgnoreCase("1")) {
                            gridViewId.add(course_id);
                            gridViewString.add(course_name);
                            gridViewImage.add(course_photo);
                            gridViewSelected.add(course_selected);
                        }
                    }

                    CustomGridViewFragment adapterViewAndroid = new CustomGridViewFragment(getActivity(), gridViewId, gridViewString, gridViewImage, gridViewSelected);
                    androidGridView=(GridView) getView().findViewById(R.id.gridView);
                    androidGridView.setAdapter(adapterViewAndroid);
                    androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int i, long id) {
                            //Toast.makeText(getActivity(), "GridView Item: " + gridViewId.get(+i), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(), ExamSubjectActivity.class);
                            intent.putExtra("list_id", gridViewId.get(+i));
                            intent.putExtra("list_title", gridViewString.get(+i));
                            startActivity(intent);
                        }
                    });
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
