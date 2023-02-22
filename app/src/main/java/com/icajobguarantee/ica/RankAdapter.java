package com.icajobguarantee.ica;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RankAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public String printedName;

    public RankAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.rank_list_view, null);

        TextView rank_id = (TextView) vi.findViewById(R.id.rank_id);
        TextView exam_for = (TextView) vi.findViewById(R.id.exam_for);
        TextView rank_name = (TextView) vi.findViewById(R.id.rank_name);
//        TextView rank_status = (TextView) vi.findViewById(R.id.rank_status);
        TextView exam_name = (TextView) vi.findViewById(R.id.exam_name);
        TextView obtain_marks = (TextView) vi.findViewById(R.id.obtain_marks);
        TextView time_taken = (TextView) vi.findViewById(R.id.time_taken);
        TextView date_time = (TextView) vi.findViewById(R.id.date_time);
        ImageView rank_image = (ImageView) vi.findViewById(R.id.rank_image);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        //Log.d("Id value", song.get(RankHistoryActivity.TAG_RANK_ID));

        //Setting all values in listview
        rank_id.setText(song.get(RankHistoryActivity.TAG_RANK_ID));
        exam_for.setText(song.get(RankHistoryActivity.TAG_EXAM_FOR));
        rank_name.setText("Rank : " + song.get(RankHistoryActivity.TAG_RANK));
//        rank_status.setText("Status : " + song.get(RankHistoryActivity.TAG_STATUS));
        exam_name.setText(song.get(RankHistoryActivity.TAG_EXAM_NAME));
        obtain_marks.setText("Marks(%) : " + song.get(RankHistoryActivity.TAG_OBTAIN_MARKS));
        time_taken.setText("Time taken : " + song.get(RankHistoryActivity.TAG_TIME_TAKEN));
        date_time.setText("Date : " + song.get(RankHistoryActivity.TAG_DATE_TIME));

        if (song.get(RankHistoryActivity.TAG_RANK).equalsIgnoreCase("1")) {
            rank_image.setImageResource(R.drawable.award01);
        } else if (song.get(RankHistoryActivity.TAG_RANK).equalsIgnoreCase("2")) {
            rank_image.setImageResource(R.drawable.award02);
        } else if (song.get(RankHistoryActivity.TAG_RANK).equalsIgnoreCase("3")) {
            rank_image.setImageResource(R.drawable.award03);
        } else {
            rank_image.setImageResource(R.drawable.award04);
        }

        return vi;
    }
}
