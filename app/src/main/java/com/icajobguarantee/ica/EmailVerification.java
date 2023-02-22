package com.icajobguarantee.ica;

import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailVerification extends AppCompatActivity {
    EditText email;
    Button verify_button;
    LinearLayout lowerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        init();
    }

    private void init() {
        email = (EditText) findViewById(R.id.email);
        verify_button = (Button) findViewById(R.id.verify_button);
        lowerLayout = (LinearLayout) findViewById(R.id.lowerLayout);

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length() == 0) {
                    email.requestFocus();
                    email.setError("Email id cannot be empty");
                    return;
                } else if (email.getText().toString().length() > 0 && !isEmailValid(email.getText().toString())) {
                    email.requestFocus();
                    email.setError("Enter a valid email");
                    return;
                } else {
                    ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                        getEmailVarification(email.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public static boolean isEmailValid(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void getEmailVarification(String email) {
        if (email.equalsIgnoreCase("paulsujit0@gmail.com")) {
            verify_button.setVisibility(View.GONE);
            lowerLayout.setVisibility(View.VISIBLE);
        } else {

        }
    }

    /**
     * react to the user tapping the back/up icon in the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
