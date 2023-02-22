package com.icajobguarantee.ica;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RankListView extends BaseAdapter {

    private Context mContext;

    private final ArrayList<String> listQuesId, listQuesText, listQuesImage, listQuesType,
            listQuesCAns, listQuesGAns, listcorrectans, listnotetext;

    public RankListView(Context context, ArrayList<String> listQuesId,
                          ArrayList<String> listQuesText, ArrayList<String> listQuesImage,
                          ArrayList<String> listQuesType, ArrayList<String> listQuesCAns,
                          ArrayList<String> listQuesGAns, ArrayList<String> listcorrectans,
                          ArrayList<String> listnotetext) {
        mContext = context;
        this.listQuesId = listQuesId;
        this.listQuesText = listQuesText;
        this.listQuesImage = listQuesImage;
        this.listQuesType = listQuesType;
        this.listQuesCAns = listQuesCAns;
        this.listQuesGAns = listQuesGAns;
        this.listcorrectans = listcorrectans;
        this.listnotetext = listnotetext;
    }

    @Override
    public int getCount() {
        return listQuesId.size();
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
            listViewAndroid = inflater.inflate(R.layout.rank_listview_list, null);
        }

        LinearLayout comp_award = (LinearLayout)listViewAndroid.findViewById(R.id.comp_award);
        ImageView rank_image = (ImageView)listViewAndroid.findViewById(R.id.rank_image);

        LinearLayout result_layout = (LinearLayout) listViewAndroid.findViewById(R.id.result_layout);
        LinearLayout analysis_layout = (LinearLayout) listViewAndroid.findViewById(R.id.analysis_layout);
        LinearLayout ans_layout = (LinearLayout) listViewAndroid.findViewById(R.id.ans_layout);

        LinearLayout rank_layout = (LinearLayout) listViewAndroid.findViewById(R.id.rank_layout);
        LinearLayout status_layout = (LinearLayout) listViewAndroid.findViewById(R.id.status_layout);

        LinearLayout hint_note_layout = (LinearLayout) listViewAndroid.findViewById(R.id.hint_note_layout);
        hint_note_layout.setVisibility(View.GONE);

        TextView ans_hlayout = (TextView) listViewAndroid.findViewById(R.id.ans_hlayout);

        TextView exam_name_type = (TextView) listViewAndroid.findViewById(R.id.exam_name_type);
        TextView exam_name = (TextView) listViewAndroid.findViewById(R.id.exam_name);
        TextView no_question = (TextView) listViewAndroid.findViewById(R.id.no_question);
        TextView exam_date = (TextView) listViewAndroid.findViewById(R.id.exam_date);
        TextView duration = (TextView) listViewAndroid.findViewById(R.id.duration);
        TextView total_marks = (TextView) listViewAndroid.findViewById(R.id.total_marks);
        TextView your_marks = (TextView) listViewAndroid.findViewById(R.id.your_marks);
        TextView marks_percentage = (TextView) listViewAndroid.findViewById(R.id.marks_percentage);

        TextView rank_data = (TextView) listViewAndroid.findViewById(R.id.rank_data);
        TextView rank_status = (TextView) listViewAndroid.findViewById(R.id.rank_status);

        TextView ques_id = (TextView) listViewAndroid.findViewById(R.id.ques_id);
        TextView ques_text = (TextView) listViewAndroid.findViewById(R.id.ques_text);
        ImageView ques_image = (ImageView) listViewAndroid.findViewById(R.id.ques_image);
        TextView correct_answer = (TextView) listViewAndroid.findViewById(R.id.correct_answer);
        TextView your_answer = (TextView) listViewAndroid.findViewById(R.id.your_answer);

        TextView hint_note_text = (TextView) listViewAndroid.findViewById(R.id.hint_note_text);

        if (listQuesId.get(position).equalsIgnoreCase("0")) {
            result_layout.setVisibility(View.VISIBLE);
            analysis_layout.setVisibility(View.GONE);

            String string_text = listQuesText.get(position);
            String[] str_arr = string_text.split(">=<");

            if (str_arr.length > 7) {
                rank_layout.setVisibility(View.VISIBLE);
                status_layout.setVisibility(View.VISIBLE);

                exam_name.setText(str_arr[0]);
                no_question.setText(str_arr[1]);
                duration.setText(str_arr[2]);
                total_marks.setText(str_arr[3]);
                your_marks.setText(str_arr[4]);
                marks_percentage.setText(str_arr[5]);
                rank_data.setText(str_arr[6]);
                if (str_arr[7].equalsIgnoreCase("Completed")) {
                    rank_status.setTextColor(ContextCompat.getColor(mContext, R.color.Lightgreen));
                }
                rank_status.setText(str_arr[7]);
                if (str_arr[8].equalsIgnoreCase("1")) {
                    exam_name_type.setText("Examination Name");
                    comp_award.setVisibility(View.GONE);
                } else if (str_arr[8].equalsIgnoreCase("2")) {
                    exam_name_type.setText("Competition Name");
                    comp_award.setVisibility(View.VISIBLE);
                    if (str_arr[6].equalsIgnoreCase("1")) {
                        rank_image.setImageResource(R.drawable.award01);
                    } else if (str_arr[6].equalsIgnoreCase("2")) {
                        rank_image.setImageResource(R.drawable.award02);
                    } else if (str_arr[6].equalsIgnoreCase("3")) {
                        rank_image.setImageResource(R.drawable.award03);
                    } else {
                        rank_image.setImageResource(R.drawable.award04);
                    }
                } else if (str_arr[8].equalsIgnoreCase("3")) {
                    exam_name_type.setText("Assessment Name");
                    comp_award.setVisibility(View.GONE);
                }
                exam_date.setText(str_arr[9]);
            } else {
                rank_layout.setVisibility(View.GONE);
                status_layout.setVisibility(View.GONE);

                exam_name.setText(str_arr[0]);
                no_question.setText(str_arr[1]);
                duration.setText(str_arr[2]);
                total_marks.setText(str_arr[3]);
                your_marks.setText(str_arr[4]);
                marks_percentage.setText(str_arr[5]);
                if (str_arr[6].equalsIgnoreCase("1"))
                    exam_name_type.setText("Examination Name");
                else if (str_arr[6].equalsIgnoreCase("2"))
                    exam_name_type.setText("Competition Name");
                else if (str_arr[6].equalsIgnoreCase("3"))
                    exam_name_type.setText("Assessment Name");
            }
        }

        return listViewAndroid;
    }
}
