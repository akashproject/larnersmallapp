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

public class AcademicAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public String printedName;

    public AcademicAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.academic_list, null);

        TextView courseid = (TextView) vi.findViewById(R.id.courseid);
        TextView coursename = (TextView) vi.findViewById(R.id.coursename);
        TextView admissiondate = (TextView) vi.findViewById(R.id.admissiondate);
        ImageView arrow_right = (ImageView) vi.findViewById(R.id.arrow_right);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        //Setting all values in listview
        courseid.setText(song.get(ProfileDetailsActivity.TAG_COURSE_ID));
        coursename.setText(song.get(ProfileDetailsActivity.TAG_COURSE_NAME));
        admissiondate.setText("Admission Date : " + song.get(ProfileDetailsActivity.TAG_ADMISSION_DATE));

        return vi;
    }
}
