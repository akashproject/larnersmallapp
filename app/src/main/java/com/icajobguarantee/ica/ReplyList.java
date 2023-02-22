package com.icajobguarantee.ica;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReplyList extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> listId;
    private final ArrayList<String> listType;
    private final ArrayList<String> listString;
    private final ArrayList<String> listDetails;

    public ReplyList(Context context, ArrayList<String> listId, ArrayList<String> listType, ArrayList<String> listString, ArrayList<String> listDetails) {
        mContext = context;
        this.listId = listId;
        this.listType = listType;
        this.listString = listString;
        this.listDetails = listDetails;
    }

    @Override
    public int getCount() {
        return listId.size();
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
            listViewAndroid = inflater.inflate(R.layout.reply_list, null);
        }


        RelativeLayout reply_box = (RelativeLayout) listViewAndroid.findViewById(R.id.reply_box);
        TextView reply_id = (TextView) listViewAndroid.findViewById(R.id.reply_id);
        TextView reply_subject = (TextView) listViewAndroid.findViewById(R.id.reply_subject);
        TextView reply_details = (TextView) listViewAndroid.findViewById(R.id.reply_details);

        if (listType.get(position).equalsIgnoreCase("1")) {
            reply_box.setBackgroundResource(R.drawable.shadow_light_blue);

            // CODE FOR ADD MARGINS
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            linearParams.setMargins(0, 0, 100, 0);
            reply_box.setLayoutParams(linearParams);
            reply_box.requestLayout();

            reply_subject.setGravity(Gravity.LEFT);
            reply_details.setGravity(Gravity.LEFT);
        } else {
            reply_box.setBackgroundResource(R.drawable.shadow_light_yellow);

            // CODE FOR ADD MARGINS
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            linearParams.setMargins(100, 0, 0, 0);
            reply_box.setLayoutParams(linearParams);
            reply_box.requestLayout();

            reply_subject.setGravity(Gravity.RIGHT);
            reply_details.setGravity(Gravity.RIGHT);
        }

        //textViewAndroid.getInputExtras(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        reply_id.setText(listId.get(position));
        reply_subject.setText(listString.get(position));
        //reply_details.setText(listViewDetails.get(position));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            reply_details.setText(Html.fromHtml(listDetails.get(position), Html.FROM_HTML_MODE_COMPACT));
        } else {
            reply_details.setText(Html.fromHtml(listDetails.get(position)));
        }

        return listViewAndroid;
    }


}
