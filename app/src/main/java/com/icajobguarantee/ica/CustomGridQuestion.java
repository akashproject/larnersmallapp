package com.icajobguarantee.ica;

import android.content.Context;
//import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGridQuestion extends BaseAdapter {
    private Context mContext;
    private final ArrayList<Integer> ques_set;
    private final String[] ans_st_arr;

    public CustomGridQuestion(Context context, ArrayList<Integer> ques_set, String[] ans_st_arr) {
        mContext = context;
        this.ques_set = ques_set;
        this.ans_st_arr = ans_st_arr;
    }

    @Override
    public int getCount() {
        return ques_set.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return null;
        View mView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = new View(mContext);
            mView = inflater.inflate(R.layout.question_selection_grid, parent, false);
        } else {
            mView = (View) convertView;
        }

        TextView ques_no_btn = (TextView) mView.findViewById(R.id.ques_no_btn);
        LinearLayout ques_no_layout = (LinearLayout) mView.findViewById(R.id.ques_no_layout);

        ques_no_btn.setText(String.valueOf(ques_set.get(position)));
        if (ans_st_arr[position].equalsIgnoreCase("1")) {
            ques_no_layout.setBackgroundResource(R.drawable.circle_background_done);
            ques_no_btn.setTextColor(ContextCompat.getColor(mContext, R.color.White));
        } else if (ans_st_arr[position].equalsIgnoreCase("2")) {
            ques_no_layout.setBackgroundResource(R.drawable.circle_background_gray);
            ques_no_btn.setTextColor(ContextCompat.getColor(mContext, R.color.White));
        } else if (ans_st_arr[position].equalsIgnoreCase("0")) {
            ques_no_layout.setBackgroundResource(R.drawable.circle_background);
            ques_no_btn.setTextColor(ContextCompat.getColor(mContext, R.color.Textcolor));
        }

        return mView;
    }
}
