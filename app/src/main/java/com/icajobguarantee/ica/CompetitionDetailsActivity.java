package com.icajobguarantee.ica;

import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CompetitionDetailsActivity extends AppCompatActivity {

    TextView course_title, course_code, course_type, course_details;
    Button start_btn;
    String exam_id, exam_title, exam_code, exam_type, course_id, subject_id;
    ExamStartSesManager examStartSesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_details);

        init();

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examStartSesManager.createEStartSession(exam_id, course_id, subject_id);

                System.out.println("Selected Item " + exam_id + ", " + course_id + ", " + subject_id);

                //Toast.makeText(getApplicationContext(), "Start Exam", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), CompQuestionActivity.class);
                intent.putExtra("exam_id", exam_id);
                intent.putExtra("course_id", course_id);
                intent.putExtra("subject_id", subject_id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init() {
        examStartSesManager = new ExamStartSesManager(getApplicationContext());

        course_title = (TextView) findViewById(R.id.course_title);
//        course_code = (TextView) findViewById(R.id.course_code);
//        course_type = (TextView) findViewById(R.id.course_type);
        course_details = (TextView) findViewById(R.id.course_details);
        start_btn = (Button) findViewById(R.id.start_btn);

        Intent intent = getIntent();
        exam_id = intent.getStringExtra("exam_id");
        exam_title = intent.getStringExtra("exam_title");
        exam_code = intent.getStringExtra("exam_code");
        exam_type = intent.getStringExtra("exam_type");
        course_id = intent.getStringExtra("course_id");
        subject_id = intent.getStringExtra("subject_id");

        course_title.setText(exam_title);
//        course_code.setText(exam_code);
//        course_type.setText(exam_type);
//        course_details.setText(exam_code);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            course_details.setText(Html.fromHtml(exam_code, Html.FROM_HTML_MODE_LEGACY, null, null));
        } else {
            course_details.setText(Html.fromHtml(exam_code, null, null));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
