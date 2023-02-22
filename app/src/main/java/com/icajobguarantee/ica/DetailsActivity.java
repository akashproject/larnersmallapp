package com.icajobguarantee.ica;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
//import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

//import com.google.android.youtube.player.YouTubePlayer;
//import com.khizar1556.mkvideoplayer.MKPlayer;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
//import com.google.firebase.auth.PhoneAuthProvider;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.vdocipher.aegis.media.ErrorDescription;

import com.vdocipher.aegis.player.VdoPlayer;
import com.vdocipher.aegis.player.VdoPlayerSupportFragment;
//import com.thefinestartist.ytpa.YouTubePlayerActivity;
//import com.thefinestartist.ytpa.enums.Orientation;
//import com.thefinestartist.ytpa.YouTubePlayerActivity;
//import com.thefinestartist.ytpa.enums.Orientation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;
import hb.xvideoplayer.MxMediaManager;
import hb.xvideoplayer.MxVideoPlayer;
import hb.xvideoplayer.MxVideoPlayerWidget;

import static com.icajobguarantee.ica.youTubeVideoIdGet.getYoutubeVideoIdFromURL;

// 94217baa6e243a1f4c0eb598497f160

public class DetailsActivity extends AppCompatActivity implements WebInterface, DownloadFile.Listener, YouTubePlayer.OnInitializedListener, VdoPlayer.InitializationListener {

    private ActionBar toolbar;
    BottomNavigationView navigation;

    TextView course_id, course_title, course_time, course_details;
    //TextView mBufferingTextView;
    RelativeLayout imageLayout;
    ImageView main_image, item_videoIcon, spinner_arrow;
    String video_id = "", originalImageUrl, list_id, list_title, cou_id, cou_title,
            sub_id, sub_title, exam_id, student_id, font_size, uriPath, l_id, l_name, l_image,
            l_details, l_created, l_video, activity_for, url_pattern = "youtu";
    LinearLayout assesment_layout, pdfView, main_layout, layVideo, vidoblock1, vidoblock2;
    ConstraintLayout root;
    Button start_btn;

    LoginSessionManager loginSessionManager;
//    Button post_button;
//    EditText comment_edit_text;
    RemotePDFViewPager remotePDFViewPager;
    PDFPagerAdapter adapter;
    PDFViewPager pdfViewPager;

    static int screen_i = 0, screen_change = 0;
    JSONArray jsonArray;

    MenuItem navigation_prev, navigation_next;
    WebView webView;
    ScrollView scroll_view;

    ImageView back_press, home_press;
    Spinner spinner_font;
    String[] fontspinnerArray;
    //WebView course_webview;

    VideoView videoView;
    ImageButton stretch_video;
    static int stopPosition;
    MediaController mediacontroller;

    //Zoom Image
    PhotoView course_image;
    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;
    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

//    YouTubePlayerSupportFragment frag;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer player;
//    String api_key = "AIzaSyAlR-PIlLFM8maby5_8d1eFIw7E_IEYvc8";

    private ProgressDialog pDialog;

    private VdoPlayerSupportFragment vdoPlayerSupportFragment;
    String otp_val, playbackInfo_val;
    VdoPlayer vdoPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
//        }

        init();

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (videoView.isPlaying()) {
//                    videoView.pause();
//                }
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want to leave this page? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), ChapterActivity.class);
                        intent.putExtra("activity_for", activity_for);
//                intent.putExtra("list_id", sub_id);
//                intent.putExtra("list_title", sub_title);
//                intent.putExtra("cou_id", cou_id);
//                intent.putExtra("cou_title", cou_title);
                        intent.putExtra("list_id", cou_id);
                        intent.putExtra("list_title", cou_title);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        home_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
            }
        });

//        prev_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                screen_i--;
//                viewJsonDataInToLayout(screen_i);
//            }
//        });
//
//        next_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                screen_i++;
//                viewJsonDataInToLayout(screen_i);
//            }
//        });

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            getData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AssesmentExamActivity.class);
                intent.putExtra("exam_id", exam_id);
                intent.putExtra("course_id", cou_id);
