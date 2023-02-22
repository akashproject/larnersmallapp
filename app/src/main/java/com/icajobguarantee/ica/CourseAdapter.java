package com.icajobguarantee.ica;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
//import android.support.v4.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseAdapter extends PagerAdapter {

    private Context mContext;
    private final ArrayList<String> cViewId;
    private final ArrayList<String> cViewString;
    private final ArrayList<String> cViewImage;
    private final ArrayList<String> cViewSelected;
    private static LayoutInflater inflater=null;

    public CourseAdapter(Context context, ArrayList<String> cViewId, ArrayList<String> cViewString, ArrayList<String> cViewImage, ArrayList<String> cViewSelected){
        this.mContext = context;
        this.cViewId = cViewId;
        this.cViewString = cViewString;
        this.cViewImage = cViewImage;
        this.cViewSelected = cViewSelected;
    }

    @Override
    public int getCount() {
        return cViewId.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (RelativeLayout)o);
        //return false;
    }

    @Override
    public float getPageWidth(final int position) {
        return 0.33f;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflater.inflate(R.layout.swipe, container, false);
        View v = inflater.inflate(R.layout.course_gallery_list, null);
        TextView course_id = (TextView)v.findViewById(R.id.course_id);
        TextView course_title = (TextView)v.findViewById(R.id.course_title);
        ImageView course_image = (ImageView)v.findViewById(R.id.course_image);

        course_id.setText(cViewId.get(position));
        course_title.setText(cViewString.get(position));

        if (!cViewImage.get(position).equalsIgnoreCase("")) {
            Picasso.with(mContext).invalidate(cViewImage.get(position));
            Picasso.with(mContext).load(cViewImage.get(position)).into(course_image);
        }
        container.addView(v);

        course_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((DashboardActivity)mContext).b_home_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_courses_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.HeaderBg));
//                ((DashboardActivity)mContext).b_exam_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_center_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_settings_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//
//                ((DashboardActivity)mContext).b_home_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_courses_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.HeaderBg));
//                ((DashboardActivity)mContext).b_exam_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_center_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_settings_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));

//                Intent intent = new Intent((DashboardActivity)mContext, SubjectActivity.class);
//                intent.putExtra("activity_for", "course");
//                intent.putExtra("list_id", cViewId.get(position));
//                intent.putExtra("list_title", cViewString.get(position));
//                mContext.startActivity(intent);

                Intent intent = new Intent((DashboardActivity)mContext, ChapterActivity.class);
                intent.putExtra("activity_for", "course");
                intent.putExtra("list_id", cViewId.get(position));
                intent.putExtra("list_title", cViewString.get(position));
                mContext.startActivity(intent);
            }
        });

        course_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((DashboardActivity)mContext).b_home_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_courses_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.HeaderBg));
//                ((DashboardActivity)mContext).b_exam_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_center_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_settings_image.setColorFilter(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//
//                ((DashboardActivity)mContext).b_home_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_courses_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.HeaderBg));
//                ((DashboardActivity)mContext).b_exam_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_center_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));
//                ((DashboardActivity)mContext).b_settings_text.setTextColor(((DashboardActivity)mContext).getResources().getColor(R.color.DarkGray));

//                Intent intent = new Intent((DashboardActivity)mContext, SubjectActivity.class);
//                intent.putExtra("activity_for", "course");
//                intent.putExtra("list_id", cViewId.get(position));
//                intent.putExtra("list_title", cViewString.get(position));
//                mContext.startActivity(intent);

                Intent intent = new Intent((DashboardActivity)mContext, ChapterActivity.class);
                intent.putExtra("activity_for", "course");
                intent.putExtra("list_id", cViewId.get(position));
                intent.putExtra("list_title", cViewString.get(position));
                mContext.startActivity(intent);

            }
        });

        return v;
        //return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
        //super.destroyItem(container, position, object);
    }
}
