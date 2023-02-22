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

public class BranchlistAdapter extends BaseAdapter {
    private Activity activity;
    private Context mContext;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    /*public ImageLoader imageLoader;
    public textCapitalized txtCpital;*/
    public String printedName;

    public BranchlistAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.branch_list, null);

        TextView branch_id = (TextView) vi.findViewById(R.id.branch_id);
        TextView branch_name = (TextView) vi.findViewById(R.id.branch_name);
        TextView contact_no = (TextView) vi.findViewById(R.id.contact_no);
        TextView address = (TextView) vi.findViewById(R.id.address);
        ImageView arrow_right = (ImageView) vi.findViewById(R.id.arrow_right);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        //Setting all values in listview
        branch_id.setText(song.get(BranchSelect.TAG_BRANCH_ID));
        branch_name.setText(song.get(BranchSelect.TAG_BRANCH_NAME));
        contact_no.setText(song.get(BranchSelect.TAG_CONTACT_NO));
        address.setText(song.get(BranchSelect.TAG_ADDRESS));

        return vi;
    }
}
