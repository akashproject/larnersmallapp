package com.icajobguarantee.ica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListCustom extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> listViewId;
    private ArrayList<String> listViewName;
    private final ArrayList<String> listViewRProgress;
    private final ArrayList<String> listViewAProgress;
    private final ArrayList<String> listViewTProgress;

    public ListCustom(Context context, ArrayList<String> listViewId, ArrayList<String> listViewName, ArrayList<String> listViewRProgress, ArrayList<String> listViewAProgress, ArrayList<String> listViewTProgress) {
        mContext = context;
        this.listViewId = listViewId;
        this.listViewName = listViewName;
        this.listViewRProgress = listViewRProgress;
        this.listViewAProgress = listViewAProgress;
        this.listViewTProgress = listViewTProgress;
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
            listViewAndroid = inflater.inflate(R.layout.list_custom, null);
        }

        TextView list_id = (TextView) listViewAndroid.findViewById(R.id.list_id);
        TextView list_name = (TextView) listViewAndroid.findViewById(R.id.list_name);

        TextView rl_text = (TextView) listViewAndroid.findViewById(R.id.rl_text);
        TextView al_text = (TextView) listViewAndroid.findViewById(R.id.al_text);
        TextView tl_text = (TextView) listViewAndroid.findViewById(R.id.tl_text);

        LinearLayout read_layout = (LinearLayout) listViewAndroid.findViewById(R.id.read_layout);
        LinearLayout asse_layout = (LinearLayout) listViewAndroid.findViewById(R.id.asse_layout);
        LinearLayout tot_layout = (LinearLayout) listViewAndroid.findViewById(R.id.tot_layout);

        LinearLayout rl_block1 = (LinearLayout) listViewAndroid.findViewById(R.id.rl_block1);
        LinearLayout rl_block2 = (LinearLayout) listViewAndroid.findViewById(R.id.rl_block2);
        LinearLayout rl_block3 = (LinearLayout) listViewAndroid.findViewById(R.id.rl_block3);
        LinearLayout rl_block4 = (LinearLayout) listViewAndroid.findViewById(R.id.rl_block4);
        LinearLayout rl_block5 = (LinearLayout) listViewAndroid.findViewById(R.id.rl_block5);
        LinearLayout rl_block6 = (LinearLayout) listViewAndroid.findViewById(R.id.rl_block6);

        LinearLayout al_block1 = (LinearLayout) listViewAndroid.findViewById(R.id.al_block1);
        LinearLayout al_block2 = (LinearLayout) listViewAndroid.findViewById(R.id.al_block2);
        LinearLayout al_block3 = (LinearLayout) listViewAndroid.findViewById(R.id.al_block3);
        LinearLayout al_block4 = (LinearLayout) listViewAndroid.findViewById(R.id.al_block4);
        LinearLayout al_block5 = (LinearLayout) listViewAndroid.findViewById(R.id.al_block5);
        LinearLayout al_block6 = (LinearLayout) listViewAndroid.findViewById(R.id.al_block6);

        LinearLayout tl_block1 = (LinearLayout) listViewAndroid.findViewById(R.id.tl_block1);
        LinearLayout tl_block2 = (LinearLayout) listViewAndroid.findViewById(R.id.tl_block2);
        LinearLayout tl_block3 = (LinearLayout) listViewAndroid.findViewById(R.id.tl_block3);
        LinearLayout tl_block4 = (LinearLayout) listViewAndroid.findViewById(R.id.tl_block4);
        LinearLayout tl_block5 = (LinearLayout) listViewAndroid.findViewById(R.id.tl_block5);
        LinearLayout tl_block6 = (LinearLayout) listViewAndroid.findViewById(R.id.tl_block6);

        TextView rl_text2 = (TextView) listViewAndroid.findViewById(R.id.rl_text2);
        TextView rl_text3 = (TextView) listViewAndroid.findViewById(R.id.rl_text3);
        TextView rl_text4 = (TextView) listViewAndroid.findViewById(R.id.rl_text4);
        TextView rl_text5 = (TextView) listViewAndroid.findViewById(R.id.rl_text5);
        TextView rl_text6 = (TextView) listViewAndroid.findViewById(R.id.rl_text6);

        TextView al_text2 = (TextView) listViewAndroid.findViewById(R.id.al_text2);
        TextView al_text3 = (TextView) listViewAndroid.findViewById(R.id.al_text3);
        TextView al_text4 = (TextView) listViewAndroid.findViewById(R.id.al_text4);
        TextView al_text5 = (TextView) listViewAndroid.findViewById(R.id.al_text5);
        TextView al_text6 = (TextView) listViewAndroid.findViewById(R.id.al_text6);

        TextView tl_text2 = (TextView) listViewAndroid.findViewById(R.id.tl_text2);
        TextView tl_text3 = (TextView) listViewAndroid.findViewById(R.id.tl_text3);
        TextView tl_text4 = (TextView) listViewAndroid.findViewById(R.id.tl_text4);
        TextView tl_text5 = (TextView) listViewAndroid.findViewById(R.id.tl_text5);
        TextView tl_text6 = (TextView) listViewAndroid.findViewById(R.id.tl_text6);

        list_id.setText(listViewId.get(position));
        list_name.setText(listViewName.get(position));

        rl_text.setText("Course");
        al_text.setText("Assessment");
        if (!listViewRProgress.get(position).isEmpty()
                && !listViewRProgress.get(position).equalsIgnoreCase("")
                && !listViewRProgress.get(position).equalsIgnoreCase("null")
                && !listViewAProgress.get(position).isEmpty()
                && !listViewAProgress.get(position).equalsIgnoreCase("")
                && !listViewAProgress.get(position).equalsIgnoreCase("null")) {
            tl_text.setText("Overall");
        } else {
            tl_text.setText("Marks");
        }

        if (!listViewRProgress.get(position).isEmpty()
                && !listViewRProgress.get(position).equalsIgnoreCase("")
                && !listViewRProgress.get(position).equalsIgnoreCase("null")) {
            read_layout.setVisibility(View.VISIBLE);
            if (Integer.parseInt(listViewRProgress.get(position)) < 20) {
                rl_block1.setBackgroundResource(R.color.Red);
                rl_block2.setBackgroundResource(R.color.Trans);
                rl_block3.setBackgroundResource(R.color.Trans);
                rl_block4.setBackgroundResource(R.color.Trans);
                rl_block5.setBackgroundResource(R.color.Trans);
                //rl_block6.setBackgroundResource(R.color.Trans);
                rl_text2.setText(listViewRProgress.get(position) + "%");
                rl_text3.setText("");
                rl_text4.setText("");
                rl_text5.setText("");
                rl_text6.setText("");
            } else if (Integer.parseInt(listViewRProgress.get(position)) >= 20 && Integer.parseInt(listViewRProgress.get(position)) < 40) {
                rl_block1.setBackgroundResource(R.color.Orange);
                rl_block2.setBackgroundResource(R.color.Orange);
                rl_block3.setBackgroundResource(R.color.Trans);
                rl_block4.setBackgroundResource(R.color.Trans);
                rl_block5.setBackgroundResource(R.color.Trans);
                //rl_block6.setBackgroundResource(R.color.Trans);
                rl_text2.setText("");
                rl_text3.setText(listViewRProgress.get(position) + "%");
                rl_text4.setText("");
                rl_text5.setText("");
                rl_text6.setText("");
            } else if (Integer.parseInt(listViewRProgress.get(position)) >= 40 && Integer.parseInt(listViewRProgress.get(position)) < 60) {
                rl_block1.setBackgroundResource(R.color.Yellow);
                rl_block2.setBackgroundResource(R.color.Yellow);
                rl_block3.setBackgroundResource(R.color.Yellow);
                rl_block4.setBackgroundResource(R.color.Trans);
                rl_block5.setBackgroundResource(R.color.Trans);
                //rl_block6.setBackgroundResource(R.color.Trans);
                rl_text2.setText("");
                rl_text3.setText("");
                rl_text4.setText(listViewRProgress.get(position) + "%");
                rl_text5.setText("");
                rl_text6.setText("");
            } else if (Integer.parseInt(listViewRProgress.get(position)) >= 60 && Integer.parseInt(listViewRProgress.get(position)) < 80) {
                rl_block1.setBackgroundResource(R.color.Green);
                rl_block2.setBackgroundResource(R.color.Green);
                rl_block3.setBackgroundResource(R.color.Green);
                rl_block4.setBackgroundResource(R.color.Green);
                rl_block5.setBackgroundResource(R.color.Trans);
                //rl_block6.setBackgroundResource(R.color.Trans);
                rl_text2.setText("");
                rl_text3.setText("");
                rl_text4.setText("");
                rl_text5.setText(listViewRProgress.get(position) + "%");
                rl_text6.setText("");
            } else if (Integer.parseInt(listViewRProgress.get(position)) >= 80 && Integer.parseInt(listViewRProgress.get(position)) <= 100) {
                rl_block1.setBackgroundResource(R.color.Shapgreen);
                rl_block2.setBackgroundResource(R.color.Shapgreen);
                rl_block3.setBackgroundResource(R.color.Shapgreen);
                rl_block4.setBackgroundResource(R.color.Shapgreen);
                rl_block5.setBackgroundResource(R.color.Shapgreen);
                //rl_block6.setBackgroundResource(R.color.Trans);
                rl_text2.setText("");
                rl_text3.setText("");
                rl_text4.setText("");
                rl_text5.setText("");
                rl_text6.setText(listViewRProgress.get(position) + "%");
            }/* else if (Integer.parseInt(listViewRProgress.get(position)) >= 90 && Integer.parseInt(listViewRProgress.get(position)) <= 100) {
                rl_block1.setBackgroundResource(R.color.Shapgreen);
                rl_block2.setBackgroundResource(R.color.Shapgreen);
                rl_block3.setBackgroundResource(R.color.Shapgreen);
                rl_block4.setBackgroundResource(R.color.Shapgreen);
                rl_block5.setBackgroundResource(R.color.Shapgreen);
                rl_block6.setBackgroundResource(R.color.Shapgreen);
            }*/
        } else {
            read_layout.setVisibility(View.GONE);
        }

        if (!listViewAProgress.get(position).isEmpty()
                && !listViewAProgress.get(position).equalsIgnoreCase("")
                && !listViewAProgress.get(position).equalsIgnoreCase("null")) {
            asse_layout.setVisibility(View.VISIBLE);
            if (Integer.parseInt(listViewAProgress.get(position)) < 20) {
                al_block1.setBackgroundResource(R.color.Red);
                al_block2.setBackgroundResource(R.color.Trans);
                al_block3.setBackgroundResource(R.color.Trans);
                al_block4.setBackgroundResource(R.color.Trans);
                al_block5.setBackgroundResource(R.color.Trans);
                //al_block6.setBackgroundResource(R.color.Trans);
                al_text2.setText(listViewAProgress.get(position) + "%");
                al_text3.setText("");
                al_text4.setText("");
                al_text5.setText("");
                al_text6.setText("");
            } else if (Integer.parseInt(listViewAProgress.get(position)) >= 20 && Integer.parseInt(listViewAProgress.get(position)) < 40) {
                al_block1.setBackgroundResource(R.color.Orange);
                al_block2.setBackgroundResource(R.color.Orange);
                al_block3.setBackgroundResource(R.color.Trans);
                al_block4.setBackgroundResource(R.color.Trans);
                al_block5.setBackgroundResource(R.color.Trans);
                //al_block6.setBackgroundResource(R.color.Trans);
                al_text2.setText("");
                al_text3.setText(listViewAProgress.get(position) + "%");
                al_text4.setText("");
                al_text5.setText("");
                al_text6.setText("");
            } else if (Integer.parseInt(listViewAProgress.get(position)) >= 40 && Integer.parseInt(listViewAProgress.get(position)) < 60) {
                al_block1.setBackgroundResource(R.color.Yellow);
                al_block2.setBackgroundResource(R.color.Yellow);
                al_block3.setBackgroundResource(R.color.Yellow);
                al_block4.setBackgroundResource(R.color.Trans);
                al_block5.setBackgroundResource(R.color.Trans);
                //al_block6.setBackgroundResource(R.color.Trans);
                al_text2.setText("");
                al_text3.setText("");
                al_text4.setText(listViewAProgress.get(position) + "%");
                al_text5.setText("");
                al_text6.setText("");
            } else if (Integer.parseInt(listViewAProgress.get(position)) >= 60 && Integer.parseInt(listViewAProgress.get(position)) < 80) {
                al_block1.setBackgroundResource(R.color.Green);
                al_block2.setBackgroundResource(R.color.Green);
                al_block3.setBackgroundResource(R.color.Green);
                al_block4.setBackgroundResource(R.color.Green);
                al_block5.setBackgroundResource(R.color.Trans);
                //al_block6.setBackgroundResource(R.color.Trans);
                al_text2.setText("");
                al_text3.setText("");
                al_text4.setText("");
                al_text5.setText(listViewAProgress.get(position) + "%");
                al_text6.setText("");
            } else if (Integer.parseInt(listViewAProgress.get(position)) >= 80 && Integer.parseInt(listViewAProgress.get(position)) <= 100) {
                al_block1.setBackgroundResource(R.color.Shapgreen);
                al_block2.setBackgroundResource(R.color.Shapgreen);
                al_block3.setBackgroundResource(R.color.Shapgreen);
                al_block4.setBackgroundResource(R.color.Shapgreen);
                al_block5.setBackgroundResource(R.color.Shapgreen);
                //al_block6.setBackgroundResource(R.color.Trans);
                al_text2.setText("");
                al_text3.setText("");
                al_text4.setText("");
                al_text5.setText("");
                al_text6.setText(listViewAProgress.get(position) + "%");
            }/* else if (Integer.parseInt(listViewAProgress.get(position)) >= 90 && Integer.parseInt(listViewAProgress.get(position)) <= 100) {
                al_block1.setBackgroundResource(R.color.Shapgreen);
                al_block2.setBackgroundResource(R.color.Shapgreen);
                al_block3.setBackgroundResource(R.color.Shapgreen);
                al_block4.setBackgroundResource(R.color.Shapgreen);
                al_block5.setBackgroundResource(R.color.Shapgreen);
                al_block6.setBackgroundResource(R.color.Shapgreen);
            }*/
        } else {
            asse_layout.setVisibility(View.GONE);
        }

        if (!listViewTProgress.get(position).isEmpty()
                && !listViewTProgress.get(position).equalsIgnoreCase("")
                && !listViewTProgress.get(position).equalsIgnoreCase("null")) {
            tot_layout.setVisibility(View.GONE);
            if (Integer.parseInt(listViewTProgress.get(position)) < 20) {
                tl_block1.setBackgroundResource(R.color.Red);
                tl_block2.setBackgroundResource(R.color.Trans);
                tl_block3.setBackgroundResource(R.color.Trans);
                tl_block4.setBackgroundResource(R.color.Trans);
                tl_block5.setBackgroundResource(R.color.Trans);
                //tl_block6.setBackgroundResource(R.color.Trans);
                tl_text2.setText(listViewTProgress.get(position) + "%");
                tl_text3.setText("");
                tl_text4.setText("");
                tl_text5.setText("");
                tl_text6.setText("");
            } else if (Integer.parseInt(listViewTProgress.get(position)) >= 20 && Integer.parseInt(listViewTProgress.get(position)) < 40) {
                tl_block1.setBackgroundResource(R.color.Orange);
                tl_block2.setBackgroundResource(R.color.Orange);
                tl_block3.setBackgroundResource(R.color.Trans);
                tl_block4.setBackgroundResource(R.color.Trans);
                tl_block5.setBackgroundResource(R.color.Trans);
                //tl_block6.setBackgroundResource(R.color.Trans);
                tl_text2.setText("");
                tl_text3.setText(listViewTProgress.get(position) + "%");
                tl_text4.setText("");
                tl_text5.setText("");
                tl_text6.setText("");
            } else if (Integer.parseInt(listViewTProgress.get(position)) >= 40 && Integer.parseInt(listViewTProgress.get(position)) < 60) {
                tl_block1.setBackgroundResource(R.color.Yellow);
                tl_block2.setBackgroundResource(R.color.Yellow);
                tl_block3.setBackgroundResource(R.color.Yellow);
                tl_block4.setBackgroundResource(R.color.Trans);
                tl_block5.setBackgroundResource(R.color.Trans);
                //tl_block6.setBackgroundResource(R.color.Trans);
                tl_text2.setText("");
                tl_text3.setText("");
                tl_text4.setText(listViewTProgress.get(position) + "%");
                tl_text5.setText("");
                tl_text6.setText("");
            } else if (Integer.parseInt(listViewTProgress.get(position)) >= 60 && Integer.parseInt(listViewTProgress.get(position)) < 80) {
                tl_block1.setBackgroundResource(R.color.Green);
                tl_block2.setBackgroundResource(R.color.Green);
                tl_block3.setBackgroundResource(R.color.Green);
                tl_block4.setBackgroundResource(R.color.Green);
                tl_block5.setBackgroundResource(R.color.Trans);
                //tl_block6.setBackgroundResource(R.color.Trans);
                tl_text2.setText("");
                tl_text3.setText("");
                tl_text4.setText("");
                tl_text5.setText(listViewTProgress.get(position) + "%");
                tl_text6.setText("");
            } else if (Integer.parseInt(listViewTProgress.get(position)) >= 80 && Integer.parseInt(listViewTProgress.get(position)) <= 100) {
                tl_block1.setBackgroundResource(R.color.Shapgreen);
                tl_block2.setBackgroundResource(R.color.Shapgreen);
                tl_block3.setBackgroundResource(R.color.Shapgreen);
                tl_block4.setBackgroundResource(R.color.Shapgreen);
                tl_block5.setBackgroundResource(R.color.Shapgreen);
                //tl_block6.setBackgroundResource(R.color.Trans);
                tl_text2.setText("");
                tl_text3.setText("");
                tl_text4.setText("");
                tl_text5.setText("");
                tl_text6.setText(listViewTProgress.get(position) + "%");
            }/* else if (Integer.parseInt(listViewTProgress.get(position)) >= 90 && Integer.parseInt(listViewTProgress.get(position)) <= 100) {
                tl_block1.setBackgroundResource(R.color.Shapgreen);
                tl_block2.setBackgroundResource(R.color.Shapgreen);
                tl_block3.setBackgroundResource(R.color.Shapgreen);
                tl_block4.setBackgroundResource(R.color.Shapgreen);
                tl_block5.setBackgroundResource(R.color.Shapgreen);
                tl_block6.setBackgroundResource(R.color.Shapgreen);
            }*/
        } else {
            tot_layout.setVisibility(View.GONE);
        }

        return listViewAndroid;
    }

}
