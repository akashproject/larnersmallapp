package com.icajobguarantee.ica;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AcademicDetReportAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    public String module_st_str;

    public AcademicDetReportAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        mContext = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.academic_detreport_list, null);

        TextView coursename = (TextView) vi.findViewById(R.id.coursename);
        TextView modulestatus = (TextView) vi.findViewById(R.id.modulestatus);
        TextView coursemarksper = (TextView) vi.findViewById(R.id.coursemarksper);
        TextView courseattendance = (TextView) vi.findViewById(R.id.courseattendance);
        TextView courcepass = (TextView) vi.findViewById(R.id.courcepass);
        TextView batchst = (TextView) vi.findViewById(R.id.batchst);
        TextView batchen = (TextView) vi.findViewById(R.id.batchen);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        //Setting all values in listview
        coursename.setText(song.get(AcademicReportActivity.TAG_SUB_NAME));

        if (song.get(AcademicReportActivity.TAG_VIEW_FOR).equalsIgnoreCase("Marks")) {
            courseattendance.setVisibility(View.GONE);
            batchst.setVisibility(View.GONE);
            batchen.setVisibility(View.GONE);

            if (song.get(AcademicReportActivity.TAG_MODULE_STATUS).equalsIgnoreCase("P")) {
                module_st_str = "Module Pending";
            } else if (song.get(AcademicReportActivity.TAG_MODULE_STATUS).equalsIgnoreCase("R")) {
                module_st_str = "Module Running";
            } else if (song.get(AcademicReportActivity.TAG_MODULE_STATUS).equalsIgnoreCase("E")) {
                module_st_str = "Exam Pending";
            } else if (song.get(AcademicReportActivity.TAG_MODULE_STATUS).equalsIgnoreCase("F")) {
                module_st_str = "Exam Failed";
            } else if (song.get(AcademicReportActivity.TAG_MODULE_STATUS).equalsIgnoreCase("C")) {
                module_st_str = "Exam Passed";
            }
            modulestatus.setText("Module Status : " + module_st_str);
            if (!song.get(AcademicReportActivity.TAG_SUB_MARKS).equalsIgnoreCase("0")) {
                coursemarksper.setVisibility(View.VISIBLE);
                coursemarksper.setText("Mark(%) : " + song.get(AcademicReportActivity.TAG_SUB_MARKS));
            } else {
                coursemarksper.setVisibility(View.GONE);
            }
            if (!song.get(AcademicReportActivity.TAG_SUB_PASS).equalsIgnoreCase("")) {
                courcepass.setVisibility(View.GONE);
                courcepass.setText(song.get(AcademicReportActivity.TAG_SUB_PASS));
            } else {
                courcepass.setVisibility(View.GONE);
            }
        } else if (song.get(AcademicReportActivity.TAG_VIEW_FOR).equalsIgnoreCase("Attendance")) {
            modulestatus.setVisibility(View.GONE);
            coursemarksper.setVisibility(View.GONE);
            courcepass.setVisibility(View.GONE);

            if (!song.get(AcademicReportActivity.TAG_BATCH_STDATE).equals("null")) {
                String[] stdatetime_arr = song.get(AcademicReportActivity.TAG_BATCH_STDATE).split("T");
                String[] stdate_arr = stdatetime_arr[0].split("-");
                batchst.setText("Start Date : " + stdate_arr[2] + "/" + stdate_arr[1] + "/" +stdate_arr[0]);
            } else {
                batchst.setText("Start Date : ");
            }
            if (!song.get(AcademicReportActivity.TAG_BATCH_ENDATE).equals("null")) {
                String[] etdatetime_arr = song.get(AcademicReportActivity.TAG_BATCH_ENDATE).split("T");
                String[] etdate_arr = etdatetime_arr[0].split("-");
                batchen.setText("End Date : " + etdate_arr[2] + "/" + etdate_arr[1] + "/" +etdate_arr[0]);
            } else {
                batchen.setText("Start Date : ");
            }

            if (!song.get(AcademicReportActivity.TAG_SUB_ATTEND).equals("null")) {
                courseattendance.setVisibility(View.VISIBLE);
                courseattendance.setText("Attendance(%) : " + song.get(AcademicReportActivity.TAG_SUB_ATTEND));
            } else {
                courseattendance.setVisibility(View.GONE);
            }
        }


        return vi;
    }
}