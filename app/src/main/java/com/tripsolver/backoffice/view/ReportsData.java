package com.tripsolver.backoffice.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.tripsolver.backoffice.BuildConfig;
import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.ReportsContract;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.ReportsDataResponse;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import com.tripsolver.backoffice.model.ScrollViewExt;
import com.tripsolver.backoffice.model.ScrollViewListener;
import com.tripsolver.backoffice.model.Session;
import com.tripsolver.backoffice.model.Validations;
import com.tripsolver.backoffice.presenter.ReportDataPresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 4/27/2019.
 */

public class ReportsData extends AppCompatActivity implements ReportsContract.MainView, ScrollViewListener {
    String tabledata="";

    TableLayout tl;
    String returnBodyText;
    TableRow tr;
    android.app.AlertDialog dialog;

    Session sess;
    List<ReportsDataResponse> repdataresponselist;

    TextView companyTV,valueTV;
    ReportDataPresenter reportsdatapresenter;
    String fromdate,todate,source;
    TextView noresultsfound;
    ProgressDialog pdia;
    TextView generateddate, sourcevalue,fromdatevalue,todatevalue,statusvalue;
    String yearstr,monthstr,daystr;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    int dummy = 0;
    String clientname;
    String todaydatestr;
    Button sendmail;
    ScrollViewExt scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rowdataview);
        // Inflate the layout for this fragment
        Calendar cal=Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
         todaydatestr=dateFormat.format(date);

        yearstr =String.valueOf(cal.get(Calendar.YEAR));
        monthstr = String.valueOf(cal.get(Calendar.MONTH)+1);
        daystr = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        tl = (TableLayout)findViewById(R.id.maintable);
        noresultsfound=(TextView)findViewById(R.id.noresultsfound);
        generateddate=(TextView)findViewById(R.id.generateddate);
        sourcevalue=(TextView)findViewById(R.id.sourcevalue);
        fromdatevalue=(TextView)findViewById(R.id.fromdatevalue);
        todatevalue=(TextView)findViewById(R.id.todatevalue);
        statusvalue=(TextView)findViewById(R.id.statusvalue);
        sendmail=(Button)findViewById(R.id.sendmail);
        scroll = (ScrollViewExt) findViewById(R.id.scrollView);
        scroll.setScrollViewListener(this);
        sess=new Session(ReportsData.this);
        clientname=sess.getBofname();

        sourcevalue.setText("All");
        statusvalue.setText("All");


