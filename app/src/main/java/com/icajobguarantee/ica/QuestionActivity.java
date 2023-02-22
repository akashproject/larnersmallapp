package com.icajobguarantee.ica;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.os.Bundle;
//import androidx.appcompat.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class QuestionActivity extends AppCompatActivity implements WebInterface {

    static ArrayList<HashMap<String, String>> dataList;
    public ArrayList<HashMap<String, String>> dataList1;
    public ArrayList<HashMap<String, String>> dataList2;
    TextView no_question, question_timer, subject_name, question_id, question_text,
            dr_tot_val, cr_tot_val;
    String exam_id, course_id, subject_id, student_id, student_exam_id;
    String rheader_title = "", duration;
    String ques_id, ques_ip_type, qus_image, ques_text, ques_type, ques_status, set_answer = "",
            checkb_answer = "", radiob_answer = "", radiob_answer1 = "", accounting_answer2 = "",
            acc_subans2 = "";
    EditText answer_text, acc_amount;
    Button next_button, save_button, add_a_line_button, prev_button, skip_button;
    static int screen_i = 0;
    public int no_ques = 0, ok_flag = 1, qblock1 = 0, qblock2 = 0, qblock3 = 0, pclick_btn = 0;
    JSONArray jsonArray;
    PhotoView question_image;

    LinearLayout checkbox_layout, accounting_layout1, accounting_layout2, accounting_layout3,
            accounting_layout4, accounting_layout5, accounting_layout6;
    RadioGroup radiobutton_layout, asradiobutton_layout, liaradiobutton_layout,
            eqradiobutton_layout, radio_drcr_group;
    CheckBox checkBox;
    RadioButton radioButton, radioButton1, radioButton2, radioButton3, radio_drcr_button;

    ExamStartSesManager examStartSesManager;

    static Map<String,Integer> radioSlMap = new HashMap<>();
    HashMap<Integer,String> paccountMap, saccountMap, accountMap, reasonMap, acctypeMap;
    String[] paccspinnerArray, saccspinnerArray, accspinnerArray, reasonspinnerArray, acctypeArray;
    Spinner spinner_paccount, spinner_saccount, spinner_account, spinner_reason,
            spinner_account_type;
    ArrayList<String> acc_ans2 = new ArrayList<>();
    Double drtot = 0.0, crtot = 0.0, drtot1 = 0.0, crtot1 = 0.0;

    String pacc_id, pacc_name, sacc_id, sacc_name, aacc_id, aacc_name, aacc_type_id, aacc_type_name;
    ListView acc_data2, question5_list;
    AccountingListAdapter accountingListAdapter;

//    private static final long START_TIME_IN_MILISEC = 300000;
    private static long START_TIME_IN_MILISEC;
    private CountDownTimer mcountDownTimer;
    private Boolean mTimerRunning;
