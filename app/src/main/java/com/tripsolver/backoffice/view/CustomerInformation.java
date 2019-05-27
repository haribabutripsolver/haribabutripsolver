package com.tripsolver.backoffice.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.tripsolver.backoffice.Adapters.PassengerListAdapter;
import com.tripsolver.backoffice.interfacefiles.PassengerDetContract;
import com.tripsolver.backoffice.model.CustomerDataResponse;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.PassengerDataResponse;
import com.tripsolver.backoffice.model.PaymenInfoResponse;
import com.tripsolver.backoffice.model.Session;
import com.tripsolver.backoffice.presenter.PassengerdetResponsePresenter;
import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adilsoomro on 8/19/16.
 */
public class CustomerInformation extends AppCompatActivity implements PassengerDetContract.MainView {
    List<PassengerDataResponse> passengerdatalist;
    String custdetresponse, passendetresponse, paymentinforesponse, bookingid, returnBodyText;
    public List<CustomerDataResponse> customerdatalist;
    List<PaymenInfoResponse> paymentinfolist;
    RecyclerView recyclerView;
    TextView noresultsfoundtext;
    PassengerdetResponsePresenter passengerdetpresenter;
    TextView custfnamevaluetxt, custlnamevaluetxt, phonevaluetxt, emailvaluetxt;
    ProgressDialog pdia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passengerinformation);
        Bundle bundle=getIntent().getExtras();
        bookingid=bundle.getString("bookingid");
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        passengerdetpresenter = new PassengerdetResponsePresenter(this);
        custfnamevaluetxt = (TextView) findViewById(R.id.custfnamevalue);
        custlnamevaluetxt = (TextView) findViewById(R.id.custlnamevalue);
        phonevaluetxt = (TextView) findViewById(R.id.phonevalue);
        emailvaluetxt = (TextView) findViewById(R.id.emailvalue);
        passengerdatalist = new ArrayList<>();
        Session sess = new Session(this);
        int status = NetworkUtil.getConnectivityStatus(this);


        if (status != 0) {
            callApiService(bookingid, sess.getAgencyId());
        }
        else
        {
            noInternetPopUp();
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.customertittle);



    }

    public void callApiService(String bookingid, String b2cid) {
        passengerdetpresenter.onButtonClick();
        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);

        Call<ResponseBody> call = apiService.passengerdetService(bookingid, b2cid);

        // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
        retrofit2.Callback<ResponseBody> callback = new Callback<ResponseBody>() {

            /* When server response. */
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

			/*	hideProgressDialog();*/
                passengerdetpresenter.servicecalledComplete();
                StringBuffer messageBuffer = new StringBuffer();
                int statusCode = response.code();
                if (statusCode == 200) {

                    try {
                        // Get return string.
                        returnBodyText = response.body().string();
                        returnBodyText=returnBodyText.substring(1,returnBodyText.length()-1);

                        JSONObject jsonResponsevolley = new JSONObject(returnBodyText);
                        JSONArray contactdetarray = jsonResponsevolley.getJSONArray("Contactinf");
                        custdetresponse = contactdetarray.toString();
                        JSONArray passendetresponsearray = jsonResponsevolley.getJSONArray("paxinfo");
                        passendetresponse = passendetresponsearray.toString();

                        JSONArray carddetresparray = jsonResponsevolley.getJSONArray("cardinfo");
                        paymentinforesponse = carddetresparray.toString();


                        Gson gson = new Gson();

                        TypeToken<List<PassengerDataResponse>> passentoken = new TypeToken<List<PassengerDataResponse>>() {
                        };

                        TypeToken<List<CustomerDataResponse>> customertoken = new TypeToken<List<CustomerDataResponse>>() {
                        };


                        TypeToken<List<PaymenInfoResponse>> paymentinfotoken = new TypeToken<List<PaymenInfoResponse>>() {
                        };

                        passengerdatalist = gson.fromJson(passendetresponse, passentoken.getType());
                        customerdatalist = gson.fromJson(custdetresponse, customertoken.getType());
                        ((TicketDetails) getParent()).paymentinfolist = gson.fromJson(paymentinforesponse, paymentinfotoken.getType());


                        if (passengerdatalist != null && passengerdatalist.size() > 0) {
                            try {
                                callRecyclerAdapter(passengerdatalist);
                                bindCustData();

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

                Toast.makeText(CustomerInformation.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        // Use callback object to process web server return data in asynchronous mode.
        call.enqueue(callback);


    }

    public void callRecyclerAdapter(List<PassengerDataResponse> passendatalist) {
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



    }


    @Override
    public void showProgress() {
        pdia = new ProgressDialog(CustomerInformation.this);
        pdia.setMessage("Loading...");
        pdia.setCancelable(false);
        pdia.show();
    }

    @Override
    public void setData(String value, TextView txtview) {
txtview.setText(value);
    }

    @Override
    public void hideProgress() {
pdia.dismiss();
    }

    @Override
    public void changeVisibility(int value, View view) {

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
}
