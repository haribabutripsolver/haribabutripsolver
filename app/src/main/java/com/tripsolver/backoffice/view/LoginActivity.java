package com.tripsolver.backoffice.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.MainContract;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import com.tripsolver.backoffice.model.Session;
import com.tripsolver.backoffice.model.Validations;
import com.tripsolver.backoffice.presenter.LoginActivityPresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity implements MainContract.MainView {
    EditText usernameedit, passwordedit;
    Button loginbt;
    TextView forgotpassword;
    String ipaddressvalue;
    String loginresponsetext;
    String b2cidvalue;
    Session sess;
    LoginActivityPresenter ticketbookingpresenter;
    String bofname;
    String agencyid;
    EditText bofedit;
    String logourl;
    ProgressDialog pdia;
    TextView conditionlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginform);
        usernameedit = (EditText) findViewById(R.id.usernameedit);
        passwordedit = (EditText) findViewById(R.id.passwordedit);
        loginbt = (Button) findViewById(R.id.loginbt);
        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
         conditionlink =(TextView)findViewById(R.id.conditionslink);

        ticketbookingpresenter = new LoginActivityPresenter(this);
        bofedit=(EditText)findViewById(R.id.bofname);
        sess=new Session(LoginActivity.this);


    }

    public void loginOnClick(View v) {

        String usernamevalue = usernameedit.getText().toString();
        String passwordvalue = passwordedit.getText().toString();
        bofname=bofedit.getText().toString();
        if (checkValidations()) {
            ticketbookingpresenter.onButtonClick();
            int status = NetworkUtil.getConnectivityStatus(this);

            if (status != 0) {
                callApiService(usernamevalue, passwordvalue, bofname, "");
            }
            else
            {
                noInternetPopUp();
            }
        }
    }

    public void forgotPasswordOnClick(View v) {
    }
public boolean checkValidations()
{
    if (Validations.isEmpty(bofedit.getText().toString(),bofedit , "Agency name")) {

    }

    if (Validations.isEmpty(usernameedit.getText().toString(),usernameedit , "Username")) {

    }
    if (Validations.isEmpty(passwordedit.getText().toString(), passwordedit, "Password")) {
    }
    boolean value=Validations.isEmpty(usernameedit.getText().toString(),usernameedit , "Username")&&
            Validations.isEmpty(passwordedit.getText().toString(), passwordedit, "Password")&&
            Validations.isEmpty(bofedit.getText().toString(), bofedit, "Agency name") ;

    return value;
}
    public void callApiService(String username, String passwordname, String bofname, String userip) {
        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);
        RestApiInterface apiServiceIpaddress =
                RestApiCall.getIpaddressClient().create(RestApiInterface.class);
        Call<ResponseBody> callIpAddress = apiServiceIpaddress.ipAdressResponse("http://icanhazip.com");


        retrofit2.Callback<ResponseBody> callback = new Callback<ResponseBody>() {

            /* When server response. */
            @Override
            public void onResponse(Call<ResponseBody> callIpAddress, Response<ResponseBody> response) {

			/*	hideProgressDialog();*/

                StringBuffer messageBuffer = new StringBuffer();
                int statusCode = response.code();
                if (statusCode == 200) {
                    try {
                        ipaddressvalue = response.body().string();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

			/*	hideProgressDialog();*/

            }
        };

        Call<ResponseBody> call = apiService.loginPost(username, passwordname, bofname, ipaddressvalue);

        // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
        retrofit2.Callback<ResponseBody> logincallback = new Callback<ResponseBody>() {

            /* When server response. */
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

			/*	hideProgressDialog();*/

                StringBuffer messageBuffer = new StringBuffer();
                int statusCode = response.code();
                if (statusCode == 200) {

                    try {
                        // Get return string.
                        loginresponsetext = response.body().string();
                        JSONObject jsonResponsevolley=null;
                        JSONArray bookingsarray=null;
                        try {
                             jsonResponsevolley = new JSONObject(loginresponsetext);

                             bookingsarray = jsonResponsevolley.getJSONArray("Login");
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        // Because return text is a json format string, so we should parse it manually.


                        if (loginresponsetext != null && loginresponsetext.length() > 0) {
                            try {
                                JSONObject loginjsonobj = jsonResponsevolley.getJSONObject("Header");
                                String responsemsg = loginjsonobj.getString("Message");
                                if (responsemsg.equalsIgnoreCase("Successfully Login")) {
                                    b2cidvalue = bookingsarray.getJSONObject(0).getString("b2c_id");
                                    agencyid=bookingsarray.getJSONObject(0).getString("agency_idn");
                                    logourl=  bookingsarray.getJSONObject(0).getString("logo_url");
loginnavigation();
                                } else {
                                    ticketbookingpresenter.servicecalledComplete();
                                    usernameedit.setError("Please check UserName And Password");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

							/*if (registerResponse.isSuccess()) {
                                messageBuffer.append(registerResponse.getMessage());
							} else {
								messageBuffer.append("User register failed.");
							}*/
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
						/*Log.e(TAG_RETROFIT_GET_POST, ex.getMessage());*/
                    }
                } else {
                    // If server return error.
                    ticketbookingpresenter.servicecalledComplete();
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
                ticketbookingpresenter.servicecalledComplete();
            }
        };

        // Use callback object to process web server return data in asynchronous mode.
        callIpAddress.enqueue(callback);
        call.enqueue(logincallback);



    }

    public void loginnavigation()
    {
        sess.setISLogin(true);
        ticketbookingpresenter.servicecalledComplete();
        Intent i=new Intent(LoginActivity.this,AppActivity.class);
        Bundle b=new Bundle();
        b.putString("b2cid",b2cidvalue);
        sess.setAgencyId(b2cidvalue);
        sess.setAgentId(agencyid);
        sess.setBofname(bofname);
        sess.setLogoUrl(logourl);
        i.putExtras(b);
        startActivity(i);

    }


    @Override
    public void showProgress() {
        pdia = new ProgressDialog(LoginActivity.this);
        pdia.setMessage("Loading...");
        pdia.setCancelable(false);
        pdia.show();
    }

    @Override
    public void setData(String value, TextView txtview) {

    }

    @Override
    public void hideProgress() {
if(pdia!=null)
{
    pdia.dismiss();
}
    }

    @Override
    public void changeVisibility(int value, View view) {
        view.setVisibility(value);
    }

    @Override
    public void setQuote(String string) {

    }

    @Override
    public void bindData(String filtervalue, String filtertype) {

    }

    @Override
    public void setFragment(MainActivity fragment) {

    }

    @Override
    public void onBackPressed() {

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
    public void gotoWebTermsandCondtions(View v)
    {
        Uri uriUrl = Uri.parse("https://www.tripsolver.net/terms");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