//    private long mTimeLeftInSeconds = START_TIME_IN_MILISEC;
    private long mTimeLeftInSeconds;
    private long mTimeCountInSeconds = 0;

    public static final String TAG_AC_NAME = "accname";
    public static final String TAG_DR_VAL = "drval";
    public static final String TAG_CR_VAL = "crval";

    LoginSessionManager loginSessionManager;
    AlertDialog.Builder builder, builder1;
    String[] ans_st_arr;

    ArrayList<String> qu5_arr, quop5_arr;
    String ques5_item = "";

    RadioGroup as_rgroup, li_rgroup, eq_rgroup;
    RadioButton as_incr, as_decr, as_impct, li_incr, li_decr, li_impct, eq_incr, eq_decr, eq_impct;
    LinearLayout as_spi, as_spd, li_spi, li_spd, eq_spi, eq_spd;
    EditText as_saiamount, as_sadamount, li_saiamount, li_sadamount, eq_saiamount, eq_sadamount;
    Spinner as_spi_saccount, as_spd_saccount, li_spi_saccount, li_spi_reason, li_spd_saccount,
            li_spd_reason, eq_spi_saccount, eq_spi_reason, eq_spd_saccount, eq_spd_reason;
    String as_saci_name, as_saci_id, as_sacd_name, as_sacd_id, li_saci_name, li_saci_id, li_sacir_name,
            li_sacir_id, li_sacd_name, li_sacd_id, li_sacdr_name, li_sacdr_id, eq_saci_name,
            eq_saci_id, eq_sacir_name, eq_sacir_id, eq_sacd_name, eq_sacd_id, eq_sacdr_name,
            eq_sacdr_id, as_str, li_str, eq_str, as_radio = "0", li_radio = "0", eq_radio = "0";

    RadioGroup asaclabel_group, liaclabel_group, inaclabel_group, exaclabel_group;
    RadioButton asaclabel_incr, asaclabel_decr, asaclabel_impct, liaclabel_incr, liaclabel_decr,
            liaclabel_impct, inaclabel_incr, inaclabel_decr, inaclabel_impct, exaclabel_incr,
            exaclabel_decr, exaclabel_impct;
    String asaclabel_radio = "0", liaclabel_radio = "0", inaclabel_radio = "0",
            exaclabel_radio = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        init();

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getExam(exam_id);
            //getCourse(course_id);
            //getSubject(subject_id);
            getPrimaryAccount();
            getSecondaryAccount();
            //getAccount();
            getEquityReason();
            getquestion(exam_id);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

        as_rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                //System.out.println(rb.getText());
                if (rb.getText().equals("Increase")) {
                    as_spi.setVisibility(View.VISIBLE);
                    as_spd.setVisibility(View.GONE);
                    as_radio = "1";
                    as_incr.setButtonDrawable(R.drawable.arrow12);
                    as_decr.setButtonDrawable(R.drawable.arrow21);
                    as_impct.setButtonDrawable(R.drawable.arrow31);
                } else if (rb.getText().equals("Decrease")) {
                    as_spi.setVisibility(View.GONE);
                    as_spd.setVisibility(View.VISIBLE);
                    as_radio = "2";
                    as_incr.setButtonDrawable(R.drawable.arrow11);
                    as_decr.setButtonDrawable(R.drawable.arrow22);
                    as_impct.setButtonDrawable(R.drawable.arrow31);
                } else {
                    as_spi.setVisibility(View.GONE);
                    as_spd.setVisibility(View.GONE);
                    as_radio = "3";
                    as_incr.setButtonDrawable(R.drawable.arrow11);
                    as_decr.setButtonDrawable(R.drawable.arrow21);
                    as_impct.setButtonDrawable(R.drawable.arrow32);
                }
            }
        });

        li_rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (rb.getText().equals("Increase")) {
                    li_spi.setVisibility(View.VISIBLE);
                    li_spd.setVisibility(View.GONE);
                    li_radio = "1";
                    li_incr.setButtonDrawable(R.drawable.arrow12);
                    li_decr.setButtonDrawable(R.drawable.arrow21);
                    li_impct.setButtonDrawable(R.drawable.arrow31);
                } else if (rb.getText().equals("Decrease")) {
                    li_spi.setVisibility(View.GONE);
                    li_spd.setVisibility(View.VISIBLE);
                    li_radio = "2";
                    li_incr.setButtonDrawable(R.drawable.arrow11);
                    li_decr.setButtonDrawable(R.drawable.arrow22);
                    li_impct.setButtonDrawable(R.drawable.arrow31);
                } else {
                    li_spi.setVisibility(View.GONE);
                    li_spd.setVisibility(View.GONE);
                    li_radio = "3";
                    li_incr.setButtonDrawable(R.drawable.arrow11);
                    li_decr.setButtonDrawable(R.drawable.arrow21);
                    li_impct.setButtonDrawable(R.drawable.arrow32);
                }
            }
        });

        eq_rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (rb.getText().equals("Increase")) {
                    eq_spi.setVisibility(View.VISIBLE);
                    eq_spd.setVisibility(View.GONE);
                    eq_radio = "1";
                    eq_incr.setButtonDrawable(R.drawable.arrow12);
                    eq_decr.setButtonDrawable(R.drawable.arrow21);
                    eq_impct.setButtonDrawable(R.drawable.arrow31);
                } else if (rb.getText().equals("Decrease")) {
                    eq_spi.setVisibility(View.GONE);
                    eq_spd.setVisibility(View.VISIBLE);
                    eq_radio = "2";
                    eq_incr.setButtonDrawable(R.drawable.arrow11);
                    eq_decr.setButtonDrawable(R.drawable.arrow22);
                    eq_impct.setButtonDrawable(R.drawable.arrow31);
                } else {
                    eq_spi.setVisibility(View.GONE);
                    eq_spd.setVisibility(View.GONE);
                    eq_radio = "3";
                    eq_incr.setButtonDrawable(R.drawable.arrow11);
                    eq_decr.setButtonDrawable(R.drawable.arrow21);
                    eq_impct.setButtonDrawable(R.drawable.arrow32);
                }
            }
        });

        asaclabel_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                //System.out.println(rb.getText());
                if (rb.getText().equals("Increase")) {
                    asaclabel_radio = "1";
                    asaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                    asaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    asaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else if (rb.getText().equals("Decrease")) {
                    asaclabel_radio = "2";
                    asaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    asaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                    asaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else {
                    asaclabel_radio = "3";
                    asaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    asaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    asaclabel_impct.setButtonDrawable(R.drawable.arrow32);
                }
            }
        });

        liaclabel_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                //System.out.println(rb.getText());
                if (rb.getText().equals("Increase")) {
                    liaclabel_radio = "1";
                    liaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                    liaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    liaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else if (rb.getText().equals("Decrease")) {
                    liaclabel_radio = "2";
                    liaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    liaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                    liaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else {
                    liaclabel_radio = "3";
                    liaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    liaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    liaclabel_impct.setButtonDrawable(R.drawable.arrow32);
                }
            }
        });

        inaclabel_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                //System.out.println(rb.getText());
                if (rb.getText().equals("Increase")) {
                    inaclabel_radio = "1";
                    inaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                    inaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    inaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else if (rb.getText().equals("Decrease")) {
                    inaclabel_radio = "2";
                    inaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    inaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                    inaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else {
                    inaclabel_radio = "3";
                    inaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    inaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    inaclabel_impct.setButtonDrawable(R.drawable.arrow32);
                }
            }
        });

        exaclabel_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                //System.out.println(rb.getText());
                if (rb.getText().equals("Increase")) {
                    exaclabel_radio = "1";
                    exaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                    exaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    exaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else if (rb.getText().equals("Decrease")) {
                    exaclabel_radio = "2";
                    exaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    exaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                    exaclabel_impct.setButtonDrawable(R.drawable.arrow31);
                } else {
                    exaclabel_radio = "3";
                    exaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                    exaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                    exaclabel_impct.setButtonDrawable(R.drawable.arrow32);
                }
            }
        });

        no_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pclick_btn = 0;
                final AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(QuestionActivity.this, R.style.myDialog));
                View mView = getLayoutInflater().inflate(R.layout.popup_question_selection, null);

                final ArrayList<Integer> ques_set = new ArrayList<Integer>();
                for (int i = 0; i < no_ques; i++) {
                    ques_set.add(i + 1);
                }

                CustomGridQuestion customGridQuestion = new CustomGridQuestion(QuestionActivity.this, ques_set, ans_st_arr);
                GridView question_no_grid = (GridView) mView.findViewById(R.id.question_no_grid);
                ImageView mClose = (ImageView) mView.findViewById(R.id.close_btn);

                builder.setView(mView);
                builder.setCancelable(true);
                dialog = builder.create();

                question_no_grid.setAdapter(customGridQuestion);
                question_no_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int i, long id) {
//                        Toast.makeText(getApplicationContext(), "GridView Item: " + ques_set.get(+i), Toast.LENGTH_LONG).show();
                        screen_i = ques_set.get(+i) - 1;
                        prepareJsonLayout();
                        dialog.dismiss();
                    }
                });

                mClose.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pclick_btn = 1;
                Log.d("return array length: ", String.valueOf(jsonArray.length()));
                prepareValidation();

                if (ok_flag == 1) {
                    screen_i++;
                    prepareJsonLayout();
                }
            }
        });

        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pclick_btn = 2;
                screen_i++;
                prepareJsonLayout();
            }
        });

        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pclick_btn = 0;
                screen_i--;
                prepareJsonLayout();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1 = new AlertDialog.Builder(QuestionActivity.this);
                builder1.setTitle("Submit the answer!!!");
                builder1.setMessage("Are you sure you want to submit your answer?");
                builder1.setCancelable(false);
                builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prepareValidation();
                        //if (ok_flag == 1) {
                        saveAnswered();
                        //}
                    }
                });

                builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder1.show();

            }
        });

        add_a_line_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aacc_id.equalsIgnoreCase("0") && !(acc_amount.getText().toString().trim()).equalsIgnoreCase("")) {
                    //Lets do
                    int selectedId = radio_drcr_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radio_drcr_button = (RadioButton) findViewById(selectedId);
                    String radio_text = String.valueOf(radio_drcr_button.getText());
                    String redio_sl_text = "";

                    String drval = "";
                    String crval = "";

                    if (radio_text.equalsIgnoreCase("Debit")) {
                        redio_sl_text = "1";
                        drval = acc_amount.getText().toString().trim();
                        drtot += Double.parseDouble(drval);
                    } else {
                        redio_sl_text = "2";
                        crval = acc_amount.getText().toString().trim();
                        crtot += Double.parseDouble(crval);
                    }
                    acc_subans2 = "";
                    acc_subans2 += pacc_id + "," + sacc_id + "," + aacc_id + "," + acc_amount.getText().toString().trim() + "," + redio_sl_text;
                    //acc_ans2 = new ArrayList<>();
                    acc_ans2.add(acc_subans2);

                    HashMap<String, String> catdata1 = new HashMap<>();
                    catdata1.put(TAG_AC_NAME, aacc_name);
                    catdata1.put(TAG_DR_VAL, drval);
                    catdata1.put(TAG_CR_VAL, crval);
                    dataList1.add(catdata1);

                    HashMap<String, String> catdata2 = new HashMap<>();
                    catdata2.put("aacc_id", aacc_id);
                    catdata2.put(TAG_AC_NAME, aacc_name);
                    dataList2.add(catdata2);


                    accountingListAdapter = new AccountingListAdapter(QuestionActivity.this, dataList1);
                    acc_data2.setAdapter(accountingListAdapter);

                    listViewHeight.setListViewHeightBasedOnChildren(acc_data2);

                    dr_tot_val.setText(String.valueOf(drtot));
                    cr_tot_val.setText(String.valueOf(crtot));

                    acc_amount.getText().clear();
                    spinner_paccount.setSelection(0);
                    spinner_saccount.setSelection(0);
                    spinner_account.setSelection(0);

                } else {
                    //Error code
                    Toast.makeText(getApplicationContext(), "Please select the correct answer", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);

        screen_i = 0;
        examStartSesManager = new ExamStartSesManager(getApplicationContext());

        Intent intent = getIntent();
        exam_id = intent.getStringExtra("exam_id");
        course_id = intent.getStringExtra("course_id");
        subject_id = intent.getStringExtra("subject_id");

        no_question = (TextView) findViewById(R.id.no_question);
        question_timer = (TextView) findViewById(R.id.question_timer);
        subject_name = (TextView) findViewById(R.id.subject_name);

        question_id = (TextView) findViewById(R.id.question_id);
        question_text = (TextView) findViewById(R.id.question_text);
        question_image = (PhotoView) findViewById(R.id.question_image);

        answer_text = (EditText) findViewById(R.id.answer_text);
        next_button = (Button) findViewById(R.id.next_button);
        prev_button = (Button) findViewById(R.id.prev_button);
        skip_button = (Button) findViewById(R.id.skip_button);
        save_button = (Button) findViewById(R.id.save_button);

        checkbox_layout = (LinearLayout) findViewById(R.id.checkbox_layout);
        radiobutton_layout = (RadioGroup) findViewById(R.id.radiobutton_layout);
        accounting_layout1 = (LinearLayout) findViewById(R.id.accounting_layout1);
        accounting_layout2 = (LinearLayout) findViewById(R.id.accounting_layout2);
        accounting_layout3 = (LinearLayout) findViewById(R.id.accounting_layout3);
        accounting_layout4 = (LinearLayout) findViewById(R.id.accounting_layout4);
        accounting_layout5 = (LinearLayout) findViewById(R.id.accounting_layout5);
        accounting_layout6 = (LinearLayout) findViewById(R.id.accounting_layout6);

        spinner_paccount = (Spinner) findViewById(R.id.spinner_paccount);
        spinner_saccount = (Spinner) findViewById(R.id.spinner_saccount);
        spinner_account = (Spinner) findViewById(R.id.spinner_account);
        acc_amount = (EditText) findViewById(R.id.acc_amount);
        radio_drcr_group = (RadioGroup) findViewById(R.id.radio_drcr_group);
        acc_data2 = (ListView) findViewById(R.id.acc_data2);
        dr_tot_val = (TextView) findViewById(R.id.dr_tot_val);
        cr_tot_val = (TextView) findViewById(R.id.cr_tot_val);

        add_a_line_button = (Button) findViewById(R.id.add_a_line_button);

        dataList = new ArrayList<HashMap<String,String>>();
        dataList1 = new ArrayList<HashMap<String,String>>();
        dataList2 = new ArrayList<HashMap<String,String>>();

        qu5_arr = new ArrayList<String>();
        quop5_arr = new ArrayList<String>();
        question5_list = (ListView) findViewById(R.id.question5_list);

        spinner_account_type = (Spinner) findViewById(R.id.spinner_account_type);

        as_rgroup = (RadioGroup) findViewById(R.id.as_rgroup);
        li_rgroup = (RadioGroup) findViewById(R.id.li_rgroup);
        eq_rgroup = (RadioGroup) findViewById(R.id.eq_rgroup);

        as_rgroup.clearCheck();
        li_rgroup.clearCheck();
        eq_rgroup.clearCheck();

        as_incr = (RadioButton) findViewById(R.id.as_incr);
        as_decr = (RadioButton) findViewById(R.id.as_decr);
        as_impct = (RadioButton) findViewById(R.id.as_impct);

        li_incr = (RadioButton) findViewById(R.id.li_incr);
        li_decr = (RadioButton) findViewById(R.id.li_decr);
        li_impct = (RadioButton) findViewById(R.id.li_impct);

        eq_incr = (RadioButton) findViewById(R.id.eq_incr);
        eq_decr = (RadioButton) findViewById(R.id.eq_decr);
        eq_impct = (RadioButton) findViewById(R.id.eq_impct);

        as_spi = (LinearLayout) findViewById(R.id.as_spi);
        as_spd = (LinearLayout) findViewById(R.id.as_spd);
        li_spi = (LinearLayout) findViewById(R.id.li_spi);
        li_spd = (LinearLayout) findViewById(R.id.li_spd);
        eq_spi = (LinearLayout) findViewById(R.id.eq_spi);
        eq_spd = (LinearLayout) findViewById(R.id.eq_spd);

        as_saiamount = (EditText) findViewById(R.id.as_saiamount);
        as_sadamount = (EditText) findViewById(R.id.as_sadamount);
        li_saiamount = (EditText) findViewById(R.id.li_saiamount);
        li_sadamount = (EditText) findViewById(R.id.li_sadamount);
        eq_saiamount = (EditText) findViewById(R.id.eq_saiamount);
        eq_sadamount = (EditText) findViewById(R.id.eq_sadamount);

        as_spi_saccount = (Spinner) findViewById(R.id.as_spi_saccount);
        as_spd_saccount = (Spinner) findViewById(R.id.as_spd_saccount);
        li_spi_saccount = (Spinner) findViewById(R.id.li_spi_saccount);
        li_spi_reason = (Spinner) findViewById(R.id.li_spi_reason);
        li_spd_saccount = (Spinner) findViewById(R.id.li_spd_saccount);
        li_spd_reason = (Spinner) findViewById(R.id.li_spd_reason);
        eq_spi_saccount = (Spinner) findViewById(R.id.eq_spi_saccount);
        eq_spi_reason = (Spinner) findViewById(R.id.eq_spi_reason);
        eq_spd_saccount = (Spinner) findViewById(R.id.eq_spd_saccount);
        eq_spd_reason = (Spinner) findViewById(R.id.eq_spd_reason);

        asaclabel_group = (RadioGroup) findViewById(R.id.asaclabel_group);
        liaclabel_group = (RadioGroup) findViewById(R.id.liaclabel_group);
        inaclabel_group = (RadioGroup) findViewById(R.id.inaclabel_group);
        exaclabel_group = (RadioGroup) findViewById(R.id.exaclabel_group);

        asaclabel_group.clearCheck();
        liaclabel_group.clearCheck();
        inaclabel_group.clearCheck();
        exaclabel_group.clearCheck();

        asaclabel_incr = (RadioButton) findViewById(R.id.asaclabel_incr);
        asaclabel_decr = (RadioButton) findViewById(R.id.asaclabel_decr);
        asaclabel_impct = (RadioButton) findViewById(R.id.asaclabel_impct);

        liaclabel_incr = (RadioButton) findViewById(R.id.liaclabel_incr);
        liaclabel_decr = (RadioButton) findViewById(R.id.liaclabel_decr);
        liaclabel_impct = (RadioButton) findViewById(R.id.liaclabel_impct);

        inaclabel_incr = (RadioButton) findViewById(R.id.inaclabel_incr);
        inaclabel_decr = (RadioButton) findViewById(R.id.inaclabel_decr);
        inaclabel_impct = (RadioButton) findViewById(R.id.inaclabel_impct);

        exaclabel_incr = (RadioButton) findViewById(R.id.exaclabel_incr);
        exaclabel_decr = (RadioButton) findViewById(R.id.exaclabel_decr);
        exaclabel_impct = (RadioButton) findViewById(R.id.exaclabel_impct);
    }

    private void getExam(String exam_id) {
        String url = "http://learnersmall.in/android/api/v2/exam/" + exam_id;
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 0);
    }

    private void getCourse(String course_id) {
        String url = "http://learnersmall.in/android/api/v2/course/" + course_id;
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 1);
    }

    private void getSubject(String subject_id) {
        String url = "http://learnersmall.in/android/api/v2/course/subject/" + subject_id;
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 2);
    }

    private void getquestion(String exam_id){
        //Log.d("Go for ", "Get Data " + student_code);
        //qfor = "logIn";
        String url = "https://learnersmall.in/android/api/v2/question";
        RequestParams params = new RequestParams();
        params.put("exam", exam_id);

        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.sendRequest(url, params,3);
    }

    private void getPrimaryAccount() {
        String url = "http://learnersmall.in/android/api/v2/primary_account";
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 5);
    }
    private void getSecondaryAccount() {
        String url = "http://learnersmall.in/android/api/v2/secondary_account";
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 6);
    }
    private void getAccount() {
        String url = "http://learnersmall.in/android/api/v2/account";
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 7);
    }
    private void getAccount1(String question_id) {
        String url = "http://learnersmall.in/android/api/v2/account/" + question_id;
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 7);
    }
    private void getEquityReason() {
        String url = "http://learnersmall.in/android/api/v2/reason_equity";
        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.getRequest(url, 8);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                Log.d("json return : ", response);
                JSONObject jsonObject = new JSONObject(response);
                String exam_name = jsonObject.getString("exam_name").trim();
                duration = jsonObject.getString("duration").trim();
                START_TIME_IN_MILISEC = Integer.parseInt(duration) * 60 * 1000;
                mTimeLeftInSeconds = START_TIME_IN_MILISEC;

                if (rheader_title.equalsIgnoreCase("")) {
                    rheader_title += exam_name;
                }/* else {
                    rheader_title = course_name + " - " + rheader_title;
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            try {
                Log.d("json return : ", response);
                JSONObject jsonObject = new JSONObject(response);
                String course_name = jsonObject.getString("course_name").trim();
                if (rheader_title.equalsIgnoreCase("")) {
                    rheader_title += course_name;
                } else {
                    rheader_title = course_name + " - " + rheader_title;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 2) {
            try {
                Log.d("json return : ", response);
                JSONObject jsonObject = new JSONObject(response);
                String subject_name = jsonObject.getString("subject_name").trim();
                if (rheader_title.equalsIgnoreCase("")) {
                    rheader_title += subject_name;
                } else {
                    rheader_title = rheader_title + " - " + subject_name;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 3) {
            try {
                Log.d("json return : ", response);
                jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    ans_st_arr = new String[jsonArray.length()];
                    for (int lk = 0; lk < jsonArray.length(); lk++) {
                        ans_st_arr[lk] = "0";
                    }
                    startTimer();
                    no_ques = jsonArray.length();
                    no_question.setText(String.valueOf(screen_i + 1) + "/" + String.valueOf(no_ques));
                    if (screen_i + 1 == no_ques) {
                        next_button.setVisibility(View.VISIBLE);
                        next_button.setEnabled(false);
                        prev_button.setVisibility(View.VISIBLE);
                        if (screen_i + 1 == 1) {
                            prev_button.setEnabled(false);
                        } else {
                            prev_button.setEnabled(true);
                        }
                        skip_button.setVisibility(View.GONE);
                        save_button.setVisibility(View.VISIBLE);
                    } else {
                        next_button.setVisibility(View.VISIBLE);
                        next_button.setEnabled(true);
                        prev_button.setVisibility(View.VISIBLE);
                        if (screen_i + 1 == 1) {
                            prev_button.setEnabled(false);
                        } else {
                            prev_button.setEnabled(true);
                        }
                        skip_button.setVisibility(View.VISIBLE);
                        save_button.setVisibility(View.GONE);
                    }
                    JSONObject jsonObject = jsonArray.getJSONObject(screen_i);
                    ques_id = jsonObject.getString("id").trim();
                    ques_ip_type = jsonObject.getString("ques_ip_type").trim();
                    ques_text = jsonObject.getString("qus").trim();
                    qus_image = jsonObject.getString("qus_image").trim();
                    ques_type = jsonObject.getString("type").trim();
                    ques_status = jsonObject.getString("state").trim();
                    if (ques_status.equalsIgnoreCase("1")) {
                        question_id.setText(ques_id);

                        if (ques_type.equalsIgnoreCase("qusen")) {
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.VISIBLE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.GONE);
                        } else if (ques_type.equalsIgnoreCase("check")) {
                            qblock1 = 0;
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.VISIBLE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.GONE);

                            checkbox_layout.removeAllViews();
                            if (qblock1 == 0) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    String option_string = jsonArray1.getString(i).trim();
                                    checkBox = new CheckBox(this);
                                    checkBox.setId(i + 1);
                                    checkBox.setText(option_string);
                                    checkBox.setOnClickListener(getOnClickDoSomething(checkBox));
                                    checkbox_layout.addView(checkBox);
                                }
                                qblock1 = 1;
                            }
                        } else if (ques_type.equalsIgnoreCase("radio")) {
                            qblock2 = 0;
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.VISIBLE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.GONE);

                            radiobutton_layout.removeAllViews();
                            if (qblock2 == 0) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                                if (jsonArray1.length() > 0) {
                                    radiobutton_layout = new RadioGroup(this);
                                }
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    String option_string = jsonArray1.getString(i).trim();
                                    radioButton = new RadioButton(this);
                                    radioButton.setId(i + 1);
                                    radioButton.setText(option_string);
                                    radioButton.setOnClickListener(getOnClickRDoSomething(radioButton));
                                    radiobutton_layout.addView(radioButton);
                                }
                                //((ViewGroup) findViewById(R.id.radiobutton_layout)).removeAllViews();
                                ((ViewGroup) findViewById(R.id.radiobutton_layout)).addView(radiobutton_layout);
                                qblock2 = 1;
                            }
                        } else if (ques_type.equalsIgnoreCase("accounting1")) {
                            qblock3 = 0;
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.VISIBLE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.GONE);

                            if (qblock3 == 0) {
                                final JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                                if (jsonArray1.length() > 0) {
                                    asradiobutton_layout = new RadioGroup(this);
                                    liaradiobutton_layout = new RadioGroup(this);
                                    eqradiobutton_layout = new RadioGroup(this);
                                }

                                //asradiobutton_layout.removeAllViews();
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    String option_string = jsonArray1.getString(i).trim();
                                    radioButton1 = new RadioButton(QuestionActivity.this);
                                    radioButton1.setId(i + 1);
                                    if (i == 0) {
                                        radioButton1.setButtonDrawable(R.drawable.arrow11);
                                    } else if (i == 1) {
                                        radioButton1.setButtonDrawable(R.drawable.arrow21);
                                    } else if (i == 2) {
                                        radioButton1.setButtonDrawable(R.drawable.arrow31);
                                    }
                                    radioButton1.setPadding(20, 20, 20, 20);
                                    //radioButton1.setText(option_string);
                                    radioButton1.setOnClickListener(getOnClickARDoSomething(radioButton1, 1));
                                    asradiobutton_layout.addView(radioButton1);
                                }
                                ((ViewGroup) findViewById(R.id.asradiobutton_layout)).removeAllViews();
                                ((ViewGroup) findViewById(R.id.asradiobutton_layout)).addView(asradiobutton_layout);

                                asradiobutton_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        System.out.println("custom radio button checked : " + checkedId);
                                        asradiobutton_layout.removeAllViews();
                                        for (int i = 0; i < jsonArray1.length(); i++) {
                                            radioButton1 = new RadioButton(QuestionActivity.this);
                                            radioButton1.setId(i + 1);
                                            if (i == 0) {
                                                if (i + 1 == checkedId) {
                                                    radioButton1.setButtonDrawable(R.drawable.arrow12);
                                                } else {
                                                    radioButton1.setButtonDrawable(R.drawable.arrow11);
                                                }
                                            } else if (i == 1) {
                                                if (i + 1 == checkedId) {
                                                    radioButton1.setButtonDrawable(R.drawable.arrow22);
                                                } else {
                                                    radioButton1.setButtonDrawable(R.drawable.arrow21);
                                                }
                                            } else if (i == 2) {
                                                if (i + 1 == checkedId) {
                                                    radioButton1.setButtonDrawable(R.drawable.arrow32);
                                                } else {
                                                    radioButton1.setButtonDrawable(R.drawable.arrow31);
                                                }
                                            }
                                            radioButton1.setPadding(20, 20, 20, 20);
                                            //radioButton1.setText(option_string);
                                            radioButton1.setOnClickListener(getOnClickARDoSomething(radioButton1, 1));
                                            asradiobutton_layout.addView(radioButton1);
                                        }
                                        ((ViewGroup) findViewById(R.id.asradiobutton_layout)).removeAllViews();
                                        ((ViewGroup) findViewById(R.id.asradiobutton_layout)).addView(asradiobutton_layout);
                                    }
                                });

                                //liaradiobutton_layout.removeAllViews();
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    String option_string = jsonArray1.getString(i).trim();
                                    radioButton2 = new RadioButton(QuestionActivity.this);
                                    radioButton2.setId(i + 1);
                                    if (i == 0) {
                                        radioButton2.setButtonDrawable(R.drawable.arrow11);
                                    } else if (i == 1) {
                                        radioButton2.setButtonDrawable(R.drawable.arrow21);
                                    } else if (i == 2) {
                                        radioButton2.setButtonDrawable(R.drawable.arrow31);
                                    }
                                    radioButton2.setPadding(20, 20, 20, 20);
                                    //radioButton2.setText(option_string);
                                    radioButton2.setOnClickListener(getOnClickARDoSomething(radioButton2, 2));
                                    liaradiobutton_layout.addView(radioButton2);
                                }
                                ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).removeAllViews();
                                ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).addView(liaradiobutton_layout);

                                liaradiobutton_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        System.out.println("custom radio button checked : " + checkedId);
                                        liaradiobutton_layout.removeAllViews();
                                        for (int i = 0; i < jsonArray1.length(); i++) {
                                            radioButton2 = new RadioButton(QuestionActivity.this);
                                            radioButton2.setId(i + 1);
                                            if (i == 0) {
                                                if (i + 1 == checkedId) {
                                                    radioButton2.setButtonDrawable(R.drawable.arrow12);
                                                } else {
                                                    radioButton2.setButtonDrawable(R.drawable.arrow11);
                                                }
                                            } else if (i == 1) {
                                                if (i + 1 == checkedId) {
                                                    radioButton2.setButtonDrawable(R.drawable.arrow22);
                                                } else {
                                                    radioButton2.setButtonDrawable(R.drawable.arrow21);
                                                }
                                            } else if (i == 2) {
                                                if (i + 1 == checkedId) {
                                                    radioButton2.setButtonDrawable(R.drawable.arrow32);
                                                } else {
                                                    radioButton2.setButtonDrawable(R.drawable.arrow31);
                                                }
                                            }
                                            radioButton2.setPadding(20, 20, 20, 20);
                                            //radioButton1.setText(option_string);
                                            radioButton2.setOnClickListener(getOnClickARDoSomething(radioButton2, 2));
                                            liaradiobutton_layout.addView(radioButton2);
                                        }
                                        ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).removeAllViews();
                                        ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).addView(liaradiobutton_layout);
                                    }
                                });

                                //eqradiobutton_layout.removeAllViews();
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    String option_string = jsonArray1.getString(i).trim();
                                    radioButton3 = new RadioButton(QuestionActivity.this);
                                    radioButton3.setId(i + 1);
                                    if (i == 0) {
                                        radioButton3.setButtonDrawable(R.drawable.arrow11);
                                    } else if (i == 1) {
                                        radioButton3.setButtonDrawable(R.drawable.arrow21);
                                    } else if (i == 2) {
                                        radioButton3.setButtonDrawable(R.drawable.arrow31);
                                    }
                                    radioButton3.setPadding(20, 20, 20, 20);
                                    //radioButton3.setText(option_string);
                                    radioButton3.setOnClickListener(getOnClickARDoSomething(radioButton3, 3));
                                    eqradiobutton_layout.addView(radioButton3);
                                }
                                ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).removeAllViews();
                                ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).addView(eqradiobutton_layout);

                                eqradiobutton_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        System.out.println("custom radio button checked : " + checkedId);
                                        eqradiobutton_layout.removeAllViews();
                                        for (int i = 0; i < jsonArray1.length(); i++) {
                                            radioButton3 = new RadioButton(QuestionActivity.this);
                                            radioButton3.setId(i + 1);
                                            if (i == 0) {
                                                if (i + 1 == checkedId) {
                                                    radioButton3.setButtonDrawable(R.drawable.arrow12);
                                                } else {
                                                    radioButton3.setButtonDrawable(R.drawable.arrow11);
                                                }
                                            } else if (i == 1) {
                                                if (i + 1 == checkedId) {
                                                    radioButton3.setButtonDrawable(R.drawable.arrow22);
                                                } else {
                                                    radioButton3.setButtonDrawable(R.drawable.arrow21);
                                                }
                                            } else if (i == 2) {
                                                if (i + 1 == checkedId) {
                                                    radioButton3.setButtonDrawable(R.drawable.arrow32);
                                                } else {
                                                    radioButton3.setButtonDrawable(R.drawable.arrow31);
                                                }
                                            }
                                            radioButton3.setPadding(20, 20, 20, 20);
                                            //radioButton1.setText(option_string);
                                            radioButton3.setOnClickListener(getOnClickARDoSomething(radioButton3, 3));
                                            eqradiobutton_layout.addView(radioButton3);
                                        }
                                        ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).removeAllViews();
                                        ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).addView(eqradiobutton_layout);
                                    }
                                });
                                qblock3 = 1;
                            }
                        } else if (ques_type.equalsIgnoreCase("accounting2")) {
//                            removeJournal();
                            drtot = 0.0;
                            crtot = 0.0;
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.VISIBLE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.GONE);

                            ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, paccspinnerArray);
                            adapter1.setDropDownViewResource(R.layout.spinner_item_1);
                            spinner_paccount.setAdapter(adapter1);
                            spinner_paccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    pacc_name = spinner_paccount.getSelectedItem().toString();
                                    pacc_id = paccountMap.get(spinner_paccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                            adapter2.setDropDownViewResource(R.layout.spinner_item_1);
                            spinner_saccount.setAdapter(adapter2);
                            spinner_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    sacc_name = spinner_saccount.getSelectedItem().toString();
                                    sacc_id = saccountMap.get(spinner_saccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            getAccount1(ques_id);
//                            ArrayAdapter<String> adapter3 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, accspinnerArray);
//                            adapter3.setDropDownViewResource(R.layout.spinner_item_1);
//                            spinner_account.setAdapter(adapter3);
//                            spinner_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    aacc_name = spinner_account.getSelectedItem().toString();
//                                    aacc_id = accountMap.get(spinner_account.getSelectedItemPosition());
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
                        } else if (ques_type.equalsIgnoreCase("accounting3")) {
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.VISIBLE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.GONE);

                            Picasso.with(this).invalidate(ques_text);
                            Picasso.with(this).load(ques_text).into(question_image);

                            JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                            acctypeArray = new String[jsonArray1.length() + 1];
                            acctypeMap = new HashMap<Integer, String>();

                            acctypeMap.put(0, "0");
                            acctypeArray[0] = "Select Account Type";
                            int j = 0;
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                String option_string = jsonArray1.getString(i).trim();
                                j = i + 1;
                                //Log.d("j value", String.valueOf(j));
                                acctypeMap.put(j, String.valueOf(j));
                                acctypeArray[j] = option_string;
                            }

                            ArrayAdapter<String> adapter4 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, acctypeArray);
                            adapter4.setDropDownViewResource(R.layout.spinner_item_1);
                            spinner_account_type.setAdapter(adapter4);
                            spinner_account_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    aacc_type_name = spinner_account_type.getSelectedItem().toString();
                                    aacc_type_id = acctypeMap.get(spinner_account_type.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else if (ques_type.equalsIgnoreCase("accounting4")) {
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.VISIBLE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.GONE);

                            ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                            adapter1.setDropDownViewResource(R.layout.spinner_item_1);
                            as_spi_saccount.setAdapter(adapter1);
                            as_spi_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    as_saci_name = as_spi_saccount.getSelectedItem().toString();
                                    as_saci_id = saccountMap.get(as_spi_saccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                            adapter2.setDropDownViewResource(R.layout.spinner_item_1);
                            as_spd_saccount.setAdapter(adapter2);
                            as_spd_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    as_sacd_name = as_spd_saccount.getSelectedItem().toString();
                                    as_saci_id = saccountMap.get(as_spd_saccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter3 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                            adapter3.setDropDownViewResource(R.layout.spinner_item_1);
                            li_spi_saccount.setAdapter(adapter3);
                            li_spi_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    li_saci_name = li_spi_saccount.getSelectedItem().toString();
                                    li_saci_id = saccountMap.get(li_spi_saccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter7 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                            adapter7.setDropDownViewResource(R.layout.spinner_item_1);
                            li_spi_reason.setAdapter(adapter7);
                            li_spi_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    li_sacir_name = li_spi_reason.getSelectedItem().toString();
                                    li_sacir_id = reasonMap.get(li_spi_reason.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter4 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                            adapter4.setDropDownViewResource(R.layout.spinner_item_1);
                            li_spd_saccount.setAdapter(adapter4);
                            li_spd_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    li_sacd_name = li_spd_saccount.getSelectedItem().toString();
                                    li_sacd_id = saccountMap.get(li_spd_saccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter8 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                            adapter8.setDropDownViewResource(R.layout.spinner_item_1);
                            li_spd_reason.setAdapter(adapter8);
                            li_spd_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    li_sacdr_name = li_spd_reason.getSelectedItem().toString();
                                    li_sacdr_id = reasonMap.get(li_spd_reason.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter5 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                            adapter5.setDropDownViewResource(R.layout.spinner_item_1);
                            eq_spi_saccount.setAdapter(adapter5);
                            eq_spi_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    eq_saci_name = eq_spi_saccount.getSelectedItem().toString();
                                    eq_saci_id = saccountMap.get(eq_spi_saccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter9 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                            adapter9.setDropDownViewResource(R.layout.spinner_item_1);
                            eq_spi_reason.setAdapter(adapter9);
                            eq_spi_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    eq_sacir_name = eq_spi_reason.getSelectedItem().toString();
                                    eq_sacir_id = reasonMap.get(eq_spi_reason.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter6 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                            adapter6.setDropDownViewResource(R.layout.spinner_item_1);
                            eq_spd_saccount.setAdapter(adapter6);
                            eq_spd_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    eq_sacd_name = eq_spd_saccount.getSelectedItem().toString();
                                    eq_sacd_id = saccountMap.get(eq_spd_saccount.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ArrayAdapter<String> adapter10 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                            adapter10.setDropDownViewResource(R.layout.spinner_item_1);
                            eq_spd_reason.setAdapter(adapter10);
                            eq_spd_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    eq_sacdr_name = eq_spd_reason.getSelectedItem().toString();
                                    eq_sacdr_id = reasonMap.get(eq_spd_reason.getSelectedItemPosition());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } else if (ques_type.equalsIgnoreCase("accounting5")) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". Select appropriate option from dropdown list………");
                            //ques_text;

                            question_image.setVisibility(View.GONE);
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.VISIBLE);
                            accounting_layout6.setVisibility(View.GONE);

                            qu5_arr.clear();
                            String[] que5_arr = ques_text.split("=><");
                            for (int l = 0; l < que5_arr.length; l++) {
                                qu5_arr.add(que5_arr[l]);
                            }

                            JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                            String option_string = "";
                            for (int p = 0; p < jsonArray1.length(); p++) {
                                if (option_string.isEmpty())
                                    option_string += jsonArray1.getString(p).trim();
                                else
                                    option_string += "=><" + jsonArray1.getString(p).trim();
                            }
                            quop5_arr.clear();
                            for (int t = 0; t < jsonArray1.length(); t++) {
                                quop5_arr.add(option_string);
                            }

                            QuestionListAdapter resultListView = new QuestionListAdapter(this, qu5_arr, quop5_arr, "");
                            question5_list.setAdapter(resultListView);
                        } else if (ques_type.equalsIgnoreCase("accounting6")) {
                            if (!ques_text.isEmpty()) {
                                question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            } else {
                                question_text.setText(String.valueOf(screen_i + 1) + ". ");
                                if (!qus_image.isEmpty()) {
                                    question_image.setVisibility(View.VISIBLE);
                                    Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                    Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                                } else {
                                    question_image.setVisibility(View.GONE);
                                }
                            }
                            answer_text.setVisibility(View.GONE);
                            checkbox_layout.setVisibility(View.GONE);
                            radiobutton_layout.setVisibility(View.GONE);
                            accounting_layout1.setVisibility(View.GONE);
                            accounting_layout2.setVisibility(View.GONE);
                            accounting_layout3.setVisibility(View.GONE);
                            accounting_layout4.setVisibility(View.GONE);
                            accounting_layout5.setVisibility(View.GONE);
                            accounting_layout6.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 4) {
            try {
                Log.d("Response data : ", response);
                JSONObject jsonObject = new JSONObject(response);
                student_exam_id = jsonObject.getString("id").trim();
//                Toast.makeText(getApplicationContext(), "Thank you, you can get your result shortly." + student_exam_id, Toast.LENGTH_LONG).show();
                examStartSesManager.clearEStartSession();
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("student_exam_id", student_exam_id);
                intent.putExtra("exam_id", exam_id);
                startActivity(intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 5) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                paccspinnerArray = new String[jsonArray.length() + 1];
                paccountMap = new HashMap<Integer,String>();

                paccountMap.put(0, "0");
                paccspinnerArray[0] = "Select Primary Account";
                int j = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id").trim();
                    String account_name = jsonObject1.getString("account_name").trim();
                    j = i + 1;
                    //Log.d("j value", String.valueOf(j));
                    paccountMap.put(j, id);
                    paccspinnerArray[j] = account_name;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 6) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                saccspinnerArray = new String[jsonArray.length() + 1];
                saccountMap = new HashMap<Integer,String>();

                saccountMap.put(0, "0");
                saccspinnerArray[0] = "Select Secondary Account";
                int j = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id").trim();
                    String acc_name = jsonObject1.getString("acc_name").trim();
                    j = i + 1;
                    //Log.d("j value", String.valueOf(j));
                    saccountMap.put(j, id);
                    saccspinnerArray[j] = acc_name;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 7) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                accspinnerArray = new String[jsonArray.length() + 1];
                accountMap = new HashMap<Integer,String>();

                accountMap.put(0, "0");
                accspinnerArray[0] = "Select Account";
                int j = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id").trim();
                    String accName = jsonObject1.getString("accName").trim();
                    j = i + 1;
                    //Log.d("j value", String.valueOf(j));
                    accountMap.put(j, id);
                    accspinnerArray[j] = accName;
                }

                ArrayAdapter<String> adapter3 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, accspinnerArray);
                adapter3.setDropDownViewResource(R.layout.spinner_item_1);
                spinner_account.setAdapter(adapter3);
                spinner_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        aacc_name = spinner_account.getSelectedItem().toString();
                        aacc_id = accountMap.get(spinner_account.getSelectedItemPosition());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 8) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                reasonspinnerArray = new String[jsonArray.length() + 1];
                reasonMap = new HashMap<Integer,String>();

                reasonMap.put(0, "0");
                reasonspinnerArray[0] = "Select Reason";
                int j = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id").trim();
                    String reason_name = jsonObject1.getString("reason_name").trim();
                    j = i + 1;
                    //Log.d("j value", String.valueOf(j));
                    reasonMap.put(j, id);
                    reasonspinnerArray[j] = reason_name;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        subject_name.setText(rheader_title);
    }

    @Override
    public void onFailure(int flag) {

    }

    View.OnClickListener getOnClickDoSomething(final Button btn) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                int find_flag = 0;
                if (!checkb_answer.equalsIgnoreCase("")) {
                    String[] pos_array = checkb_answer.split(",");
                    checkb_answer = "";
                    for (int i = 0; i < pos_array.length; i++) {
                        if (!(String.valueOf(btn.getId())).equalsIgnoreCase(pos_array[i])) {
                            if (checkb_answer.equalsIgnoreCase("")) {
                                checkb_answer = pos_array[i];
                            } else {
                                checkb_answer += "," + pos_array[i];
                            }
                        } else {
                            find_flag =1;
                        }
                    }
                    if (find_flag == 0) {
                        checkb_answer += "," + String.valueOf(btn.getId());
                    }
                } else {
                    checkb_answer = String.valueOf(btn.getId());
                }

                if (!checkb_answer.equalsIgnoreCase("")) {
                    String[] pos_array1 = checkb_answer.split(",");
                    for (int m = 0; m < pos_array1.length - 1; m++) {
                        for (int n = m + 1; n < pos_array1.length; n++) {
                            if (Integer.parseInt(pos_array1[m]) > Integer.parseInt(pos_array1[n])) {
                                String t = pos_array1[n];
                                pos_array1[n] = pos_array1[m];
                                pos_array1[m] = t;
                            }
                        }
                    }
                    checkb_answer = "";
                    for (int k = 0; k < pos_array1.length; k++) {
                        if (checkb_answer.equalsIgnoreCase("")) {
                            checkb_answer += pos_array1[k];
                        } else {
                            checkb_answer += "," + pos_array1[k];
                        }
                    }
                }

                System.out.println("*************checked Item******" + checkb_answer);
            }
        };
    }

    View.OnClickListener getOnClickRDoSomething(final Button button4) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                radiob_answer = String.valueOf(button4.getId());
                System.out.println("*************checked Item******" + radiob_answer);
            }
        };
    }

    View.OnClickListener getOnClickARDoSomething(final Button button5, final int from_pos) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                radioSlMap.put(String.valueOf(from_pos), button5.getId());
                Map<String, Integer> sorted = sortByKeys(radioSlMap);
                radiob_answer1 = "";
                Iterator hmIterator = sorted.entrySet().iterator();
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry)hmIterator.next();
                    String radans1 = String.valueOf(mapElement.getValue());
                    if (radiob_answer1.equalsIgnoreCase("")) {
                        radiob_answer1 = radans1;
                    } else {
                        radiob_answer1 += "," + radans1;
                    }
                }


                System.out.println("*************checked Item******" + radiob_answer1);
            }
        };
    }

    public void selectItemDropDown(int mPosition, String item) {
        System.out.println("Selected item " + item + " of position " + mPosition);
        //Toast.makeText(, "Selected item " + item, Toast.LENGTH_LONG).show();
        if (item.equalsIgnoreCase("0")) {
            if (ques5_item.isEmpty()) {
                ques5_item = item;
            } else {
                String[] ques5_item_arr = ques5_item.split(",");
                //ques5_item += "," + item;
                ques5_item = "";
                if (ques5_item_arr.length == mPosition) {
                    for (int i = 0; i < ques5_item_arr.length; i++) {
                        if (ques5_item.isEmpty()) {
                            ques5_item = ques5_item_arr[i];
                        } else {
                            ques5_item += "," + ques5_item_arr[i];
                        }
                    }
                    ques5_item += "," + item;
                } else {
                    for (int i = 0; i < ques5_item_arr.length; i++) {
                        if (i == mPosition) {
                            ques5_item_arr[i] = item;
                            break;
                        }
                    }
                    for (int i = 0; i < ques5_item_arr.length; i++) {
                        if (ques5_item.isEmpty()) {
                            ques5_item = ques5_item_arr[i];
                        } else {
                            ques5_item += "," + ques5_item_arr[i];
                        }
                    }
                }
            }
        } else {
            String[] ques5_item_arr = ques5_item.split(",");
            ques5_item = "";
            for (int i = 0; i < ques5_item_arr.length; i++) {
                if (i == mPosition) {
                    ques5_item_arr[i] = item;
                    break;
                }
            }

            for (int i = 0; i < ques5_item_arr.length; i++) {
                if (ques5_item.isEmpty()) {
                    ques5_item = ques5_item_arr[i];
                } else {
                    ques5_item += "," + ques5_item_arr[i];
                }
            }
        }

        System.out.println("Selected item Result " + ques5_item);
    }

    public void prepareValidation() {
        String answer_string = "";
        if (ques_type.equalsIgnoreCase("qusen")) {
            if (answer_text.getText().toString().length() == 0) {
                answer_text.requestFocus();
                answer_text.setError("Answer cannot be empty");
                ok_flag = 0;
                return;
            } else {
                set_answer = answer_text.getText().toString().trim();
                answer_string = set_answer;
                ok_flag = 1;
            }
        } else if (ques_type.equalsIgnoreCase("check")) {
            if (checkb_answer.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please select the correct answer", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            } else {
                answer_string = checkb_answer;
                checkb_answer = "";
                ok_flag = 1;
            }
        } else if (ques_type.equalsIgnoreCase("radio")) {
            if (radiob_answer.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please select the correct answer", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            } else {
                answer_string = radiob_answer;
                radiob_answer = "";
                ok_flag = 1;
            }
        } else if (ques_type.equalsIgnoreCase("accounting1")) {
            if (radiob_answer1.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please select the correct answer", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            } else {
                answer_string = radiob_answer1;
                radiob_answer1 = "";
                ok_flag = 1;
            }
        } else if (ques_type.equalsIgnoreCase("accounting2")) {
            if (acc_ans2.size()>0) {
                if (drtot.equals(crtot)) {
                    String res_str = "";
                    for (int m = 0; m < acc_ans2.size(); m++) {
                        if (res_str.equalsIgnoreCase("")) {
                            res_str += "[{" + acc_ans2.get(m).toString() + "}";
                        } else {
                            res_str += ",{" + acc_ans2.get(m).toString() + "}";
                        }
                    }
                    res_str += "]";
                    answer_string = res_str;
                    ok_flag = 1;
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter the correct answer", Toast.LENGTH_LONG).show();
                    ok_flag = 0;
                    return;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please select the correct answer", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            }
        } else if (ques_type.equalsIgnoreCase("accounting3")) {
            if (aacc_type_name.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please select account type", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            } else {
//                answer_string = aacc_type_name;
                if (aacc_type_id.equalsIgnoreCase("0")) {
//                    Toast.makeText(getApplicationContext(), "Please select account type", Toast.LENGTH_LONG).show();
                    ok_flag = 0;
//                    return;
                } else {
                    answer_string = aacc_type_id;
                    aacc_type_id = "0";
                    ok_flag = 1;
                }
            }
        } else if (ques_type.equalsIgnoreCase("accounting4")) {
            if (as_radio.equalsIgnoreCase("0") &&
                    li_radio.equalsIgnoreCase("0") &&
                    eq_radio.equalsIgnoreCase("0")) {
                Toast.makeText(getApplicationContext(), "Please enter correct answer", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            } else {
                if (as_radio.equalsIgnoreCase("1")) {
                    String as_amount = "0";
                    if (as_saiamount.getText().toString().length() > 0) {
                        as_amount = as_saiamount.getText().toString().trim();
                    }
                    as_str = as_radio + ":{" + as_saci_id + "," + as_amount + "}";
                } else if (as_radio.equalsIgnoreCase("2")) {
                    String as_amount = "0";
                    if (as_sadamount.getText().toString().length() > 0) {
                        as_amount = as_sadamount.getText().toString().trim();
                    }
                    as_str = as_radio + ":{" + as_sacd_id + "," + as_amount + "}";
                } else if (as_radio.equalsIgnoreCase("3")) {
                    as_str = as_radio + ":{}";
                }

                if (li_radio.equalsIgnoreCase("1")) {
                    String li_amount = "0";
                    if (li_saiamount.getText().toString().length() > 0) {
                        li_amount = li_saiamount.getText().toString().trim();
                    }
                    li_str = li_radio + ":{" + li_saci_id + "," + li_sacir_id + "," + li_amount + "}";
                } else if (li_radio.equalsIgnoreCase("2")) {
                    String li_amount = "0";
                    if (li_sadamount.getText().toString().length() > 0) {
                        li_amount = li_sadamount.getText().toString().trim();
                    }
                    li_str = li_radio + ":{" + li_sacd_id + "," + li_sacdr_id + "," + li_amount + "}";
                } else if (li_radio.equalsIgnoreCase("3")) {
                    li_str = li_radio + ":{}";
                }

                if (eq_radio.equalsIgnoreCase("1")) {
                    String eq_amount = "0";
                    if (eq_saiamount.getText().toString().length() > 0) {
                        eq_amount = eq_saiamount.getText().toString().trim();
                    }
                    eq_str = eq_radio + ":{" + eq_saci_id + "," + eq_sacir_id + "," + eq_amount + "}";
                } else if (eq_radio.equalsIgnoreCase("2")) {
                    String eq_amount = "0";
                    if (eq_sadamount.getText().toString().length() > 0) {
                        eq_amount = eq_sadamount.getText().toString().trim();
                    }
                    eq_str = eq_radio + ":{" + eq_sacd_id + "," + eq_sacdr_id + "," + eq_amount + "}";
                } else if (eq_radio.equalsIgnoreCase("3")) {
                    eq_str = eq_radio + ":{}";
                }

                answer_string = "[{" + as_str + "},{" + li_str + "},{" + eq_str + "}]";
                ok_flag = 1;
            }
        } else if (ques_type.equalsIgnoreCase("accounting5")) {
            System.out.println("Selected item split " + ques5_item);
            String[] q5_item_arr = ques5_item.split(",");
            int q5select = 0;
            for (int i = 0; i < q5_item_arr.length; i++) {
                if (q5_item_arr[i].equalsIgnoreCase("0")) {
                    q5select = 1;
                }
            }
            if (q5select == 1) {
                Toast.makeText(getApplicationContext(), "Please answer all the question", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            } else {
                answer_string = ques5_item;
                ok_flag = 1;
            }

            System.out.println("Selected item final " + answer_string);
        } else if (ques_type.equalsIgnoreCase("accounting6")) {
            if (asaclabel_radio.equalsIgnoreCase("0") &&
                    liaclabel_radio.equalsIgnoreCase("0") &&
                    inaclabel_radio.equalsIgnoreCase("0") &&
                    exaclabel_radio.equalsIgnoreCase("0")) {
                Toast.makeText(getApplicationContext(), "Please enter correct answer", Toast.LENGTH_LONG).show();
                ok_flag = 0;
                return;
            } else {
                answer_string = asaclabel_radio + "," + liaclabel_radio + "," + inaclabel_radio + "," + exaclabel_radio;
                ok_flag = 1;
            }
        }

//        n

        int brflag = 0;
        int brpos = 0;
        if (ok_flag == 1) {
            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    HashMap<String, String> song = new HashMap<String, String>();
                    song = dataList.get(i);
                    if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                        Log.d("question_id ", song.get("ques_id"));
                        Log.d("question_text ", song.get("ques_text"));
                        Log.d("question_type ", song.get("ques_type"));
                        Log.d("question_string ", song.get("ques_answer"));
                        Log.d("break_done ", "break yes");
                        brpos = i;
                        brflag = 1;
                        break;
                    }
                }
                if (brflag==1) {
                    Log.d("Selected item ", answer_string);
                    if (ques_text.isEmpty()) ques_text = "text";
                    if (qus_image.isEmpty()) qus_image = "image";
                    HashMap<String, String> quesdata = new HashMap<>();
                    quesdata.put("ques_id", ques_id);
                    quesdata.put("ques_text", ques_text);
                    quesdata.put("qus_image", qus_image);
                    quesdata.put("ques_type", ques_type);
                    quesdata.put("ques_answer", answer_string);
                    //dataList.add(quesdata);
                    dataList.set(brpos, quesdata);
                } else {
                    Log.d("Selected item ", answer_string);
                    if (ques_text.isEmpty()) ques_text = "text";
                    if (qus_image.isEmpty()) qus_image = "image";
                    HashMap<String, String> quesdata = new HashMap<>();
                    quesdata.put("ques_id", ques_id);
                    quesdata.put("ques_text", ques_text);
                    quesdata.put("qus_image", qus_image);
                    quesdata.put("ques_type", ques_type);
                    quesdata.put("ques_answer", answer_string);
                    dataList.add(quesdata);
                }
            } else {
                Log.d("Selected item ", answer_string);
                if (ques_text.isEmpty()) ques_text = "text";
                if (qus_image.isEmpty()) qus_image = "image";
                HashMap<String, String> quesdata = new HashMap<>();
                quesdata.put("ques_id", ques_id);
                quesdata.put("ques_text", ques_text);
                quesdata.put("qus_image", qus_image);
                quesdata.put("ques_type", ques_type);
                quesdata.put("ques_answer", answer_string);
                dataList.add(quesdata);
            }
        }
    }

    private void startTimer() {
        mcountDownTimer = new CountDownTimer(mTimeLeftInSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInSeconds = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int min = (int) (mTimeLeftInSeconds / 1000) / 60;
        int sec = (int) (mTimeLeftInSeconds / 1000) % 60;
        Log.d("leftTime : ", String.valueOf(mTimeLeftInSeconds));
        Log.d("timerRunning : ", String.valueOf(mTimerRunning));
        Log.d("minute timer : ", String.valueOf(min));
        Log.d("second timer : ", String.valueOf(sec));
        mTimeCountInSeconds++;
        String timeLeftFormated = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        question_timer.setText(timeLeftFormated);
        if (min == 0 && sec == 1) {
            builder = new AlertDialog.Builder(QuestionActivity.this);
            builder.setTitle("00:00");
            builder.setMessage("Duration Complete, press OK to submit your answer");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getApplicationContext(), "Yes Selected", Toast.LENGTH_SHORT).show();
                    saveAnswered();
                }
            });

//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
//                }
//            });

            builder.show();
            mcountDownTimer.cancel();
        }
    }

    private void saveAnswered() {
        //String url = "https://learnersmall.in/android/api/v2/question/answer";
        String url = "https://learnersmall.in/android/api/v2/exam-submit";
        RequestParams params = new RequestParams();
        params.put("exam", exam_id);
        params.put("student", student_id);
        params.put("exam_time", mTimeCountInSeconds);
        if (dataList.size() > 0) {
            params.put("answer", dataList);
        } else {
            params.put("answer", "");
        }

        mcountDownTimer.cancel();

        Log.d("exam : ", exam_id);
        Log.d("student : ", student_id);
        Log.d("exam_time : ", String.valueOf(mTimeCountInSeconds));
        Log.d("answer : ", String.valueOf(dataList));
        Log.d("data_list_size : ", String.valueOf(dataList.size()));
        for (int i = 0; i < dataList.size(); i++) {
            HashMap<String, String> song = new HashMap<String, String>();
            song = dataList.get(i);
            Log.d("ques_id ", song.get("ques_id"));
            Log.d("ques_text ", song.get("ques_text"));
            Log.d("ques_type ", song.get("ques_type"));
            Log.d("ques_answer ", song.get("ques_answer"));
        }

        WebServiceController wc = new WebServiceController(QuestionActivity.this,QuestionActivity.this);
        wc.sendRequest(url, params,4);

//        Toast.makeText(getApplicationContext(), "Thank you, you can get your result shortly.", Toast.LENGTH_LONG).show();
//        examStartSesManager.clearEStartSession();
//        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
//        startActivity(intent);
//        finish();
    }

    private void prepareJsonLayout () {
        try {
            if (jsonArray.length()>0) {

                if (pclick_btn == 1) {
                    //Clicked on Next Button
                    ans_st_arr[screen_i - 1] = "1";
                } else if (pclick_btn == 2) {
                    //Clicked on Skip Button
                    if (ans_st_arr[screen_i - 1].equalsIgnoreCase("0")) {
                        ans_st_arr[screen_i - 1] = "2";
                    }
                }

                no_ques = jsonArray.length();
                no_question.setText(String.valueOf(screen_i + 1) + "/" + String.valueOf(no_ques));
                if (screen_i + 1 == no_ques) {
                    next_button.setVisibility(View.VISIBLE);
                    next_button.setEnabled(false);
                    prev_button.setVisibility(View.VISIBLE);
                    if (screen_i + 1 == 1) {
                        prev_button.setEnabled(false);
                    } else {
                        prev_button.setEnabled(true);
                    }
                    skip_button.setVisibility(View.GONE);
                    save_button.setVisibility(View.VISIBLE);
                } else {
                    next_button.setVisibility(View.VISIBLE);
                    next_button.setEnabled(true);
                    prev_button.setVisibility(View.VISIBLE);
                    if (screen_i + 1 == 1) {
                        prev_button.setEnabled(false);
                    } else {
                        prev_button.setEnabled(true);
                    }
                    skip_button.setVisibility(View.VISIBLE);
                    save_button.setVisibility(View.GONE);
                }
                JSONObject jsonObject = jsonArray.getJSONObject(screen_i);
                ques_id = jsonObject.getString("id").trim();
                ques_ip_type = jsonObject.getString("ques_ip_type").trim();
                ques_text = jsonObject.getString("qus").trim();
                qus_image = jsonObject.getString("qus_image").trim();
                ques_type = jsonObject.getString("type").trim();
                ques_status = jsonObject.getString("state").trim();
                if (ques_status.equalsIgnoreCase("1")) {
                    question_id.setText(ques_id);

                    if (ques_type.equalsIgnoreCase("qusen")) {
                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
                        answer_text.setVisibility(View.VISIBLE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.GONE);
                    } else if (ques_type.equalsIgnoreCase("check")) {
                        qblock1 = 0;
                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.VISIBLE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.GONE);

                        String old_ch_ans = "";
                        String[] old_ch_arr;
                        if (dataList.size() > 0) {
                            for (int i = 0; i < dataList.size(); i++) {
                                HashMap<String, String> song = new HashMap<String, String>();
                                song = dataList.get(i);
                                if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                                    old_ch_ans = song.get("ques_answer");
                                    break;
                                }
                            }
                            checkb_answer = old_ch_ans;
                            old_ch_arr = old_ch_ans.split(",");
                            checkbox_layout.removeAllViews();
                            if (qblock1 == 0) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    String option_string = jsonArray1.getString(i).trim();
                                    checkBox = new CheckBox(QuestionActivity.this);
                                    checkBox.setId(i + 1);
                                    checkBox.setText(option_string);
                                    checkBox.setOnClickListener(getOnClickDoSomething(checkBox));
                                    for (int op = 0; op < old_ch_arr.length; op++) {
                                        if (old_ch_arr[op].equalsIgnoreCase(String.valueOf(i + 1))) {
                                            checkBox.setChecked(true);
                                        }
                                    }
                                    checkbox_layout.addView(checkBox);
                                }
                                qblock1 = 1;
                            }
                        } else {
                            checkbox_layout.removeAllViews();
                            if (qblock1 == 0) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    String option_string = jsonArray1.getString(i).trim();
                                    checkBox = new CheckBox(QuestionActivity.this);
                                    checkBox.setId(i + 1);
                                    checkBox.setText(option_string);
                                    checkBox.setOnClickListener(getOnClickDoSomething(checkBox));
                                    checkbox_layout.addView(checkBox);
                                }
                                qblock1 = 1;
                            }
                        }

                    } else if (ques_type.equalsIgnoreCase("radio")) {
                        qblock2 = 0;
                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.VISIBLE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.GONE);

                        if (qblock2 == 0) {
                            String old_ch_ans = "";
                            if (dataList.size() > 0) {
                                for (int i = 0; i < dataList.size(); i++) {
                                    HashMap<String, String> song = new HashMap<String, String>();
                                    song = dataList.get(i);
                                    if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                                        old_ch_ans = song.get("ques_answer");
                                        break;
                                    }
                                }
                                radiob_answer = old_ch_ans;
                            }

                            radiobutton_layout.removeAllViews();
                            JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                            if (jsonArray1.length() > 0) {
                                radiobutton_layout = new RadioGroup(QuestionActivity.this);
                            }
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                String option_string = jsonArray1.getString(i).trim();
                                radioButton = new RadioButton(QuestionActivity.this);
                                radioButton.setId(i + 1);
                                radioButton.setText(option_string);
                                radioButton.setOnClickListener(getOnClickRDoSomething(radioButton));
                                if (!old_ch_ans.equalsIgnoreCase("")) {
                                    if (old_ch_ans.equalsIgnoreCase(String.valueOf(i + 1))) {
                                        radioButton.setChecked(true);
                                    }
                                }
                                radiobutton_layout.addView(radioButton);
                            }
                            ((ViewGroup) findViewById(R.id.radiobutton_layout)).removeAllViews();
                            ((ViewGroup) findViewById(R.id.radiobutton_layout)).addView(radiobutton_layout);
                            qblock2 = 1;
                        }
                    } else if (ques_type.equalsIgnoreCase("accounting1")) {
                        qblock3 = 0;
                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.VISIBLE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.GONE);

                        if (qblock3 == 0) {
                            String old_ch_ans = "";
                            String[] old_ch_arr;
                            if (dataList.size() > 0) {
                                for (int i = 0; i < dataList.size(); i++) {
                                    HashMap<String, String> song = new HashMap<String, String>();
                                    song = dataList.get(i);
                                    if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                                        old_ch_ans = song.get("ques_answer");
                                        break;
                                    }
                                }
                                radiob_answer1 = old_ch_ans;
                            }

                            final JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                            if (jsonArray1.length() > 0) {
                                asradiobutton_layout = new RadioGroup(QuestionActivity.this);
                                liaradiobutton_layout = new RadioGroup(QuestionActivity.this);
                                eqradiobutton_layout = new RadioGroup(QuestionActivity.this);
                            }

                            //asradiobutton_layout.removeAllViews();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                String option_string = jsonArray1.getString(i).trim();
                                radioButton1 = new RadioButton(QuestionActivity.this);
                                radioButton1.setId(i + 1);
                                if (!old_ch_ans.equalsIgnoreCase("")) {
                                    old_ch_arr = old_ch_ans.split(",");
                                    if (old_ch_arr[0].equalsIgnoreCase(String.valueOf(i + 1))) {
                                        if (i == 0) {
                                            radioButton1.setButtonDrawable(R.drawable.arrow12);
                                        } else if (i == 1) {
                                            radioButton1.setButtonDrawable(R.drawable.arrow22);
                                        } else if (i == 2) {
                                            radioButton1.setButtonDrawable(R.drawable.arrow32);
                                        }
                                    } else {
                                        if (i == 0) {
                                            radioButton1.setButtonDrawable(R.drawable.arrow11);
                                        } else if (i == 1) {
                                            radioButton1.setButtonDrawable(R.drawable.arrow21);
                                        } else if (i == 2) {
                                            radioButton1.setButtonDrawable(R.drawable.arrow31);
                                        }
                                    }
                                } else {
                                    if (i == 0) {
                                        radioButton1.setButtonDrawable(R.drawable.arrow11);
                                    } else if (i == 1) {
                                        radioButton1.setButtonDrawable(R.drawable.arrow21);
                                    } else if (i == 2) {
                                        radioButton1.setButtonDrawable(R.drawable.arrow31);
                                    }
                                }
                                radioButton1.setPadding(20, 20, 20, 20);
                                //radioButton1.setText(option_string);
                                radioButton1.setOnClickListener(getOnClickARDoSomething(radioButton1, 1));
                                asradiobutton_layout.addView(radioButton1);
                            }
                            ((ViewGroup) findViewById(R.id.asradiobutton_layout)).removeAllViews();
                            ((ViewGroup) findViewById(R.id.asradiobutton_layout)).addView(asradiobutton_layout);

                            asradiobutton_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    System.out.println("custom radio button checked : " + checkedId);
                                    asradiobutton_layout.removeAllViews();
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        radioButton1 = new RadioButton(QuestionActivity.this);
                                        radioButton1.setId(i + 1);
                                        if (i == 0) {
                                            if (i + 1 == checkedId) {
                                                radioButton1.setButtonDrawable(R.drawable.arrow12);
                                            } else {
                                                radioButton1.setButtonDrawable(R.drawable.arrow11);
                                            }
                                        } else if (i == 1) {
                                            if (i + 1 == checkedId) {
                                                radioButton1.setButtonDrawable(R.drawable.arrow22);
                                            } else {
                                                radioButton1.setButtonDrawable(R.drawable.arrow21);
                                            }
                                        } else if (i == 2) {
                                            if (i + 1 == checkedId) {
                                                radioButton1.setButtonDrawable(R.drawable.arrow32);
                                            } else {
                                                radioButton1.setButtonDrawable(R.drawable.arrow31);
                                            }
                                        }
                                        radioButton1.setPadding(20, 20, 20, 20);
                                        //radioButton1.setText(option_string);
                                        radioButton1.setOnClickListener(getOnClickARDoSomething(radioButton1, 1));
                                        asradiobutton_layout.addView(radioButton1);
                                    }
                                    ((ViewGroup) findViewById(R.id.asradiobutton_layout)).removeAllViews();
                                    ((ViewGroup) findViewById(R.id.asradiobutton_layout)).addView(asradiobutton_layout);
                                }
                            });

                            //liaradiobutton_layout.removeAllViews();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                String option_string = jsonArray1.getString(i).trim();
                                radioButton2 = new RadioButton(QuestionActivity.this);
                                radioButton2.setId(i + 1);
                                if (!old_ch_ans.equalsIgnoreCase("")) {
                                    old_ch_arr = old_ch_ans.split(",");
                                    if (old_ch_arr[1].equalsIgnoreCase(String.valueOf(i + 1))) {
                                        if (i == 0) {
                                            radioButton2.setButtonDrawable(R.drawable.arrow12);
                                        } else if (i == 1) {
                                            radioButton2.setButtonDrawable(R.drawable.arrow22);
                                        } else if (i == 2) {
                                            radioButton2.setButtonDrawable(R.drawable.arrow32);
                                        }
                                    } else {
                                        if (i == 0) {
                                            radioButton2.setButtonDrawable(R.drawable.arrow11);
                                        } else if (i == 1) {
                                            radioButton2.setButtonDrawable(R.drawable.arrow21);
                                        } else if (i == 2) {
                                            radioButton2.setButtonDrawable(R.drawable.arrow31);
                                        }
                                    }
                                } else {
                                    if (i == 0) {
                                        radioButton2.setButtonDrawable(R.drawable.arrow11);
                                    } else if (i == 1) {
                                        radioButton2.setButtonDrawable(R.drawable.arrow21);
                                    } else if (i == 2) {
                                        radioButton2.setButtonDrawable(R.drawable.arrow31);
                                    }
                                }
                                radioButton2.setPadding(20, 20, 20, 20);
                                //radioButton2.setText(option_string);
                                radioButton2.setOnClickListener(getOnClickARDoSomething(radioButton2, 2));
                                liaradiobutton_layout.addView(radioButton2);
                            }
                            ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).removeAllViews();
                            ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).addView(liaradiobutton_layout);

                            liaradiobutton_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    System.out.println("custom radio button checked : " + checkedId);
                                    liaradiobutton_layout.removeAllViews();
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        radioButton2 = new RadioButton(QuestionActivity.this);
                                        radioButton2.setId(i + 1);
                                        if (i == 0) {
                                            if (i + 1 == checkedId) {
                                                radioButton2.setButtonDrawable(R.drawable.arrow12);
                                            } else {
                                                radioButton2.setButtonDrawable(R.drawable.arrow11);
                                            }
                                        } else if (i == 1) {
                                            if (i + 1 == checkedId) {
                                                radioButton2.setButtonDrawable(R.drawable.arrow22);
                                            } else {
                                                radioButton2.setButtonDrawable(R.drawable.arrow21);
                                            }
                                        } else if (i == 2) {
                                            if (i + 1 == checkedId) {
                                                radioButton2.setButtonDrawable(R.drawable.arrow32);
                                            } else {
                                                radioButton2.setButtonDrawable(R.drawable.arrow31);
                                            }
                                        }
                                        radioButton2.setPadding(20, 20, 20, 20);
                                        //radioButton1.setText(option_string);
                                        radioButton2.setOnClickListener(getOnClickARDoSomething(radioButton2, 2));
                                        liaradiobutton_layout.addView(radioButton2);
                                    }
                                    ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).removeAllViews();
                                    ((ViewGroup) findViewById(R.id.liaradiobutton_layout)).addView(liaradiobutton_layout);
                                }
                            });

                            //eqradiobutton_layout.removeAllViews();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                String option_string = jsonArray1.getString(i).trim();
                                radioButton3 = new RadioButton(QuestionActivity.this);
                                radioButton3.setId(i + 1);
                                if (!old_ch_ans.equalsIgnoreCase("")) {
                                    old_ch_arr = old_ch_ans.split(",");
                                    if (old_ch_arr[2].equalsIgnoreCase(String.valueOf(i + 1))) {
                                        if (i == 0) {
                                            radioButton3.setButtonDrawable(R.drawable.arrow12);
                                        } else if (i == 1) {
                                            radioButton3.setButtonDrawable(R.drawable.arrow22);
                                        } else if (i == 2) {
                                            radioButton3.setButtonDrawable(R.drawable.arrow32);
                                        }
                                    } else {
                                        if (i == 0) {
                                            radioButton3.setButtonDrawable(R.drawable.arrow11);
                                        } else if (i == 1) {
                                            radioButton3.setButtonDrawable(R.drawable.arrow21);
                                        } else if (i == 2) {
                                            radioButton3.setButtonDrawable(R.drawable.arrow31);
                                        }
                                    }
                                } else {
                                    if (i == 0) {
                                        radioButton3.setButtonDrawable(R.drawable.arrow11);
                                    } else if (i == 1) {
                                        radioButton3.setButtonDrawable(R.drawable.arrow21);
                                    } else if (i == 2) {
                                        radioButton3.setButtonDrawable(R.drawable.arrow31);
                                    }
                                }
                                radioButton3.setPadding(20, 20, 20, 20);
                                //radioButton3.setText(option_string);
                                radioButton3.setOnClickListener(getOnClickARDoSomething(radioButton3, 3));
                                eqradiobutton_layout.addView(radioButton3);
                            }
                            ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).removeAllViews();
                            ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).addView(eqradiobutton_layout);

                            eqradiobutton_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    System.out.println("custom radio button checked : " + checkedId);
                                    eqradiobutton_layout.removeAllViews();
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        radioButton3 = new RadioButton(QuestionActivity.this);
                                        radioButton3.setId(i + 1);
                                        if (i == 0) {
                                            if (i + 1 == checkedId) {
                                                radioButton3.setButtonDrawable(R.drawable.arrow12);
                                            } else {
                                                radioButton3.setButtonDrawable(R.drawable.arrow11);
                                            }
                                        } else if (i == 1) {
                                            if (i + 1 == checkedId) {
                                                radioButton3.setButtonDrawable(R.drawable.arrow22);
                                            } else {
                                                radioButton3.setButtonDrawable(R.drawable.arrow21);
                                            }
                                        } else if (i == 2) {
                                            if (i + 1 == checkedId) {
                                                radioButton3.setButtonDrawable(R.drawable.arrow32);
                                            } else {
                                                radioButton3.setButtonDrawable(R.drawable.arrow31);
                                            }
                                        }
                                        radioButton3.setPadding(20, 20, 20, 20);
                                        //radioButton1.setText(option_string);
                                        radioButton3.setOnClickListener(getOnClickARDoSomething(radioButton3, 3));
                                        eqradiobutton_layout.addView(radioButton3);
                                    }
                                    ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).removeAllViews();
                                    ((ViewGroup) findViewById(R.id.eqradiobutton_layout)).addView(eqradiobutton_layout);
                                }
                            });

                            qblock3 = 1;
                        }
                    } else if (ques_type.equalsIgnoreCase("accounting2")) {
                        String old_ch_ans = "";
                        String acf_flag2 = "0";
                        drtot1 = 0.0;
                        crtot1 = 0.0;
                        dr_tot_val.setText(String.valueOf(drtot1));
                        cr_tot_val.setText(String.valueOf(crtot1));
                        if (dataList.size() > 0) {
                            for (int i = 0; i < dataList.size(); i++) {
                                HashMap<String, String> song = new HashMap<String, String>();
                                song = dataList.get(i);
                                if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                                    old_ch_ans = song.get("ques_answer");
                                    acf_flag2 = "1";
                                    break;
                                } else acf_flag2 = "0";
                            }
                        }

                        if (acf_flag2.equalsIgnoreCase("1")) {
                            removeJournal();
                            String old_ch_ans_a21 = old_ch_ans.substring(1);
                            String old_ch_ans_a22 = old_ch_ans_a21.substring(0, old_ch_ans_a21.length() - 1);
                            if (old_ch_ans_a22.indexOf("},{") != -1) {
                                String[] old_ch_arr1 = old_ch_ans_a22.split(Pattern.quote("}"));
                                for (int l2 = 0; l2 < old_ch_arr1.length; l2++) {
                                    String a2_str = old_ch_arr1[l2];
                                    String a2_str1;
                                    if (l2 == 0) {
                                        a2_str1 = a2_str.substring(1);
                                    } else {
                                        a2_str1 = a2_str.substring(2);
                                    }
                                    //String a2_str2 = a2_str1.substring(0, a2_str1.length() - 1);
                                    //Log.e("arrstr : ", a2_str1);
                                    String[] a2_str_arr = a2_str1.split(",");

                                    String drval = "";
                                    String crval = "";

                                    if (a2_str_arr[4].equalsIgnoreCase("1")) {
                                        drval = a2_str_arr[3];
                                        drtot1 += Double.parseDouble(drval);
                                    } else {
                                        crval = a2_str_arr[3];
                                        crtot1 += Double.parseDouble(crval);
                                    }

                                    String backaccname = "";
                                    if ( dataList2.size() > 0 ) {
                                        for (int j2 = 0; j2 < dataList2.size(); j2++) {
                                            HashMap<String, String> bsong1 = new HashMap<String, String>();
                                            bsong1 = dataList2.get(j2);
                                            if (bsong1.get("aacc_id").equalsIgnoreCase(a2_str_arr[2])) {
                                                backaccname = bsong1.get("accname");
                                                break;
                                            }
                                        }
                                    }

                                    acc_subans2 = "";
                                    acc_subans2 += a2_str_arr[0] + "," + a2_str_arr[1] + "," + a2_str_arr[2] + "," + a2_str_arr[3] + "," + a2_str_arr[4];
                                    //acc_ans2 = new ArrayList<>();
                                    acc_ans2.add(acc_subans2);

                                    HashMap<String, String> catdata1 = new HashMap<>();
                                    catdata1.put(TAG_AC_NAME, backaccname);
                                    catdata1.put(TAG_DR_VAL, drval);
                                    catdata1.put(TAG_CR_VAL, crval);
                                    dataList1.add(catdata1);

                                    accountingListAdapter = new AccountingListAdapter(QuestionActivity.this, dataList1);
                                    acc_data2.setAdapter(accountingListAdapter);

                                    listViewHeight.setListViewHeightBasedOnChildren(acc_data2);
                                }

                                dr_tot_val.setText(String.valueOf(drtot1));
                                cr_tot_val.setText(String.valueOf(crtot1));
                            }
                        } else {
                            removeJournal();
                        }

                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.VISIBLE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.GONE);

                        ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, paccspinnerArray);
                        adapter1.setDropDownViewResource(R.layout.spinner_item_1);
                        spinner_paccount.setAdapter(adapter1);
                        spinner_paccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                pacc_name = spinner_paccount.getSelectedItem().toString();
                                pacc_id = paccountMap.get(spinner_paccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                        adapter2.setDropDownViewResource(R.layout.spinner_item_1);
                        spinner_saccount.setAdapter(adapter2);
                        spinner_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                sacc_name = spinner_saccount.getSelectedItem().toString();
                                sacc_id = saccountMap.get(spinner_saccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        getAccount1(ques_id);

//                        ArrayAdapter<String> adapter3 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, accspinnerArray);
//                        adapter3.setDropDownViewResource(R.layout.spinner_item_1);
//                        spinner_account.setAdapter(adapter3);
//                        spinner_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                aacc_name = spinner_account.getSelectedItem().toString();
//                                aacc_id = accountMap.get(spinner_account.getSelectedItemPosition());
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
                    } else if (ques_type.equalsIgnoreCase("accounting3")) {
                        Log.d("Image url", qus_image);
                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            Log.d("Image url", qus_image);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
//                        question_text.setText(String.valueOf(screen_i + 1) + ". ");
//                        question_image.setVisibility(View.VISIBLE);
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.VISIBLE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.GONE);

                        JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                        acctypeArray = new String[jsonArray1.length() + 1];
                        acctypeMap = new HashMap<Integer, String>();

                        acctypeMap.put(0, "0");
                        acctypeArray[0] = "Select Account Type";
                        int j = 0;
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            String option_string = jsonArray1.getString(i).trim();
                            j = i + 1;
                            //Log.d("j value", String.valueOf(j));
                            acctypeMap.put(j, String.valueOf(j));
                            acctypeArray[j] = option_string;
                        }

                        ArrayAdapter<String> adapter4 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, acctypeArray);
                        adapter4.setDropDownViewResource(R.layout.spinner_item_1);
                        spinner_account_type.setAdapter(adapter4);
                        if (dataList.size() > 0) {
                            int old_ch_ans = 0;
                            for (int i = 0; i < dataList.size(); i++) {
                                HashMap<String, String> song = new HashMap<String, String>();
                                song = dataList.get(i);
                                if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                                    Log.d("Selected answer : ", song.get("ques_answer"));
                                    old_ch_ans = Integer.parseInt(song.get("ques_answer"));
                                    spinner_account_type.setSelection(old_ch_ans);
                                    break;
                                }
                            }
                        }
                        spinner_account_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                aacc_type_name = spinner_account_type.getSelectedItem().toString();
                                aacc_type_id = acctypeMap.get(spinner_account_type.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else if (ques_type.equalsIgnoreCase("accounting4")) {
                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.VISIBLE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.GONE);

                        ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                        adapter1.setDropDownViewResource(R.layout.spinner_item_1);
                        as_spi_saccount.setAdapter(adapter1);
                        as_spi_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                as_saci_name = as_spi_saccount.getSelectedItem().toString();
                                as_saci_id = saccountMap.get(as_spi_saccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                        adapter2.setDropDownViewResource(R.layout.spinner_item_1);
                        as_spd_saccount.setAdapter(adapter2);
                        as_spd_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                as_sacd_name = as_spd_saccount.getSelectedItem().toString();
                                as_saci_id = saccountMap.get(as_spd_saccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter3 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                        adapter3.setDropDownViewResource(R.layout.spinner_item_1);
                        li_spi_saccount.setAdapter(adapter3);
                        li_spi_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                li_saci_name = li_spi_saccount.getSelectedItem().toString();
                                li_saci_id = saccountMap.get(li_spi_saccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter7 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                        adapter7.setDropDownViewResource(R.layout.spinner_item_1);
                        li_spi_reason.setAdapter(adapter7);
                        li_spi_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                li_sacir_name = li_spi_reason.getSelectedItem().toString();
                                li_sacir_id = reasonMap.get(li_spi_reason.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter4 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                        adapter4.setDropDownViewResource(R.layout.spinner_item_1);
                        li_spd_saccount.setAdapter(adapter4);
                        li_spd_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                li_sacd_name = li_spd_saccount.getSelectedItem().toString();
                                li_sacd_id = saccountMap.get(li_spd_saccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter8 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                        adapter8.setDropDownViewResource(R.layout.spinner_item_1);
                        li_spd_reason.setAdapter(adapter8);
                        li_spd_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                li_sacdr_name = li_spd_reason.getSelectedItem().toString();
                                li_sacdr_id = reasonMap.get(li_spd_reason.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter5 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                        adapter5.setDropDownViewResource(R.layout.spinner_item_1);
                        eq_spi_saccount.setAdapter(adapter5);
                        eq_spi_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                eq_saci_name = eq_spi_saccount.getSelectedItem().toString();
                                eq_saci_id = saccountMap.get(eq_spi_saccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter9 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                        adapter9.setDropDownViewResource(R.layout.spinner_item_1);
                        eq_spi_reason.setAdapter(adapter9);
                        eq_spi_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                eq_sacir_name = eq_spi_reason.getSelectedItem().toString();
                                eq_sacir_id = reasonMap.get(eq_spi_reason.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter6 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, saccspinnerArray);
                        adapter6.setDropDownViewResource(R.layout.spinner_item_1);
                        eq_spd_saccount.setAdapter(adapter6);
                        eq_spd_saccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                eq_sacd_name = eq_spd_saccount.getSelectedItem().toString();
                                eq_sacd_id = saccountMap.get(eq_spd_saccount.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        ArrayAdapter<String> adapter10 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_1, reasonspinnerArray);
                        adapter10.setDropDownViewResource(R.layout.spinner_item_1);
                        eq_spd_reason.setAdapter(adapter10);
                        eq_spd_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                eq_sacdr_name = eq_spd_reason.getSelectedItem().toString();
                                eq_sacdr_id = reasonMap.get(eq_spd_reason.getSelectedItemPosition());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (ques_type.equalsIgnoreCase("accounting5")) {
                        question_text.setText(String.valueOf(screen_i + 1) + ". Select appropriate option from dropdown list………");
                        //ques_text;

                        question_image.setVisibility(View.GONE);
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.VISIBLE);
                        accounting_layout6.setVisibility(View.GONE);

                        qu5_arr.clear();
                        String[] que5_arr = ques_text.split("=><");
                        for (int l = 0; l < que5_arr.length; l++) {
                            qu5_arr.add(que5_arr[l]);
                        }

                        JSONArray jsonArray1 = jsonObject.getJSONArray("qus_option");
                        String option_string = "";
                        for (int p = 0; p < jsonArray1.length(); p++) {
                            if (option_string.isEmpty())
                                option_string += jsonArray1.getString(p).trim();
                            else
                                option_string += "=><" + jsonArray1.getString(p).trim();
                        }
                        quop5_arr.clear();
                        for (int t = 0; t < jsonArray1.length(); t++) {
                            quop5_arr.add(option_string);
                        }

                        String old_ch_ans = "";
                        if (dataList.size() > 0) {
                            for (int i = 0; i < dataList.size(); i++) {
                                HashMap<String, String> song = new HashMap<String, String>();
                                song = dataList.get(i);
                                if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                                    Log.d("Selected answer : ", song.get("ques_answer"));
                                    old_ch_ans = song.get("ques_answer");
                                    break;
                                }
                            }
                        }

                        QuestionListAdapter resultListView = new QuestionListAdapter(this, qu5_arr, quop5_arr, old_ch_ans);
                        question5_list.setAdapter(resultListView);
                    } else if (ques_type.equalsIgnoreCase("accounting6")) {
                        if (!ques_text.isEmpty()) {
                            question_text.setText(String.valueOf(screen_i + 1) + ". " + ques_text);
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        } else {
                            question_text.setText(String.valueOf(screen_i + 1) + ". ");
                            if (!qus_image.isEmpty()) {
                                question_image.setVisibility(View.VISIBLE);
                                Picasso.with(QuestionActivity.this).invalidate(qus_image);
                                Picasso.with(QuestionActivity.this).load(qus_image).into(question_image);
                            } else {
                                question_image.setVisibility(View.GONE);
                            }
                        }
                        answer_text.setVisibility(View.GONE);
                        checkbox_layout.setVisibility(View.GONE);
                        radiobutton_layout.setVisibility(View.GONE);
                        accounting_layout1.setVisibility(View.GONE);
                        accounting_layout2.setVisibility(View.GONE);
                        accounting_layout3.setVisibility(View.GONE);
                        accounting_layout4.setVisibility(View.GONE);
                        accounting_layout5.setVisibility(View.GONE);
                        accounting_layout6.setVisibility(View.VISIBLE);

                        String old_ch_ans6 = "";
                        String[] old_ch_ar6 = null;
                        String acf_flag6 = "0";
                        if (dataList.size() > 0) {
                            for (int i = 0; i < dataList.size(); i++) {
                                HashMap<String, String> song = new HashMap<String, String>();
                                song = dataList.get(i);
                                if (song.get("ques_id").equalsIgnoreCase(ques_id)) {
                                    old_ch_ans6 = song.get("ques_answer");
                                    acf_flag6 = "1";
                                    break;
                                } else acf_flag6 = "0";
                            }
                        }

                        Log.e("data_delete1 : ",acf_flag6);

                        asaclabel_incr.setChecked(false);
                        asaclabel_decr.setChecked(false);
                        asaclabel_impct.setChecked(false);

                        liaclabel_incr.setChecked(false);
                        liaclabel_decr.setChecked(false);
                        liaclabel_impct.setChecked(false);

                        inaclabel_incr.setChecked(false);
                        inaclabel_decr.setChecked(false);
                        inaclabel_impct.setChecked(false);

                        exaclabel_incr.setChecked(false);
                        exaclabel_decr.setChecked(false);
                        exaclabel_impct.setChecked(false);

                        asaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                        asaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                        asaclabel_impct.setButtonDrawable(R.drawable.arrow31);

                        liaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                        liaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                        liaclabel_impct.setButtonDrawable(R.drawable.arrow31);

                        inaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                        inaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                        inaclabel_impct.setButtonDrawable(R.drawable.arrow31);

                        exaclabel_incr.setButtonDrawable(R.drawable.arrow11);
                        exaclabel_decr.setButtonDrawable(R.drawable.arrow21);
                        exaclabel_impct.setButtonDrawable(R.drawable.arrow31);

                        asaclabel_radio = "0";
                        liaclabel_radio = "0";
                        inaclabel_radio = "0";
                        exaclabel_radio = "0";

                        if (acf_flag6.equalsIgnoreCase("1")) {
                            if (!old_ch_ans6.equalsIgnoreCase("")) {
                                Log.e("data_delete2 : ", old_ch_ans6);
                                old_ch_ar6 = old_ch_ans6.split(",");
                                for (int m = 0; m < old_ch_ar6.length; m++) {
                                    Log.e("data_delete3 : ", old_ch_ar6[m]);
                                    if (old_ch_ar6[m].equalsIgnoreCase("1")) {
                                        if (m == 0) {
                                            asaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                                            asaclabel_radio = "1";
                                        } else if (m == 1) {
                                            liaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                                            liaclabel_radio = "1";
                                        } else if (m == 2) {
                                            inaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                                            inaclabel_radio = "1";
                                        } else if (m == 3) {
                                            exaclabel_incr.setButtonDrawable(R.drawable.arrow12);
                                            exaclabel_radio = "1";
                                        }
                                    } else if (old_ch_ar6[m].equalsIgnoreCase("2")) {
                                        if (m == 0) {
                                            asaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                                            asaclabel_radio = "2";
                                        } else  if (m == 1) {
                                            liaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                                            liaclabel_radio = "2";
                                        } else if (m == 2) {
                                            inaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                                            inaclabel_radio = "2";
                                        } else if (m == 3) {
                                            exaclabel_decr.setButtonDrawable(R.drawable.arrow22);
                                            exaclabel_radio = "2";
                                        }
                                    } else if (old_ch_ar6[m].equalsIgnoreCase("3")) {
                                        if (m == 0) {
                                            asaclabel_decr.setButtonDrawable(R.drawable.arrow32);
                                            asaclabel_radio = "3";
                                        } else  if (m == 1) {
                                            liaclabel_impct.setButtonDrawable(R.drawable.arrow32);
                                            liaclabel_radio = "3";
                                        } else if (m == 2) {
                                            inaclabel_impct.setButtonDrawable(R.drawable.arrow32);
                                            inaclabel_radio = "3";
                                        } else if (m == 3) {
                                            exaclabel_impct.setButtonDrawable(R.drawable.arrow32);
                                            exaclabel_radio = "3";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeJournal() {
        drtot = 0.0;
        crtot = 0.0;
        int n = dataList1.size();
        for (int l = 0; l < n; l++) {
            dataList1.remove(0);
        }

        int m = acc_ans2.size();
        for (int l = 0; l < m; l++) {
            acc_ans2.remove(0);
        }

//        dataList1 = new ArrayList<HashMap<String,String>>();
//        acc_ans2 = new ArrayList<>();

        acc_data2.setAdapter(null);
        listViewHeight.setListViewHeightBasedOnChildren(acc_data2);
    }

    public void clearItem(int item_pos) {
        dataList1.remove(item_pos);
        acc_ans2.remove(item_pos);

        drtot = 0.0;
        crtot = 0.0;

        for (int l = 0; l < dataList1.size(); l++) {
            HashMap<String, String> song1 = new HashMap<>();
            song1 = dataList1.get(l);
            if (!(song1.get(TAG_DR_VAL)).equalsIgnoreCase("")) {
                drtot += Double.parseDouble(song1.get(TAG_DR_VAL));
            } else if (!(song1.get(TAG_CR_VAL)).equalsIgnoreCase("")) {
                crtot += Double.parseDouble(song1.get(TAG_CR_VAL));
            }
        }

        accountingListAdapter = new AccountingListAdapter(QuestionActivity.this, dataList1);
        acc_data2.setAdapter(accountingListAdapter);

        listViewHeight.setListViewHeightBasedOnChildren(acc_data2);

        dr_tot_val.setText(String.valueOf(drtot));
        cr_tot_val.setText(String.valueOf(crtot));
    }

    /*
     * Paramterized method to sort Map e.g. HashMap or Hashtable in Java
     * throw NullPointerException if Map contains null key
     */
    public static <K extends Comparable,V extends Comparable> Map<K,V> sortByKeys(Map<K,V> map){
        List<K> keys = new LinkedList<K>(map.keySet());
        Collections.sort(keys);

        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
        for(K key: keys){
            sortedMap.put(key, map.get(key));
        }

        return sortedMap;
    }
}
