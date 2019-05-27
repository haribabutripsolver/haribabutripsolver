package com.tripsolver.backoffice.view;

/**
 * Created by vanaja on 1/11/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.model.Session;


public class Splash extends Activity {
    boolean versionupdateservice = false;

    private static final String TAG = "ConnectionClass-Sample";
    public static final String mPath = "cityvalues.txt";
    private List<String> mLines;

    private TextView mTextView;
    private View mRunningBar;
    TransitionDrawable transition;

    private String mURL = "http://devffv1.flipfares.com/images/airline_logos_new/ai.png";
    private int mTries = 0;


    // Splash screen timer
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    RelativeLayout progresslayout;
    private double currentLatitude;
    private double currentLongitude;
    final int REQUEST_READ_PHONE_STATE = 1;
    String versionname;
    private static final int REQUEST_LOCATION = 2;
    boolean iscityservicecalled = false;
    private static int SPLASH_TIME_OUT = 3000;
    ArrayList<ArrayList<String>> citylistvalues;
    LinearLayout searchbuttonvalue;
    String username, password, walletamt, userid, firstname, mobilevalue;
    int status;
    Button searchbt;
    String messagevalue;
    Bundle homebundle;
    Session sess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.splashlayout);
        Session sess=new Session(Splash.this);
        final boolean islogin=sess.getIsLogin();
  new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(islogin)
                {
                    Intent i = new Intent(Splash.this, AppActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(Splash.this, LoginActivity.class);
                    startActivity(i);
                }


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}