Bundle b=getIntent().getExtras();
 String srcstr     =  b.getString("srcstr");
   String fromdatestr=  b.getString("fromdatestr");
    String todatestr=  b.getString("todatestr");
    String statusstr=b.getString("statusstr");
    String summarysrc,summarystatusstr;
        summarysrc=srcstr;
        summarystatusstr=statusstr;

        if(srcstr.equals(""))
    {
        summarysrc="All";
    }
        if(statusstr.equals(""))
        {
            summarystatusstr="All";
        }

        reportsdatapresenter=new ReportDataPresenter(this);

        try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat reqformat = new SimpleDateFormat("dd/MM/yyyy");
        Date frmdate=sdf.parse(fromdatestr);
       String reqfromdate=reqformat.format(frmdate);
       Date todate=sdf.parse(todatestr);
       String reqtodate=reqformat.format(todate);
        reportsdatapresenter.bindApiData(todaydatestr,generateddate);
        reportsdatapresenter.bindApiData(summarysrc,sourcevalue);
        reportsdatapresenter.bindApiData(reqfromdate,fromdatevalue);
            reportsdatapresenter.bindApiData(summarystatusstr,statusvalue);

        reportsdatapresenter.bindApiData(reqtodate,todatevalue);

    }
    catch (Exception e)
    {
        e.printStackTrace();
    }




        Session sess=new Session(this);
 android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.reports);
        int status = NetworkUtil.getConnectivityStatus(this);

        if (status != 0) {
            callApiService(sess.getAgencyId(),fromdatestr,todatestr,statusstr,srcstr);

        }
        else
        {
            noInternetPopUp();
        }

    }



    public void callApiService(String b2cid,String fromdatestr,String todatestr,String statusstr,String source) {
        reportsdatapresenter.onButtonClick();
        JSONObject reqobj = new JSONObject();
        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);
      /*  HashMap<String,String> headermap=new HashMap<>();
        headermap.put("Content-Type","application/json");*/
     try {

            reqobj.put("b2c_idn", b2cid);
            reqobj.put("Fromdate", fromdatestr);
            reqobj.put("Todate", todatestr);
            reqobj.put("status", statusstr);
         reqobj.put("source", source);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Call<ResponseBody> call = apiService.bookingreportsdata(new FooRequest(b2cid,fromdatestr,todatestr,statusstr,source));

        // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {

            /* When server response. */
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

			/*	hideProgressDialog();*/

                StringBuffer messageBuffer = new StringBuffer();
                int statusCode = response.code();
                if (statusCode == 200) {
                    try {

                        // Get return string.
                        returnBodyText = response.body().string();
                        JSONObject jsonResponsevolley = new JSONObject(returnBodyText);
                        if(jsonResponsevolley.getJSONObject("Header").getString("Message").equals("Data Not Found")) {
                            reportsdatapresenter.setVisibleValue(1,noresultsfound);
                            reportsdatapresenter.servicecalledComplete();
                        }
                         JSONArray bookingsarray = jsonResponsevolley.getJSONArray("Bookingactivites");
                        returnBodyText = bookingsarray.toString();
                        Gson gson = new Gson();

                        TypeToken<List<ReportsDataResponse>> typeToken = new TypeToken<List<ReportsDataResponse>>() {
                        };
                        repdataresponselist = gson.fromJson(returnBodyText, typeToken.getType());
                        if (repdataresponselist != null && repdataresponselist.size() > 0) {
                            try {
                                addHeaders();

                                addData();
                                reportsdatapresenter.servicecalledComplete();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

							/*if (registerResponse.isSuccess()) {
                                messageBuffer.append(registerResponse.getMessage());
							} else {
								messageBuffer.append("User register failed.");
							}*/
                        }
                        else {
                            reportsdatapresenter.setVisibleValue(1,noresultsfound);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
						/*Log.e(TAG_RETROFIT_GET_POST, ex.getMessage());*/
                    }
                } else {
                    // If server return error.
                    messageBuffer.append("Server return error code is ");
                    messageBuffer.append(statusCode);
                    messageBuffer.append("\r\n\r\n");
                    messageBuffer.append("Error message is ");
                    messageBuffer.append(response.message());
                }

                // Show a Toast message.
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

			/*	hideProgressDialog();*/

            }
        };

        // Use callback object to process web server return data in asynchronous mode.
        call.enqueue(callback);


    }


    /** This function add the headers to the table **/

    public void addHeaders(){
        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");
        /** Create a TableRow dynamically **/

        tr = new TableRow(this);

        tr.setLayoutParams(new LayoutParams(

                LayoutParams.FILL_PARENT,

                LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView valueTV = new TextView(this);

        valueTV.setText("BK RefID");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(25, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV);
        valueTV = new TextView(this);

        valueTV.setText("Status");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.
        valueTV = new TextView(this);

        valueTV.setText("Source");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV);
        TextView companyTV = new TextView(this);

        companyTV.setText("Booking Type");

        companyTV.setTextColor(Color.WHITE);

        companyTV.setTypeface(mtypeFace);

        companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        companyTV.setPadding(50, 10, 5, 10);

        tr.addView(companyTV);
        companyTV = new TextView(this);
        companyTV.setText("Date");

        companyTV.setTextColor(Color.WHITE);

        companyTV.setTypeface(mtypeFace);

        companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        companyTV.setPadding(50, 10, 5, 10);

        tr.addView(companyTV);  // Adding textView to tablerow.

        /** Creating another textview **/

      // Adding textView to tablerow.

         valueTV = new TextView(this);


        valueTV.setText("PNR");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);


        valueTV.setText("Name");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.


        valueTV = new TextView(this);


        valueTV.setText("Airline");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);

        valueTV.setText("From");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);

        valueTV.setText("To");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);

        valueTV.setText("Class");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);

        valueTV.setText("TripType");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);

        valueTV.setText("TotalPax");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);

        valueTV.setText("TotalFare");

        valueTV.setTextColor(Color.WHITE);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        valueTV.setPadding(30, 30, 20, 30);

        valueTV.setTypeface(mtypeFace);

        tr.addView(valueTV); // Adding textView to tablerow.

       // Adding


        tr.setBackgroundResource(R.color.gradient);

        // Add the TableRow to the TableLayout

        tl.addView(tr, new TableLayout.LayoutParams(

                LayoutParams.FILL_PARENT,

                LayoutParams.WRAP_CONTENT));


        // we are adding two textviews for the divider because we have two columns

        tr = new TableRow(this);

        tr.setLayoutParams(new LayoutParams(

                LayoutParams.FILL_PARENT,

                LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
/*
        TextView divider = new TextView(this);

        divider.setText("______________________________________________________________________________________________________________");

        divider.setTextColor(Color.parseColor("#ffffff"));

        divider.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        divider.setPadding(5, 0, 0, 0);

        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        tr.addView(divider);*/ // Adding textView to tablerow.

       /* TextView divider2 = new TextView(this);

        divider2.setText("-------------------------");

        divider2.setTextColor(Color.GREEN);

        divider2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        divider2.setPadding(5, 0, 0, 0);

        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        tr.addView(divider2);*/ // Adding textView to tablerow.

        // Add the TableRow to the TableLayout

        tl.addView(tr, new TableLayout.LayoutParams(

                LayoutParams.FILL_PARENT,

                LayoutParams.WRAP_CONTENT));

    }

    /** This function add the data to the table **/

    public void addData(){
        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");
        if (dummy != 0) {
            dummy--;
        }
        int value = dummy;
        for (int i = value; i < repdataresponselist.size()&&i<(dummy+20); i++)

        {
value++;
            /** Create a TableRow dynamically **/

            tr = new TableRow(this);
            /*if (i % 2 == 0) {*/
/*
            } else {
                tr.setBackgroundResource(R.color.grey);

            }*/
            ReportsDataResponse reportdataobj=repdataresponselist.get(i);
            ArrayList<String> responsestrarr=new ArrayList<>();
            responsestrarr.add(reportdataobj.getBkRefid());

            responsestrarr.add(reportdataobj.getStatus());
            responsestrarr.add(reportdataobj.getSource());

            responsestrarr.add(reportdataobj.getBookingtype());

            responsestrarr.add(reportdataobj.getBookingdate());
            responsestrarr.add(reportdataobj.getPNR());
            responsestrarr.add(reportdataobj.getName());
            responsestrarr.add(reportdataobj.getAirline());
            responsestrarr.add(reportdataobj.getFrom());
            responsestrarr.add(reportdataobj.getTo());
            responsestrarr.add(reportdataobj.getClasstype());
            responsestrarr.add(reportdataobj.getTriptype());
            responsestrarr.add(reportdataobj.getTotalpax());
            responsestrarr.add(reportdataobj.getTotalfare());

            for (int j = 0; j < 14; j++) {

try {

    tr.setLayoutParams(new LayoutParams(

            LayoutParams.FILL_PARENT,

            LayoutParams.WRAP_CONTENT));

    /** Creating a TextView to add to the row **/
    companyTV = new TextView(this);




    companyTV.setText(responsestrarr.get(j));

    companyTV.setTextColor(Color.parseColor("#000000"));

    companyTV.setTypeface(mtypeFace);

    companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

    if(j==1)
    {

        switch(responsestrarr.get(j)) {
            case "Confirmed":
                companyTV.setBackgroundColor(Color.parseColor("#fdba52"));

                break;
            case "Ticketed":
                companyTV.setBackgroundColor(Color.parseColor("#b9eeb9"));

                break;
            case "Cancelled":
                companyTV.setBackgroundColor(Color.parseColor("#f6bcbc"));

                break;
            case "Booking Started":
                companyTV.setBackgroundColor(Color.parseColor("#e6c295"));

                break;

        }
    }


    switch (j)
    {
        case 2:
            companyTV.setPadding(40, 30, 5, 30);
            break;
        case 3:
            companyTV.setPadding(50, 30, 5, 30);
            break;
        case 7:
            companyTV.setPadding(40, 30, 5, 30);
            break;
        case 8:
            companyTV.setPadding(25, 30, 5, 30);
            break;
        case 12:
            companyTV.setPadding(70, 30, 5, 30);
            break;
        case 13:
            companyTV.setPadding(40, 30, 5, 30);
            break;

       /* case 11:
            companyTV.setPadding(40, 30, 5, 30);
            break;
        case 12:
            companyTV.setPadding(40, 30, 5, 30);
            break;*/
            default:
                companyTV.setPadding(20, 30, 5, 30);

                break;







    }


    tr.addView(companyTV);  // Adding textView to tablerow.

    tl.addView(tr, new TableLayout.LayoutParams(

            LayoutParams.FILL_PARENT,

            LayoutParams.WRAP_CONTENT));
}
catch (Exception e)
{
    e.printStackTrace();
}


            }

        }
        dummy=value;
tabledata=getHtmlData();

    }


   /* public void callRecyclerAdapter(List<PassengerDataResponse> passendatalist) {
        if (passendatalist != null) {

            PassengerListAdapter mAdapter = new PassengerListAdapter(passendatalist, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            noresultsfoundtext.setVisibility(View.VISIBLE);
        }
        passengerdetpresenter.servicecalledComplete();
    }


    public void bindCustData() {
        //Intialize data
        CustomerDataResponse custdataobj = customerdatalist.get(0);
        String custfnamevalue = custdataobj.getFirstName();
        String custlnamevalue = custdataobj.getLastName();
        String phonevalue = custdataobj.getPhone();
        String emailvalue = custdataobj.getEmail();

        // Set Data
        passengerdetpresenter.bindApiData(custfnamevalue,custfnamevaluetxt);
        passengerdetpresenter.bindApiData(custlnamevalue,custlnamevaluetxt);
        passengerdetpresenter.bindApiData(phonevalue,phonevaluetxt);
        passengerdetpresenter.bindApiData(emailvalue,emailvaluetxt);



    }*/
    @Override
    public void setData(String value, TextView txtview) {
txtview.setText(value);
    }



    @Override
    public void changeVisibility(int value, View view) {
view.setVisibility(value);
    }

    @Override
    public void setQuote(String string) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            // Android home
            case android.R.id.home:
this.finish();
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }

    @Override
    public void showProgress() {
        pdia = new ProgressDialog(ReportsData.this);
        pdia.setMessage("Loading...");
        pdia.setCancelable(false);
        pdia.show();
    }


    @Override
    public void hideProgress() {
        if(pdia!=null)
        {
            pdia.dismiss();
        }

}

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        try {
            View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
            int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
            // if diff is zero, then the bottom has been reached
            if (diff == 0 && (dummy % 20 == 0)) {
                dummy++;



                try {
                              /*  getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });*/
                    reportsdatapresenter.onButtonClick();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addData();

                            reportsdatapresenter.servicecalledComplete();
                        }
                    }, 1000);


                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class FooRequest {
        final String b2c_idn;
        final String Fromdate;
        final String Todate;
        final String status;
        final String source;

        FooRequest(String b2c_idn, String Fromdate,String Todate,String status,String source) {
            this.b2c_idn=b2c_idn;
            this.Fromdate=Fromdate;
            this.Todate=Todate;
            this.status=status;
            this.source=source;
        }
    }

    public void noInternetPopUp()
    {
        final android.support.v7.app.AlertDialog.Builder builder1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder1 = new android.support.v7.app.AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        } else {
            builder1 = new android.support.v7.app.AlertDialog.Builder(this);
        }
        builder1.setMessage("Please make sure the device has internet connection")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.cancel();
                    }
                })

                .show();
    }



    private class SendMailTask extends AsyncTask<String, Void, Void> {
        String tabledata,emailstring;
        private SendMailTask(String tabledata,String emailstring)
        {
            this.tabledata=tabledata;
            this.emailstring=emailstring;
        }

        protected void onPreExecute() {
            reportsdatapresenter.onButtonClick();

        }

        protected Void doInBackground(String... urls) {
       Bitmap bitmap=     getBitmapFromView(tl);
            String imagedata;
            Uri  urivalue=null;

            urivalue=  saveBitmap(bitmap);

           imagedata=  getMimeType(ReportsData.this,urivalue);

            final String from = "bharibabu@tripsolver.net";
            final String pass = "haritripsolver";
            // Recipient's email ID needs to be mentioned.
            String to = emailstring;
            try {
                String host = "smtp.gmail.com";





      /*      String from = "test@gmail.com";
            String pass ="test123";
            // Recipient's email ID needs to be mentioned.
            String to = "g.vanajachary@gmail.com";

            String host = "smtp.gmail.com";*/

                // Get system properties
                Properties properties = System.getProperties();
                // Setup mail server
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.user", from);
                properties.put("mail.smtp.password", pass);
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");

                // Get the default Session object.
                //  Session session = Session.getDefaultInstance(properties);

                javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties,
                        new javax.mail.Authenticator() {
                            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                return new javax.mail.PasswordAuthentication(
                                        from, pass);// Specify the Username and the PassWord

                            }
                        });


                try {
                    // Create a default MimeMessage object.
                    MimeMessage message = new MimeMessage(session);

                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(from));

                    // Set To: header field of the header.
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(to));

                    // Set Subject: header field
                    String  version = BuildConfig.VERSION_NAME;
                    message.setSubject(clientname+" Bookings Report: "+todaydatestr);