//                intent.putExtra("subject_id", sub_id);
                startActivity(intent);
            }
        });

//        item_videoIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.start();
//            }
//        });

//        post_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String comment = comment_edit_text.getText().toString().trim();
//                if (comment.length() == 0) {
//                    comment_edit_text.requestFocus();
//                    comment_edit_text.setError("Comments cannot be empty");
//                    return;
//                } else {
//                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
//                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
//                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
//                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
//                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
//                        //getData(comment);
//                        Toast.makeText(getApplicationContext(), "It is temporarily down due to API maintainance!!!", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });

    }

    private void init() {
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        HashMap<String, String> loginSessionDetails = loginSessionManager.getLoginSessionDetails();
        student_id = loginSessionDetails.get(LoginSessionManager.KEY_USERID_SES);

        screen_i = 0;
        screen_change = 0;
        root = (ConstraintLayout) findViewById(R.id.remote_pdf_root);

//        header_title = (TextView) findViewById(R.id.header_title);
        back_press = (ImageView) findViewById(R.id.back_press);
        home_press = (ImageView) findViewById(R.id.home_press);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        imageLayout = (RelativeLayout) findViewById(R.id.imageLayout);

        vidoblock1 = (LinearLayout) findViewById(R.id.vidoblock1);
        vidoblock2 = (LinearLayout) findViewById(R.id.vidoblock2);

//        main_image = (ImageView) findViewById(R.id.main_image);
//        item_videoIcon = (ImageView) findViewById(R.id.item_videoIcon);
//        main_image.setVisibility(View.GONE);
//        item_videoIcon.setVisibility(View.GONE);

//        frag = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_fragment);

        vdoPlayerSupportFragment = (VdoPlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.vdo_player_fragment);


        layVideo = (LinearLayout) findViewById(R.id.layVideo);
        layVideo.setVisibility(View.GONE);

        course_id = (TextView) findViewById(R.id.course_id);
        course_title = (TextView) findViewById(R.id.course_title);
        course_time = (TextView) findViewById(R.id.course_time);
        course_time.setVisibility(View.GONE);
        course_details = (TextView) findViewById(R.id.course_details);
        start_btn = (Button) findViewById(R.id.start_btn);
        start_btn.setVisibility(View.GONE);
        course_image = (PhotoView) findViewById(R.id.course_image);
        assesment_layout = (LinearLayout) findViewById(R.id.assesment_layout);



//        mBufferingTextView = (TextView)findViewById(R.id.buffering_textview);
//        pdfViewPager = (PDFViewPager) findViewById(R.id.pdfViewPager);
//        pdfView = (LinearLayout) findViewById(R.id.pdfView);
//        pdfView.setVisibility(View.GONE);

//        button_layout = (LinearLayout) findViewById(R.id.button_layout);
//        prev_button = (Button) findViewById(R.id.prev_button);
//        next_button = (Button) findViewById(R.id.next_button);

//        comment_edit_text = (EditText) findViewById(R.id.comment_edit_text);
//        post_button = (Button) findViewById(R.id.post_button);

        Intent intent = getIntent();
        activity_for = intent.getStringExtra("activity_for");
        list_id = intent.getStringExtra("list_id");
        list_title = intent.getStringExtra("list_title");
        cou_id = intent.getStringExtra("cou_id");
        cou_title = intent.getStringExtra("cou_title");
//        sub_id = intent.getStringExtra("sub_id");
//        sub_title = intent.getStringExtra("sub_title");

//        Log.d("intent_data_value : ", list_id + ", " + list_title + ", " + cou_id + ", " + cou_title + ", " + sub_id + ", " + sub_title);

        //header_title.setText(list_title);

//        String ctitle = intent.getStringExtra("ctitle");
//        String cdetails = intent.getStringExtra("cdetails");
//        String ctime = intent.getStringExtra("ctime");
//        String cimage = intent.getStringExtra("cimage");
//        String cvideo = intent.getStringExtra("cvideo");
//
//        course_title.setText(ctitle);
//

        toolbar = getSupportActionBar();

        navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        navigation_prev = menu.findItem(R.id.navigation_prev);
        navigation_next = menu.findItem(R.id.navigation_next);

        navigation_prev.setEnabled(false);
        navigation_next.setEnabled(false);

        spinner_arrow = (ImageView) findViewById(R.id.spinner_arrow);
        spinner_font = (Spinner) findViewById(R.id.spinner_font);
        fontspinnerArray = new String[3];
        fontspinnerArray[0] = "Medium";
        fontspinnerArray[1] = "Small";
        fontspinnerArray[2] = "Large";

        ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, fontspinnerArray);
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        spinner_font.setAdapter(adapter1);
        spinner_font.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                font_size = spinner_font.getSelectedItem().toString();

                if (font_size.equalsIgnoreCase("Small")) {
                    course_title.setTextSize(14);
                    course_time.setTextSize(12);
                    course_details.setTextSize(14);
                } else if (font_size.equalsIgnoreCase("Medium")) {
                    course_title.setTextSize(18);
                    course_time.setTextSize(16);
                    course_details.setTextSize(18);
                } else if (font_size.equalsIgnoreCase("Large")) {
                    course_title.setTextSize(22);
                    course_time.setTextSize(20);
                    course_details.setTextSize(22);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        course_webview  = (WebView) findViewById(R.id.course_webview);
        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
        webView = (WebView) findViewById(R.id.webView);
        webView.setVisibility(View.GONE);

        videoView = (VideoView) findViewById(R.id.videoView);
        vidoblock1.setVisibility(View.GONE);
        vidoblock2.setVisibility(View.GONE);
        stretch_video = (ImageButton) findViewById(R.id.stretch_video);
        stretch_video.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

//    public void videoTapped(View view) {
//        //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=" + video_id)));
//        Intent intent = new Intent(DetailsActivity.this, YouTubePlayerActivity.class);
//
//        // Youtube video ID (Required, You can use YouTubeUrlParser to parse Video Id from url)
//        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, video_id);
//
//        // Youtube player style (DEFAULT as default)
//        intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, YouTubePlayer.PlayerStyle.DEFAULT);
//
//        // Screen Orientation Setting (AUTO for default)
//        // AUTO, AUTO_START_WITH_LANDSCAPE, ONLY_LANDSCAPE, ONLY_PORTRAIT
//        intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.AUTO_START_WITH_LANDSCAPE);
//
//        // Show audio interface when user adjust volume (true for default)
//        intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);
//
//        // If the video is not playable, use Youtube app or Internet Browser to play it
//        // (true for default)
//        intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);
//
//        // Animation when closing youtubeplayeractivity (none for default)
//        intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.fade_in);
//        intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.fade_out);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    private void getData(){
        //Log.d("Go for ", "Get Data " + name);
        //qfor = "all_course";
        String url = "https://learnersmall.in/android/api/v2/details";
        RequestParams params = new RequestParams();
        params.put("chapter", list_id);

        Log.d("params: ", String.valueOf(params));

        WebServiceController wc = new WebServiceController(DetailsActivity.this,DetailsActivity.this);
        wc.sendRequest(url, params,0);
    }

    private Pair<String, String> getExampleOtpAndPlaybackInfo() {
        return Pair.create(otp_val, playbackInfo_val);
    }

    @Override
    public void getResponse(String response, int flag) {
        if (flag == 0) {
            try {
                Log.e("json_result", response);
                jsonArray = new JSONArray(response);
                if (jsonArray.length()>0) {
                    viewJsonDataInToLayout(screen_i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    public void viewJsonDataInToLayout(final int screen_i) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(screen_i);
            if (jsonArray.length() > 1) {
                if (screen_i + 1 == jsonArray.length()) {
                    navigation_next.setEnabled(false);
                } else {
                    navigation_next.setEnabled(true);
                }
                if (screen_i == 0) {
                    navigation_prev.setEnabled(false);
                } else {
                    navigation_prev.setEnabled(true);
                }
            } else {
                navigation_prev.setEnabled(false);
                navigation_next.setEnabled(false);
            }

            l_id = "";
            l_name = "";
            l_image = "";
            l_details = "";
            l_created = "";
            l_video = "";

            l_id = jsonObject.getString("id").trim();
            exam_id = jsonObject.getString("exam_id").trim();
            l_name = jsonObject.getString("details_titel").trim();
            l_image = jsonObject.getString("details_img").trim();
            System.out.println("Details text : " + jsonObject.getString("details_text"));
            if (!jsonObject.getString("details_text").isEmpty()) {
                l_details = jsonObject.getString("details_text").trim();
            }
            l_created = jsonObject.getString("created_at").trim();
            l_video = jsonObject.getString("details_video").trim();
            String item_type = jsonObject.getString("type").trim();

            updateReadStatus(l_id);

            spinner_font.setVisibility(View.VISIBLE);
            spinner_arrow.setVisibility(View.VISIBLE);
            scroll_view.setVisibility(View.VISIBLE);
            assesment_layout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
//            pdfView.setVisibility(View.GONE);
            course_id.setText(l_id);
            course_title.setText(l_name);
            stretch_video.setVisibility(View.GONE);

            if (exam_id.equalsIgnoreCase("0")
                    || exam_id.equalsIgnoreCase("")
                    || exam_id.equalsIgnoreCase("null")) {
                start_btn.setVisibility(View.GONE);
            } else {
                start_btn.setVisibility(View.VISIBLE);
            }

            if (!l_video.equalsIgnoreCase("")) {

                Log.d("Video_url", l_video);
                if (l_video.toLowerCase().indexOf(url_pattern.toLowerCase()) != -1 ) {
                    imageLayout.setVisibility(View.VISIBLE);
                    vidoblock1.setVisibility(View.GONE);
                    vidoblock2.setVisibility(View.GONE);
//                    if (videoView.isPlaying()) {
//                        videoView.pause();
//                    }
//                    main_image.setVisibility(View.VISIBLE);
//                    item_videoIcon.setVisibility(View.VISIBLE);
//                    mBufferingTextView.setVisibility(View.GONE);
                    layVideo.setVisibility(View.VISIBLE);
                    stretch_video.setVisibility(View.GONE);
//
//
                    video_id = getYoutubeVideoIdFromURL(l_video);
//                    originalImageUrl = "https://img.youtube.com/vi/"+video_id+"/0.jpg";
//                    Log.d("Video_id: ", video_id);
//                    Picasso.with(this).invalidate(originalImageUrl);
//                    Picasso.with(this).load(originalImageUrl).into(main_image);

//                    player.release();


                    //Youtube Player Fragment
//                    frag.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
//                        @Override
//                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                            if (!b) {
//                                if (!video_id.equalsIgnoreCase("")) {
//                                    layVideo.setVisibility(View.VISIBLE);
//                                    youTubePlayer.loadVideo(video_id);
//                                    //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//                                    youTubePlayer.setShowFullscreenButton(false);
//                                    player = youTubePlayer;
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//                        }
//                    });

                    //Yoututbe Player View
                    youTubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            if (!b) {
                                if (!video_id.equalsIgnoreCase("")) {
                                    layVideo.setVisibility(View.VISIBLE);
                                    youTubePlayer.loadVideo(video_id);
                                    //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//                                    youTubePlayer.setShowFullscreenButton(false);
                                    player = youTubePlayer;
                                }
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });

//                    player.release();


                    navigation_prev.setEnabled(false);
                    navigation_next.setEnabled(false);
                    if (jsonArray.length() > 1) {
                        if (screen_i + 1 == jsonArray.length()) {
                            navigation_next.setEnabled(false);
                        } else {
                            navigation_next.setEnabled(true);
                        }
                        if (screen_i == 0) {
                            navigation_prev.setEnabled(false);
                        } else {
                            navigation_prev.setEnabled(true);
                        }
                    }

                } else {

                    if (!jsonObject.getString("vdo_chipper_id").equalsIgnoreCase("")) {
                        imageLayout.setVisibility(View.VISIBLE);
                        vidoblock1.setVisibility(View.GONE);
                        vidoblock2.setVisibility(View.VISIBLE);
                        layVideo.setVisibility(View.GONE);
                        stretch_video.setVisibility(View.GONE);

                        JSONObject vdoChipperObject = jsonObject.getJSONObject("vdo_chipper");
                        otp_val = vdoChipperObject.getString("otp");
                        playbackInfo_val = vdoChipperObject.getString("playbackInfo");

//                        if (vdoPlayerSupportFragment.isVisible()) {
//                            vdoPlayerSupportFragment.onStop();
//                            vdoPlayerSupportFragment.onDestroy();
//                            vdoPlayerSupportFragment.addInitializationListener(null);
//                        }

                        vdoPlayerSupportFragment.initialize(this);

                    } else {
                        stretch_video.setVisibility(View.VISIBLE);
                        playVideo();
                    }

                    stretch_video.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            stopPosition = videoView.getCurrentPosition();

                            Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                            //Toast.makeText(getApplicationContext(), String.valueOf(stopPosition), Toast.LENGTH_LONG).show();
                            intent.putExtra("uriPath", l_video);
                            intent.putExtra("stopPosition", String.valueOf(stopPosition));
                            startActivity(intent);

//                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//                            DisplayMetrics metrics = new DisplayMetrics();
//                            getWindowManager().getDefaultDisplay().getMetrics(metrics);
//                            android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoView.getLayoutParams();
//                            params.width = metrics.widthPixels;
//                            params.height = metrics.heightPixels;
//                            params.leftMargin = 0;
//                            videoView.setLayoutParams(params);
                        }
                    });
                }

//                String uriPath = "https://www.learnersmall.in/android/public/course_video/goldenrules.mp4";
//                String uriPath = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//                uriPath = "http://flawa.in/upload_images/goldenrules.mp4";
//                try {
//                    uriPath = downloadUrl("https://drive.google.com/file/d/1Uez8O74BO_b0FmM6-RVsM7hRUixYdHAA/preview");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                course_title.setVisibility(View.GONE);

            } else {
//                if (videoView.isPlaying()) {
//                    videoView.pause();
//                }
                course_title.setVisibility(View.VISIBLE);
                imageLayout.setVisibility(View.GONE);
                vidoblock1.setVisibility(View.GONE);
                vidoblock2.setVisibility(View.GONE);

//                main_image.setVisibility(View.GONE);
//                item_videoIcon.setVisibility(View.GONE);

                layVideo.setVisibility(View.GONE);
            }

            Picasso picasso = Picasso.with(this);
            if (!l_details.equalsIgnoreCase("")
                    && !l_details.equalsIgnoreCase("null") && !l_details.isEmpty()) {
                course_details.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    course_details.setText(Html.fromHtml(l_details, Html.FROM_HTML_MODE_LEGACY, new PicassoImageGetter(picasso, course_details), null));
                } else {
                    course_details.setText(Html.fromHtml(l_details, new PicassoImageGetter(picasso, course_details), null));
                }
                //Intent intent = getIntent();
                //course_webview.loadUrl("https://learnersmall.in/android/admin/chapterdetails/" + l_id);
                //WebSettings webSettings = course_webview.getSettings();
                //webSettings.setJavaScriptEnabled(true);
            } else {
                course_details.setVisibility(View.GONE);
                //course_webview.setVisibility(View.GONE);
            }

            if (!l_created.equalsIgnoreCase("")) {
                course_time.setText("Created on " + l_created);
            } else {
                course_time.setVisibility(View.GONE);
            }

            if (!l_image.equalsIgnoreCase("")) {
                Log.d("pdf url contain ", String.valueOf(l_image.contains(".pdf")));
                if (l_image.contains(".pdf")) {
                    spinner_font.setVisibility(View.GONE);
                    spinner_arrow.setVisibility(View.GONE);
                    scroll_view.setVisibility(View.GONE);
                    start_btn.setVisibility(View.GONE);
                    imageLayout.setVisibility(View.GONE);
                    vidoblock1.setVisibility(View.GONE);
                    vidoblock2.setVisibility(View.GONE);
//                    stretch_video.setVisibility(View.GONE);
//                    main_image.setVisibility(View.GONE);
//                    item_videoIcon.setVisibility(View.GONE);

                    layVideo.setVisibility(View.GONE);

                    assesment_layout.setVisibility(View.GONE);
//                    pdfView.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.VISIBLE);

//                    if (imageLayout.getVisibility() == View.GONE) {
//                        // Not visible
//                        if (videoView.isPlaying()) {
//                            videoView.pause();
//                        }
//                    }

//                    final Context ctx = this;
//                    final DownloadFile.Listener listener = this;
//                    remotePDFViewPager = new RemotePDFViewPager(ctx, l_image, listener);
//                    remotePDFViewPager.setId(R.id.pdfViewPager);

                    if (Build.VERSION.SDK_INT >= 19) {
                        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                    }
                    else {
                        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    }
                    webView.getSettings().setSupportZoom(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setAppCacheEnabled(true);
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                            webView.loadUrl("javascript:(function() { " +
                                    "document.querySelector('[role=\"toolbar\"]').remove();})()");

//                            if (imageLayout.getVisibility() == View.GONE) {
//                                videoView.setVisibility(View.GONE);
//                                // Not visible
//                                if (videoView.isPlaying()) {
//                                    videoView.pause();
//                                }
//                            }
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            webView.loadUrl("javascript:(function() { " +
                                    "document.querySelector('[role=\"toolbar\"]').remove();})()");

//                            if (imageLayout.getVisibility() == View.GONE) {
//                                videoView.setVisibility(View.GONE);
//                                // Not visible
//                                if (videoView.isPlaying()) {
//                                    videoView.pause();
//                                }
//                            }
                        }
                    });



                    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                                System.out.println("PDF height " + webView.getContentHeight());
                                System.out.println("PDF Scroll Y " + webView.getScrollY());
                                Log.d("scrollX", String.valueOf(scrollX));
                                Log.d("scrollY", String.valueOf(scrollY));
                                Log.d("oldScrollX", String.valueOf(oldScrollX));
                                Log.d("oldScrollY", String.valueOf(oldScrollY));
                            }
                        });
                    //}

                    //String doc="<iframe src='http://docs.google.com/viewer?embedded=true&url= " + l_image + "' width='100%' height='100%'></iframe>";

                    webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + l_image);
                    //webView.loadUrl(doc);

                    System.out.println("PDF height " + webView.getContentHeight());
                    System.out.println("PDF Bar Size " + webView.getScrollBarSize());
                    System.out.println("PDF Scroll Y " + webView.getScrollY());
                } else {
                    //adapter.close();
                    course_image.setVisibility(View.VISIBLE);
                    Picasso.with(this).invalidate(l_image);
                    Picasso.with(this).load(l_image).into(course_image);
                }
            } else {
                course_image.setVisibility(View.GONE);
                //adapter.close();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void playVideo() {
        navigation_prev.setEnabled(false);
        navigation_next.setEnabled(false);

//        stretch_video.setVisibility(View.VISIBLE);
        imageLayout.setVisibility(View.VISIBLE);
        vidoblock1.setVisibility(View.VISIBLE);
        vidoblock2.setVisibility(View.GONE);
        layVideo.setVisibility(View.GONE);

        // Execute StreamVideo AsyncTask
        new StreamVideo().execute();

//        mBufferingTextView.setVisibility(VideoView.VISIBLE);


//        Uri uri = Uri.parse(l_video);
//        Log.e("video_url", l_video);
//
//        mediacontroller = new MediaController(this);
//        videoView.setMediaController(mediacontroller);
//        mediacontroller.setAnchorView(videoView);

//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
////                mBufferingTextView.setVisibility(VideoView.INVISIBLE);
//                if (jsonArray.length() > 1) {
//                    if (screen_i + 1 == jsonArray.length()) {
//                        navigation_next.setEnabled(false);
//                    } else {
//                        navigation_next.setEnabled(true);
//                    }
//                    if (screen_i == 0) {
//                        navigation_prev.setEnabled(false);
//                    } else {
//                        navigation_prev.setEnabled(true);
//                    }
//                }
//
//                videoView.start();
//
//                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                    @Override
//                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//                    }
//                });
//            }
//        });



//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                // Return the video position to the start.
//            }
//        });

//        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                videoView.setVisibility(View.GONE);
//                playVideo();
//                return true;
//            }
//        });

//        videoView.setVideoURI(Uri.parse(l_video));
//        videoView.requestFocus();

    }

    @Override
    public void onInitializationSuccess(VdoPlayer.PlayerHost playerHost, VdoPlayer vdoPlayer, boolean b) {
        Pair<String, String> exampleParams = getExampleOtpAndPlaybackInfo();

        VdoPlayer.VdoInitParams vdoInitParams = new VdoPlayer.VdoInitParams.Builder()
                .setOtp(exampleParams.first)
                .setPlaybackInfo(exampleParams.second)
                .build();

        vdoPlayer.load(vdoInitParams);
    }

    @Override
    public void onInitializationFailure(VdoPlayer.PlayerHost playerHost, ErrorDescription errorDescription) {

    }

    // StreamVideo AsyncTask
    private class StreamVideo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressbar
            pDialog = new ProgressDialog(DetailsActivity.this);
            // Set progressbar title
            pDialog.setTitle("Android Video Streaming Tutorial");
            // Set progressbar message
            pDialog.setMessage("Buffering...");
            pDialog.setIndeterminate(false);
            // Show progressbar
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            try {
                stretch_video.setVisibility(View.VISIBLE);

                // Start the MediaController
                MediaController mediacontroller = new MediaController(
                        DetailsActivity.this);
                mediacontroller.setAnchorView(videoView);
                // Get the URL from String VideoURL
                Uri video = Uri.parse(l_video);
                videoView.setMediaController(mediacontroller);
                videoView.setVideoURI(video);

                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        pDialog.dismiss();
                        videoView.start();

                        if (jsonArray.length() > 1) {
                            if (screen_i + 1 == jsonArray.length()) {
                                navigation_next.setEnabled(false);
                            } else {
                                navigation_next.setEnabled(true);
                            }
                            if (screen_i == 0) {
                                navigation_prev.setEnabled(false);
                            } else {
                                navigation_prev.setEnabled(true);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                pDialog.dismiss();
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onFailure(int flag) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
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
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to leave this page? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                }

//                if (vdoPlayer1.getPlaybackState() == 1) {
//                    vdoPlayer1.stop();
//                }

                vdoPlayerSupportFragment.onStop();
                vdoPlayerSupportFragment.onDestroy();

                Intent intent = new Intent(getApplicationContext(), ChapterActivity.class);
                intent.putExtra("activity_for", activity_for);
//              intent.putExtra("list_id", sub_id);
//              intent.putExtra("list_title", sub_title);
//              intent.putExtra("cou_id", cou_id);
//              intent.putExtra("cou_title", cou_title);
                intent.putExtra("list_id", cou_id);
                intent.putExtra("list_title", cou_title);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void updateLayout() {
        screen_change = 1;
        root.removeAllViewsInLayout();
        root.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        root.addView(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        root.addView(prev_button,
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        root.addView(next_button,
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_prev:
//                    Toast.makeText(getApplicationContext(), "Previous", Toast.LENGTH_LONG).show();
                    if (jsonArray.length() > 1) {
                        if (screen_i > 0) {
                            previousData();
                        }
                    }
                    return true;
                case R.id.navigation_next:
//                    Toast.makeText(getApplicationContext(), "Next", Toast.LENGTH_LONG).show();
                    if (jsonArray.length() > 1) {
                        if (screen_i + 1 < jsonArray.length()) {
                            nextData();
                        }
                    }
                    return true;
            }

            return false;
        }
    };

    public void previousData() {
        screen_i--;
        Log.d("screen_element_prev", String.valueOf(screen_i));
        if (screen_change == 1) {
            updateLayoutadd();
        }

//        if (videoView.isPlaying()) {
//            videoView.pause();
//        }

//        if (vdoPlayer1.getPlaybackState() == 1) {
//            vdoPlayer1.stop();
//        }

        viewJsonDataInToLayout(screen_i);
    }

    public void nextData() {
        screen_i++;
        Log.d("screen_element_next", String.valueOf(screen_i));
        if (screen_change == 1) {
            updateLayoutadd();
        }

//        if (videoView.isPlaying()) {
//            videoView.pause();
//        }

//        if (vdoPlayer1.getPlaybackState() == 1) {
//            vdoPlayer1.stop();
//        }

        viewJsonDataInToLayout(screen_i);
    }

    public void updateLayoutadd() {
        screen_change = 0;
        root.removeAllViewsInLayout();
        root.addView(main_layout);
        root.addView(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void updateReadStatus(String chpdt_id) {
        String url = "https://learnersmall.in/android/api/v2/updatereadstatus";
        RequestParams params = new RequestParams();
        params.put("id", chpdt_id);
        params.put("student", student_id);

        WebServiceController wc = new WebServiceController(DetailsActivity.this,DetailsActivity.this);
        wc.sendRequest(url, params,1);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (videoView.isPlaying()) {
//            stopPosition = videoView.getCurrentPosition();
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                videoView.pause();
//            }
////            mkplayer.stop();
////            MxVideoPlayer.releaseAllVideos();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        videoView.seekTo(stopPosition);
//        videoView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (videoView.isPlaying()) {
//            videoView.stopPlayback();
//        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

//    public String downloadUrl(String myurl) throws IOException {
//        System.out.println("download url string " + myurl);
//        InputStream is = null;
//        try {
//            URL url = new URL(myurl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.connect();
//            is = conn.getInputStream();
//            String contentAsString = readIt(is);
//            return contentAsString;
//        } finally {
//            if (is != null) {
//                is.close();
//            }
//        }
//    }
//
//    public String readIt(InputStream stream) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            if (line.contains("fmt_stream_map")) {
//                sb.append(line + "\n");
//                break;
//            }
//        }
//        reader.close();
//        String result = decode(sb.toString());
//        String[] url = result.split("\\|");
//        return url[1];
//    }
//
//    public String decode(String in) {
//        String working = in;
//        int index;
//        index = working.indexOf("\\u");
//        while (index > -1) {
//            int length = working.length();
//            if (index > (length - 6)) break;
//            int numStart = index + 2;
//            int numFinish = numStart + 4;
//            String substring = working.substring(numStart, numFinish);
//            int number = Integer.parseInt(substring, 16);
//            String stringStart = working.substring(0, index);
//            String stringEnd = working.substring(numFinish);
//            working = stringStart + ((char) number) + stringEnd;
//            index = working.indexOf("\\u");
//        }
//        return working;
//    }
}
