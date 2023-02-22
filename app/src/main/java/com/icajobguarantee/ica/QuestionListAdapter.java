package com.icajobguarantee.ica;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionListAdapter extends BaseAdapter {
    private Context mContext;

    private final ArrayList<String> qu5_arr, quop5_arr;
    String[] opArray;
    HashMap<Integer,String> opMap;
    String old_ch_ans;
    String[] old_ch_arr;

    public QuestionListAdapter(Context context, ArrayList<String> qu5_arr,
                          ArrayList<String> quop5_arr, String old_ch_ans) {
        mContext = context;
        this.qu5_arr = qu5_arr;
        this.quop5_arr = quop5_arr;
        this.old_ch_ans = old_ch_ans;
    }

    @Override
    public int getCount() {
        return qu5_arr.size();
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
            listViewAndroid = inflater.inflate(R.layout.question5_list, null);
        }

        TextView question_text = (TextView) listViewAndroid.findViewById(R.id.question_text);
        final Spinner option_spinner = (Spinner) listViewAndroid.findViewById(R.id.option_spinner);

        question_text.setText(qu5_arr.get(position));

        String string_text = quop5_arr.get(position);
        String[] str_arr = string_text.split("=><");

        opArray = new String[str_arr.length + 1];
        opMap = new HashMap<Integer, String>();

        opMap.put(0, "0");
        opArray[0] = "Select";
        int j = 0;
        for (int i = 0; i < str_arr.length; i++) {
            String option_string = str_arr[i];
            j = i + 1;
            //Log.d("j value", String.valueOf(j));
            opMap.put(j, String.valueOf(j));
            opArray[j] = option_string;
        }

        final int mPosition = position;
        ArrayAdapter<String> adapter5 =new ArrayAdapter<String>(mContext, R.layout.spinner_item_1, opArray);
        adapter5.setDropDownViewResource(R.layout.spinner_item_1);
        option_spinner.setAdapter(adapter5);
        if (!old_ch_ans.equalsIgnoreCase("")) {
            old_ch_arr = old_ch_ans.split(",");
            option_spinner.setSelection(Integer.parseInt(old_ch_arr[position]));
        }
        option_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_name = option_spinner.getSelectedItem().toString();
                String item_id = opMap.get(option_spinner.getSelectedItemPosition());
                //QuestionActivity.selectItemDropDown(mPosition, item_id);
                ((QuestionActivity) mContext).selectItemDropDown(mPosition, item_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return listViewAndroid;
    }
}
