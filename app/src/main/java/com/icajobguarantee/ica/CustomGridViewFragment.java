package com.icajobguarantee.ica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGridViewFragment extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> gridViewId;
    private final ArrayList<String> gridViewString;
    private final ArrayList<String> gridViewImageId;
    private final ArrayList<String> gridViewSelected;

    public CustomGridViewFragment(Context context, ArrayList<String> gridViewId, ArrayList<String> gridViewString, ArrayList<String> gridViewImageId, ArrayList<String> gridViewSelected) {
        mContext = context;
        this.gridViewId = gridViewId;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
        this.gridViewSelected = gridViewSelected;
    }

    @Override
    public int getCount() {
        return gridViewId.size();
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
        View gridViewAndroid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.fragment_ccourse_grid, parent, false);
        } else {
            gridViewAndroid = (View) convertView;
        }

        TextView textViewIdAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_id);
        TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
        ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
        //ImageView android_check = (ImageView) gridViewAndroid.findViewById(R.id.android_check);

        //textViewAndroid.getInputExtras(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        textViewIdAndroid.setText(gridViewId.get(position));
        textViewAndroid.setText(gridViewString.get(position));
//            if (gridViewSelected.get(position).equalsIgnoreCase("0")) {
//                android_check.setVisibility(View.GONE);
//            } else {
//                android_check.setVisibility(View.VISIBLE);
//            }
        //imageViewAndroid.setImageResource(gridViewImageId.get(position));
        //imageLoader.clearCache();
        //imageLoader.DisplayImage(gridViewImageId.get(position), imageViewAndroid);
        if (!gridViewImageId.get(position).equalsIgnoreCase("") && gridViewImageId.get(position) != null && !gridViewImageId.get(position).isEmpty()) {
            Picasso.with(mContext).invalidate(gridViewImageId.get(position));
            Picasso.with(mContext).load(gridViewImageId.get(position)).into(imageViewAndroid);
        }


        return gridViewAndroid;
    }

}
