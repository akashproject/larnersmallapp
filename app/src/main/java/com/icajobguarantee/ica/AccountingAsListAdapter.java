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

public class AccountingAsListAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    /*public ImageLoader imageLoader;
    public textCapitalized txtCpital;*/
    public String printedName;

    public AccountingAsListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.layout_account_dr_cr, null);

        TextView acc_name_val = (TextView) vi.findViewById(R.id.acc_name_val);
        TextView dr_val = (TextView) vi.findViewById(R.id.dr_val);
        TextView cr_val = (TextView) vi.findViewById(R.id.cr_val);
        ImageView clear_row = (ImageView) vi.findViewById(R.id.clear_row);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        Log.d("account_name", song.get(QuestionActivity.TAG_AC_NAME));
        Log.d("account_dr_value", song.get(QuestionActivity.TAG_DR_VAL));
        Log.d("account_cr_value", song.get(QuestionActivity.TAG_CR_VAL));

        //Setting all values in listview
        acc_name_val.setText(song.get(QuestionActivity.TAG_AC_NAME));
        dr_val.setText(song.get(QuestionActivity.TAG_DR_VAL));
        cr_val.setText(song.get(QuestionActivity.TAG_CR_VAL));

        final HashMap<String, String> finalSong1 = song;
        clear_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                String acc_name_val = finalSong1.get(QuestionActivity.TAG_AC_NAME);
                ((AssesmentExamActivity) mContext).clearItem(position);
            }
        });

        return vi;
    }
}