/*
                StringBuilder builder = new StringBuilder();
*/


                    String passname, passage, seatnopassstring, passgen, citizentype;


                /*builder.append(String.valueOf());*/

/*
                String str = messagee.getStackTrace().toString();
*/
                    StringWriter sw = new StringWriter();
/*
                    exp.printStackTrace(new PrintWriter(sw));
*/


                    message.setText(sw.toString(), "utf-8", "image/png");
                    message.setContent(imagedata,"image/png");


                    message.setText(tabledata, "utf-8", "html");

                    // Send message
                    Transport transport = session.getTransport("smtp");
                    transport.connect(host, from, pass);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

              runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    System.out.println("Sent message successfully....");
                    Log.e("myApp", "Sent message successfully");
                } catch (Exception mex) {
                    mex.printStackTrace();
                }

            } catch (Exception e1)

            {
                e1.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            reportsdatapresenter.servicecalledComplete();

            Toast.makeText(ReportsData.this,"Sucessfully Sent Reports to Email",Toast.LENGTH_LONG).show();
        }
    }


    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    public Uri saveBitmap(Bitmap bitmap)
    {
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"title", null);
        Uri screenshotUri = Uri.parse(path);
       return screenshotUri;
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        if(dialog!=null) {
            dialog.dismiss();
        }
      final  AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();


    }

    public  String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

    public String getHtmlData()
    {
        String data="";
StringBuilder builder=new StringBuilder();
String totalbookings=String.valueOf(repdataresponselist.size());
        String totalpax=String.valueOf(repdataresponselist.size());


builder.append("<table class=\"m_2113622591869623870m_-4426447250637836043desktop\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"font-size:14px;color:#333333;background-color:#ffffff;font-family:Arial,Helvetica,sans-serif;border-collapse:collapse;line-height:100%;width:850px\"><tbody><tr><td align=\"left\" valign=\"top\" width=\"100%\"><img src=\"https://ci6.googleusercontent.com/proxy/J1ov65-LRUilmDbhEujwzHjY9VM8hmrMDIhbO_-oBxV4jSFslTAQlQqq_nvQdweCg2ZeyjI1nRDaYn1fs8xT3neGsEIKjKSpjiQ=s0-d-e1-ft#https://www.tripsolver.net/images/tripsolver-border.jpg\" width=\"100%\" height=\"7\" border=\"0\" style=\"text-align:center;font-size:20px;line-height:100%;display:block;font-family:Arial,Helvetica,sans-serif;font-weight:bold;padding:0px;margin:0px;color:#555555\" class=\"CToWUd\"></td></tr></tbody><tbody><tr><td align=\"left\" valign=\"top\" style=\"border-bottom:1px solid #d4d4d4\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr><td align=\"left\" valign=\"top\" height=\"70\" width=\"25%\" bgcolor=\"#ffffff\" style=\"text-align:left;padding-top:10px\"><a href=\"#m_2113622591869623870_m_-4426447250637836043_\" style=\"text-decoration:none\"><img src=\"https://ci4.googleusercontent.com/proxy/YJdz_INo9X_47_9Q_veggM4B24l81COnMF72BPXXSxOfU-GTT8yyhooE9XUbcArVj1xwNQQ6VxetHAA_OBm7Y7aU=s0-d-e1-ft#https://www.tripsolver.net/images/brol_logo.png\" border=\"0\" style=\"font-size:20px;line-height:100%;display:block;font-family:Arial,Helvetica,sans-serif;font-weight:bold;width:140px;margin:0px;color:#555555\" alt=\"Brol\" title=\"Brol\" class=\"CToWUd\"></a></td><td align=\"right\" valign=\"middle\" width=\"75%\" height=\"70\" style=\"background-color:#ffffff\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr><td align=\"left\" valign=\"middle\" style=\"text-align:right;font-size:13px;font-weight:600;color:#000000;font-family:Arial,Helvetica,sans-serif;line-height:20px;padding-right:20px\"><a href=\"https://brol.tripsolver.net/admin/login.aspx\" style=\"text-decoration:none;color:#0f479c;font-size:16px\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://brol.tripsolver.net/admin/login.aspx&amp;source=gmail&amp;ust=1557924296402000&amp;usg=AFQjCNH9jziq5SLd4Imtia-EL7GgWYbkPg\"><b>Backoffice Login</b></a></td></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr><td align=\"left\" valign=\"top\" width=\"94%\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr><td align=\"left\" valign=\"top\" height=\"10\" bgcolor=\"#ffffff\">&nbsp;</td></tr><tr></tr><tr><td style=\"font-size:20px;font-family:Arial,Helvetica,sans-serif;color:#06448f;font-weight:600;line-height:20px;text-align:left\">Your bookings daily report</td></tr><tr><td style=\"font-size:13px;font-family:Arial,Helvetica,sans-serif;color:#000000;line-height:20px;text-align:left;padding-top:10px\">Daily report is generated every 2 hours. Request to login to your backoffice portal for more information.</td></tr><tr><td align=\"left\" valign=\"top\" height=\"10\" bgcolor=\"#ffffff\">&nbsp;</td></tr><tr><td align=\"left\" valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr><td align=\"left\" valign=\"middle\" style=\"font-family:Arial,Helvetica,sans-serif;font-size:14px;line-height:18px;color:#000000;font-weight:bold;padding-bottom:5px\">Summary:</td></tr><tr><td align=\"left\" valign=\"middle\" style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:18px;color:#000000;padding-bottom:5px\">Name: Bookings daily report</td></tr><tr><td align=\"left\" valign=\"middle\" style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:18px;color:#000000;padding-bottom:5px\">Date: "+todaydatestr+"</td>" +
        "</tr><tr><td align=\"left\" valign=\"middle\" style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:18px;color:#000000;padding-bottom:5px\">Total Bookings: "+totalbookings+"" +
        "</td></tr><tr><td align=\"left\" valign=\"middle\" style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:18px;color:#000000;padding-bottom:5px\">Total Passengers: "+data+"" +
        "</td></tr><tr><td align=\"left\" valign=\"middle\" style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:18px;color:#000000;padding-bottom:5px;font-weight:bold;padding-top:10px\">Details:</td></tr></tbody></table></td></tr></tbody></table>" +
        "</td></tr></tbody></table></td><td align=\"left\" valign=\"top\" width=\"3%\">&nbsp;</td></tr></tbody></table>");



builder.append("<table border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n" +
        "<tbody><tr> \n" +
        "\t\t\n" +
        "<td style=\"padding-right:50px\" bgcolor=\"#aed6f1\"><b>BK RefID<&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>\n" +
        "\n" +
        "<td bgcolor=\"#aed6f1\"><b>Status</b></td>\n" +
        "\n" +
        "<td bgcolor=\"#aed6f1\"><b>Source&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>\n" +
        "\n" +
        "<td bgcolor=\"#aed6f1\"><b>Booking Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>PNR</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>Name</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>From</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>From</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>To</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>Class</b></td>\n" +
        "<td bgcolor=\"#aed6f1\"><b>TripType</b></td>\n" +
        "\n" +
        "<td bgcolor=\"#aed6f1\"><b>TotalPax</b></td>\n" +
        "\n" +
        "<td bgcolor=\"#aed6f1\"><b>TotalFare</b></td>\n" +
        "\t\t\n" +
        "</tr>");

        for (int i = 0; i < repdataresponselist.size(); i++)

        {

            tr = new TableRow(this);
            ReportsDataResponse reportdataobj = repdataresponselist.get(i);
            ArrayList<String> responsestrarr = new ArrayList<>();
            responsestrarr.add(reportdataobj.getBkRefid());

            responsestrarr.add(reportdataobj.getStatus());
            responsestrarr.add(reportdataobj.getSource());

            responsestrarr.add(reportdataobj.getBookingtype());

            responsestrarr.add(reportdataobj.getBookingdate());
            responsestrarr.add(reportdataobj.getPNR());
            responsestrarr.add(reportdataobj.getName());
            responsestrarr.add(reportdataobj.getAirline());
            responsestrarr.add(reportdataobj.getFrom());
            responsestrarr.add(reportdataobj.getTo());
            responsestrarr.add(reportdataobj.getClasstype());
            responsestrarr.add(reportdataobj.getTriptype());
            responsestrarr.add(reportdataobj.getTotalpax());
            responsestrarr.add(reportdataobj.getTotalfare());
            builder.append("<tr>\n");
            for (int j = 0; j < 14; j++) {


          builder.append("<td>" + responsestrarr.get(j) + "</td>");
            }
            builder.append("</tr>");
        }
        builder.append("</tbody></table>");
        builder.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>" +
                "<td width=\"850\" height=\"30\"></td></tr><tr>" +
                "<td style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#000000;line-height:18px;padding-bottom:10px;font-weight:600\">Regards</td></tr><tr>" +
                "<td style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#000000;line-height:18px;padding-bottom:10px\">Tripsolver Support.</td></tr><tr><td style=\"font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#000000;line-height:18px\">Note: Booking report is generated based on booking transactions at the time of report generation and may not be accurate. reports may have transactions made in the last 24hrs or previous day. for accurate report or booking count or transaction count, we suggest to login to your account and verify detailed and latest reports. If you have questions about your report, contact Tripsolver support or send an email to <a href=\"#m_2113622591869623870_m_-4426447250637836043_\" style=\"color:#0079c0\">support@tripsolver.net</a>. Tripsolver is not responsible for inaccurate reports in emails due to transaction based data limitations.</td></tr><tr><td style=\"padding-top:8px;padding-bottom:5px\"><table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\"><tbody><tr><td style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#000000;line-height:18px;float:left;padding-top:12px;padding-right:6px\"><a href=\"https://www.tripsolver.net/\" style=\"color:#0079c0\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://www.tripsolver.net/&amp;source=gmail&amp;ust=1557924296402000&amp;usg=AFQjCNHQMwdrJjQqw1a8p9DDl17zfjqQ5g\"><img src=\"https://ci5.googleusercontent.com/proxy/F1R51JXKweYeY8bgxz5XjdtpU-NfmRjERnrE0zM1cICV4eMr7AKx3j6oXxGjZQ9FzX6N_Og9p1HYqTuly35LO2ro3gC57A=s0-d-e1-ft#https://www.tripsolver.net/images/tripsolver_pw.png\" class=\"CToWUd\"></a>  </td></tr></tbody></table></td></tr><tr><td style=\"font-family:Arial,Helvetica,sans-serif;font-size:11px;color:#6d6d6d;line-height:18px;font-style:italic;font-weight:bold\">This is an automated report sent on a schedule. Please do not reply to this automated email. For any report schedule changes please contact tripsolver support.</td></tr><tr><td style=\"font-family:Arial,Helvetica,sans-serif;font-size:10px;color:#000000;line-height:18px;padding-top:20px;font-style:italic;color:#000000\">----DISCLAIMER<br>This email message and its attachments may contain confidential, proprietary or legally privileged informationand is intended solely for the use of the business entity to whom it is addressed.If you have erroneously received this message, please delete it immediately and notify the sender.If you are not the intended recipient of the email message you should not disseminate, distribute or copy this e-mail.E-mail transmission cannot be guaranteed to be secure or error-free as information could be intercepted, corrupted, lost,destroyed, incomplete or contain viruses and Tripsolver Inc accepts no liability for any damage caused by the limitations of the e-mail transmission.<br></td></tr></tbody></table>");
return builder.toString();
    }

    public void sendEmailPopup(View v)
    {
final EditText emailid;
        Button sendreport;

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

                LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.sendemaillayout, null);
        emailid = (EditText) customView.findViewById(R.id.emailid);
        sendreport = (Button) customView.findViewById(R.id.sendreport);

                builder.setCancelable(true);



                builder.setView(customView);
        dialog = builder.create();

        dialog.show();

                sendreport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String emailstring=emailid.getText().toString();
                        if(Validations.isEmail(emailid))
                        {
                            dialog.dismiss();
                            if(checkPermissionREAD_EXTERNAL_STORAGE(ReportsData.this)) {
                                SendMailTask sendmailtask = new SendMailTask(tabledata, emailstring);
                                sendmailtask.execute();
                            }
                            else
                            {
                                Toast.makeText(ReportsData.this,"Permission Required",Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                });





    }



}
