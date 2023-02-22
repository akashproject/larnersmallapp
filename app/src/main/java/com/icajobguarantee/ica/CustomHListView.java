package com.icajobguarantee.ica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomHListView extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> listViewId;
    private final ArrayList<String> listViewString;
    private final ArrayList<String> listViewImage;
    private final ArrayList<String> listViewCode;
    private final ArrayList<String> listViewTime;
    private final ArrayList<String> listViewDetails;
    private final ArrayList<String> listViewVideo;

    public CustomHListView(Context context, ArrayList<String> listViewId, ArrayList<String> listViewString, ArrayList<String> listViewImage, ArrayList<String> listViewCode, ArrayList<String> listViewTime, ArrayList<String> listViewDetails, ArrayList<String> listViewVideo) {
        mContext = context;
        this.listViewId = listViewId;
        this.listViewString = listViewString;
        this.listViewImage = listViewImage;
        this.listViewCode = listViewCode;
        this.listViewTime = listViewTime;
        this.listViewDetails = listViewDetails;
        this.listViewVideo = listViewVideo;
    }

    @Override
    public int getCount() {
        return listViewId.size();
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
        //return null;
        View listViewAndroid = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null) {
            listViewAndroid = new View(mContext);
            listViewAndroid = inflater.inflate(R.layout.custom_hlistview_list, null);
        }


        TextView textViewIdAndroid = (TextView) listViewAndroid.findViewById(R.id.android_list_id);
        ImageView imageViewAndroid = (ImageView) listViewAndroid.findViewById(R.id.android_list_image);
        TextView textViewAndroid = (TextView) listViewAndroid.findViewById(R.id.android_list_text);
        TextView codeViewAndroid = (TextView) listViewAndroid.findViewById(R.id.android_list_code);
        TextView timeViewAndroid = (TextView) listViewAndroid.findViewById(R.id.android_list_time);
        TextView detailsViewAndroid = (TextView) listViewAndroid.findViewById(R.id.android_list_details);

        //textViewAndroid.getInputExtras(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        textViewIdAndroid.setText(listViewId.get(position));
        textViewAndroid.setText(listViewString.get(position));

        codeViewAndroid.setVisibility(View.GONE);
//        if (!listViewCode.get(position).equalsIgnoreCase("")) {
//            codeViewAndroid.setVisibility(View.VISIBLE);
//            codeViewAndroid.setText(listViewCode.get(position));
//        } else {
//            codeViewAndroid.setVisibility(View.GONE);
//        }

        timeViewAndroid.setVisibility(View.GONE);
//        if (!listViewTime.get(position).equalsIgnoreCase("")) {
//            timeViewAndroid.setVisibility(View.VISIBLE);
//            timeViewAndroid.setText(listViewTime.get(position));
//        } else {
//            timeViewAndroid.setVisibility(View.GONE);
//        }

        detailsViewAndroid.setVisibility(View.GONE);
//        if (!listViewDetails.get(position).equalsIgnoreCase("")) {
//            detailsViewAndroid.setVisibility(View.VISIBLE);
//            detailsViewAndroid.setText(listViewDetails.get(position));
//        } else {
//            detailsViewAndroid.setVisibility(View.GONE);
//        }

        if (!listViewImage.get(position).equalsIgnoreCase("")) {
            imageViewAndroid.setVisibility(View.VISIBLE);
            Picasso.with(mContext).invalidate(listViewImage.get(position));
            Picasso.with(mContext).load(listViewImage.get(position)).into(imageViewAndroid);
        } else {
            imageViewAndroid.setVisibility(View.GONE);
        }

        return listViewAndroid;
    }
}
