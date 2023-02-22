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

public class AcademicReportAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public String printedName;

    public AcademicReportAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        mContext = a;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.academic_report_list, null);

        TextView coursename = (TextView) vi.findViewById(R.id.coursename);
        ImageView circle_red = (ImageView) vi.findViewById(R.id.circle_red);
        ImageView circle_green = (ImageView) vi.findViewById(R.id.circle_green);
        ImageView circle_yellow = (ImageView) vi.findViewById(R.id.circle_yellow);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        //Setting all values in listview
        coursename.setText(song.get(AcademicReportActivity.TAG_SUBJECT_NAME));
        if (song.get(AcademicReportActivity.TAG_SUBJECT_FLAG).equalsIgnoreCase("R")) {
            circle_red.setVisibility(View.VISIBLE);
            circle_green.setVisibility(View.GONE);
            circle_yellow.setVisibility(View.GONE);
        } else if (song.get(AcademicReportActivity.TAG_SUBJECT_FLAG).equalsIgnoreCase("G")) {
            circle_red.setVisibility(View.GONE);
            circle_green.setVisibility(View.VISIBLE);
            circle_yellow.setVisibility(View.GONE);
        } else if (song.get(AcademicReportActivity.TAG_SUBJECT_FLAG).equalsIgnoreCase("Y")) {
            circle_red.setVisibility(View.GONE);
            circle_green.setVisibility(View.GONE);
            circle_yellow.setVisibility(View.VISIBLE);
        }

        return vi;
    }
}
