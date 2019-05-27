package com.tripsolver.backoffice.view;

/**
 * Created by lenovo on 4/27/2019.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.FragmentNavigation;
import com.tripsolver.backoffice.interfacefiles.MainContract;
import com.tripsolver.backoffice.model.Session;
import com.tripsolver.backoffice.model.Validations;
import com.tripsolver.backoffice.presenter.TicketBookingResponsePresenter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AppActivity extends AppCompatActivity implements MainContract.MainView,FragmentNavigation.Presenter{
    private RelativeLayout layout;
    ArrayList<String> itemlist;
    private DrawerLayout drawerLayout;
    String selectedfilter;
    EditText searchedittxt;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    LinearLayout fulllayout;
    String filtervalue;
    TextView clientname;
    TextView clientnamemenu;
    ImageView profileimg;
    RelativeLayout myprofilelayout;
   public boolean isconfirmcall,isticketedcall,iscancelledcall,isunconfirmed;
    ImageView searchiconimg;
    TicketBookingResponsePresenter ticketbookingpresenter;
    protected FragmentNavigation.Presenter navigationPresenter;

    Session sess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appactivitylayout);
/*
        layout = (RelativeLayout) findViewById(R.id.layout);
*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        searchiconimg = (ImageView) findViewById(R.id.searchicon);
        clientname=(TextView)findViewById(R.id.clientname);
        fulllayout=(LinearLayout)findViewById(R.id.fulllayout);

        sess=new Session(AppActivity.this);
        configureNavigationDrawer();
        configureToolbar();




    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
/*
        actionbar.setHomeAsUpIndicator(R.drawable.ic_action_menu_white);
*/
        Fragment  f=new ActivityCount();
        actionbar.setDisplayHomeAsUpEnabled(true);
        clientname.setText(sess.getBofname());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, f);
        transaction.commit();
        drawerLayout.closeDrawers();
    }
    private void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);

        View headerView = navView.inflateHeaderView(R.layout.navheaderlayout);
        clientnamemenu=(TextView)headerView.findViewById(R.id.clientnamemenu);
        myprofilelayout=(RelativeLayout)headerView.findViewById(R.id.myprofilelayout);
        profileimg=(ImageView)headerView.findViewById(R.id.profileimg);
        setClientImage();
        clientnamemenu.setText(sess.getBofname());
        myprofilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AppActivity.this,MyProfile.class);
                startActivity(i);
            }
        });

        ActionBarDrawerToggle  mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment f = null;
                int itemId = menuItem.getItemId();
                if (itemId == R.id.bookinginfo) {
                    Bundle b=new Bundle();
                    b.putInt("activitytype",0);
                    b.putLong("days",30);


                   f=new MainActivity();
                    f.setArguments(b);
                } /*else if (itemId == R.id.profile) {


                }*/
            else if (itemId == R.id.reports) {
                   /* f=new ReportsData();*/
                   Intent i=new Intent(AppActivity.this,ReportsDataRequest.class);
                   startActivity(i);

            }

                else if (itemId == R.id.activitycount) {
                   f=new ActivityCount();
                   /* Intent i=new Intent(AppActivity.this,ActivityCount.class);
                    startActivity(i);*/
                }

            else if(itemId==R.id.logout)
                {
                    sess.setISLogin(false);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AppActivity.this);

                  /*  builder.setTitle("Confirm");*/
                    builder.setMessage("Are you sure to Logout?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            Intent i=new Intent(AppActivity.this,LoginActivity.class);
                            startActivity(i);
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();


                }
             if (f != null) {
                 getSupportFragmentManager().beginTransaction().remove(f).commit();

                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction()

                         .replace(R.id.content_frame, f)


                         .commit();

                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch(itemId) {
            // Android home

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }
    @Override
    public void onBackPressed()
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void setData(String value, TextView txtview) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setFragment(MainActivity fragment) {

    }





    @Override
    public void changeVisibility(int value, View view) {

    }

    @Override
    public void setQuote(String string) {

    }

    @Override
    public void bindData(String filtervalue, String filtertype) {

    }

    @Override
    public void bindFilterData(String fitervalue, String selectedfilter) {

    }

    @Override
    public void addFragment(MainActivity fragment) {

    }

    @Override
    public void onButtonClick() {

    }

    @Override
    public void bindApiData(String value, TextView view) {

    }

    @Override
    public void setVisibleValue(int value, View view) {

    }

    @Override
    public void servicecalledComplete() {

    }

    @Override
    public void onDestroy() {
super.onDestroy();
    }
    public void setClientImage()
    {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);


            URL u = new URL(sess.getLogoUrl());
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect();
            int code = huc.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                Picasso.with(this)
                        .load(sess.getLogoUrl())
                        .resize(50, 50)
                        .centerCrop()
                        .into(profileimg);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}