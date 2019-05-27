package com.tripsolver.backoffice.view;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.MyProfileContract;
import com.tripsolver.backoffice.model.MyProfileDataResponse;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import com.tripsolver.backoffice.model.Session;
import com.tripsolver.backoffice.presenter.MyProfileResponsePresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 5/1/2019.
 */

public class MyProfile extends AppCompatActivity implements MyProfileContract.MainView {
    MyProfileResponsePresenter myProfileresponsepresenter;
    String myprofileresponse;
    public List<MyProfileDataResponse> myprofiledatalist;
   TextView passengertittlefnamevalue,passengertittlemnamevalue,passengertittlelnamevalue,phonenovalue
    ,tollfreenovalue,passengerfaxnovalue,emailvalue,contactrolevalue,streetvalue
    ,branchvalue,cityvalue,statevalue,countryvalue,backofficename,agencyurlvalue,helpdeskvalue;
    String agency_id;
    Session sess;
    String agentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);
        sess=new Session(this);
        agency_id=sess.getAgencyId();
        agentid=sess.getAgentId();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        actionBar.setTitle(R.string.myprofile);
        myProfileresponsepresenter=new MyProfileResponsePresenter(this);
        //intialize textviews
        passengertittlefnamevalue=(TextView)findViewById(R.id.passengertittlefnamevalue);

                passengertittlemnamevalue=(TextView)findViewById(R.id.passengertittlemnamevalue);

        passengertittlelnamevalue=(TextView)findViewById(R.id.passengertittlelnamevalue);

        phonenovalue=(TextView)findViewById(R.id.phonenovalue);

        tollfreenovalue=(TextView)findViewById(R.id.tollfreenovalue);

        passengerfaxnovalue=(TextView)findViewById(R.id.passengerfaxnovalue);
        emailvalue=(TextView)findViewById(R.id.emailvalue);
        contactrolevalue=(TextView)findViewById(R.id.contactrolevalue);

        streetvalue=(TextView)findViewById(R.id.streetvalue);

        branchvalue=(TextView)findViewById(R.id.branchvalue);

        cityvalue=(TextView)findViewById(R.id.cityvalue);

        statevalue=(TextView)findViewById(R.id.statevalue);

        countryvalue=(TextView)findViewById(R.id.countryvalue);

        backofficename=(TextView)findViewById(R.id.backofficename);

        agencyurlvalue=(TextView)findViewById(R.id.agencyurlvalue);

        helpdeskvalue=(TextView)findViewById(R.id.helpdeskvalue);
        int status = NetworkUtil.getConnectivityStatus(this);

        if (status != 0) {
            callApiService();

        }
        else
        {
            noInternetPopUp();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void setData(String value, TextView txtview) {
txtview.setText(value);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void changeVisibility(int value, View view) {

    }

    @Override
    public void setQuote(String string) {

    }
    public void callApiService(){
        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);

        Call<ResponseBody> call = apiService.myprofilepost(agentid);

        // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
        Callback<ResponseBody> logincallback = new Callback<ResponseBody>() {

            /* When server response. */
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

			/*	hideProgressDialog();*/

                StringBuffer messageBuffer = new StringBuffer();
                int statusCode = response.code();
                if (statusCode == 200) {

                    try {
                        // Get return string.
                        myprofileresponse = response.body().string();



                       /* faredetresponse = farejsondata.toString();*/


                        JSONObject jsonResponsevolley = new JSONObject(myprofileresponse);
                        JSONArray bookingsarray = jsonResponsevolley.getJSONArray("AgencyProfile");
                        myprofileresponse = bookingsarray.toString();
                        Gson gson = new Gson();

                        TypeToken<List<MyProfileDataResponse>> typeToken = new TypeToken<List<MyProfileDataResponse>>() {
                        };

                        myprofiledatalist = gson.fromJson(myprofileresponse, typeToken.getType());

                        if (myprofiledatalist != null && myprofiledatalist.size() > 0) {
                            try {
                              bindApiData(myprofiledatalist);

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
                    myProfileresponsepresenter.servicecalledComplete();
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
                myProfileresponsepresenter.servicecalledComplete();
            }
        };


        call.enqueue(logincallback);

    }

    public void bindApiData(List<MyProfileDataResponse> myprofiledatalist)
    {
        MyProfileDataResponse myprofiledataresponse=myprofiledatalist.get(0);


        String passengertittlefnamevaluestr,passengertittlemnamevaluestr,passengertittlelnamevaluestr,phonenovaluestr
                ,tollfreenovaluestr,passengerfaxnovaluestr,emailvaluestr,contactrolevaluestr,streetvaluestr
                ,branchvaluestr,cityvaluestr,statevaluestr,countryvaluestr,backofficenamestr,agencyurlvaluestr,helpdeskvaluestr;


        passengertittlefnamevaluestr= myprofiledataresponse.getFirstname();
        passengertittlemnamevaluestr=  myprofiledataresponse.getMiddlename();
        passengertittlelnamevaluestr =myprofiledataresponse.getLastname();
        phonenovaluestr= myprofiledataresponse.getPhone();
        tollfreenovaluestr = myprofiledataresponse.getTollfreenumber();
        passengerfaxnovaluestr =myprofiledataresponse.getFaxnumber();
        emailvaluestr  =myprofiledataresponse.getEmail();
        contactrolevaluestr= myprofiledataresponse.getContactrole();
        streetvaluestr = myprofiledataresponse.getStreet();
        branchvaluestr = myprofiledataresponse.getBranch();
        cityvaluestr = myprofiledataresponse.getCity();
        statevaluestr = myprofiledataresponse.getState();
        countryvaluestr=myprofiledataresponse.getCountry();
        backofficenamestr=myprofiledataresponse.getBackofficename();
        agencyurlvaluestr=myprofiledataresponse.getAgencyurl();
        helpdeskvaluestr=myprofiledataresponse.getHelpdesklink();
        myProfileresponsepresenter.bindApiData(passengertittlefnamevaluestr,passengertittlefnamevalue);
        myProfileresponsepresenter.bindApiData(passengertittlemnamevaluestr,passengertittlemnamevalue);
        myProfileresponsepresenter.bindApiData(passengertittlelnamevaluestr,passengertittlelnamevalue);
        myProfileresponsepresenter.bindApiData(phonenovaluestr,phonenovalue);
        myProfileresponsepresenter.bindApiData(tollfreenovaluestr,tollfreenovalue);
        myProfileresponsepresenter.bindApiData(passengerfaxnovaluestr,passengerfaxnovalue);
        myProfileresponsepresenter.bindApiData(emailvaluestr,emailvalue);
        myProfileresponsepresenter.bindApiData(contactrolevaluestr,contactrolevalue);
        myProfileresponsepresenter.bindApiData(streetvaluestr,streetvalue);
        myProfileresponsepresenter.bindApiData(branchvaluestr,branchvalue);
        myProfileresponsepresenter.bindApiData(cityvaluestr,cityvalue);
        myProfileresponsepresenter.bindApiData(statevaluestr,statevalue);
        myProfileresponsepresenter.bindApiData(countryvaluestr,countryvalue);
        myProfileresponsepresenter.bindApiData(backofficenamestr,backofficename);
        myProfileresponsepresenter.bindApiData(agencyurlvaluestr,agencyurlvalue);
        myProfileresponsepresenter.bindApiData(helpdeskvaluestr,helpdeskvalue);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            // Android home
            case android.R.id.home:
/*
                drawerLayout.openDrawer(GravityCompat.START);
*/
                finish();
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }
    public void noInternetPopUp()
    {
        final AlertDialog.Builder builder1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder1 = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        } else {
            builder1 = new AlertDialog.Builder(this);
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
}
