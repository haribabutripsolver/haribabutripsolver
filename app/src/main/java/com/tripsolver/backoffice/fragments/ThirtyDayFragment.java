package com.tripsolver.backoffice.fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.ActivityContract;
import com.tripsolver.backoffice.model.ActivityCountResponse;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import com.tripsolver.backoffice.model.Session;
import com.tripsolver.backoffice.presenter.ActivityResponsePresenter;
import com.tripsolver.backoffice.view.MainActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 5/3/2019.
 */

public class ThirtyDayFragment extends Fragment  implements ActivityContract.MainView{
    TextView confirmedcounttxt,ticketedcounttxt,shoppingcancelledcounttxt
            ,unconfirmedcounttxt;
    Session sess;
    ActivityCountResponse activitycountresponse;
    ActivityResponsePresenter activityresponsepresenter;
    String returnBodyText;
    LinearLayout confirmedlayout,unconfirmedlayout,cancelledlayout,
            ticketedLayout;

    LinearLayout allbookingslayout;
    TextView allbookingscounttxt;
    int allbookingcount;
    String allbookingcountstr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activitycount,container,false);
        activityresponsepresenter = new ActivityResponsePresenter(this);

        confirmedcounttxt=(TextView)view.findViewById(R.id.confirmedcount);
        ticketedcounttxt=(TextView)view.findViewById(R.id.ticketedcount);
        shoppingcancelledcounttxt=(TextView)view.findViewById(R.id.shoppingcancelledcount);
        unconfirmedcounttxt=(TextView)view.findViewById(R.id.unconfirmedcount);

        allbookingscounttxt=(TextView)view.findViewById(R.id.allbookingscount);
        allbookingslayout=(LinearLayout) view.findViewById(R.id.allbookingslayout);
        unconfirmedcounttxt=(TextView)view.findViewById(R.id.unconfirmedcount);
        confirmedlayout=(LinearLayout)view.findViewById(R.id.confirmedlayout);
        ticketedLayout=(LinearLayout)view.findViewById(R.id.ticketedLayout);

        unconfirmedlayout=(LinearLayout)view.findViewById(R.id.unconfirmedlayout);

        cancelledlayout=(LinearLayout)view.findViewById(R.id.cancelledlayout);

        sess=new Session(getActivity());
        int status = NetworkUtil.getConnectivityStatus(getActivity());

        if (status != 0) {
            callApiService(sess.getAgencyId(),"30");
        }
        else
        {
            noInternetPopUp();
        }
        allbookingslayout=(LinearLayout) view.findViewById(R.id.allbookingslayout);
        allbookingslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allBookingsClick();
            }
        });

        confirmedlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmedClick();
            }
        });
        unconfirmedlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unconfirmedClick();
            }
        });

        cancelledlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelledClick();
            }
        });
        ticketedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketedClick();

            }
        });
        return view;
    }




    public void callApiService(String agencyid, String days) {
       /* ticketbookingpresenter.onButtonClick();*/

        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);

        Call<ResponseBody> call = apiService.activityaccount(days, agencyid);

        // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
        retrofit2.Callback<ResponseBody> callback = new Callback<ResponseBody>() {

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
                       /* JSONArray bookingsarray = new JSONArray();
                        bookingsarray.put(jsonResponsevolley);*/
                        returnBodyText = jsonResponsevolley.toString();
                        Gson gson = new Gson();

                        TypeToken<ActivityCountResponse> typeToken = new TypeToken<ActivityCountResponse>() {
                        };

                        activitycountresponse = gson.fromJson(returnBodyText, typeToken.getType());

                        if (activitycountresponse != null ) {
                            try {
                                bindApiData(activitycountresponse);
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

    public void bindApiData(ActivityCountResponse activitydata)
    {
        String canceelledcount=activitydata.getCancelled();
        String bookingstartedcount=activitydata.getBookingStarted();
        String confirmedcount=activitydata.getConfirmed();
        String ticketedcount=activitydata.getTicketed();
        if(canceelledcount.equals(""))
        {
            canceelledcount="0";
        }
        if(bookingstartedcount.equals(""))
        {
            bookingstartedcount="0";
        }
        if(confirmedcount.equals(""))
        {
            confirmedcount="0";
        }
        if(ticketedcount.equals(""))
        {
            ticketedcount="0";
        }
        try{

            allbookingcount=Integer.parseInt(bookingstartedcount)+Integer.parseInt(canceelledcount)+Integer.parseInt(confirmedcount)+
                    Integer.parseInt(ticketedcount);
            allbookingcountstr=String.valueOf(allbookingcount);

        }
        catch (Exception e)
        {
            allbookingcount=0;
            e.printStackTrace();
        }
        activityresponsepresenter.bindApiData(bookingstartedcount,unconfirmedcounttxt);
        activityresponsepresenter.bindApiData(canceelledcount,shoppingcancelledcounttxt);
        activityresponsepresenter.bindApiData(confirmedcount,confirmedcounttxt);
        activityresponsepresenter.bindApiData(ticketedcount,ticketedcounttxt);
        activityresponsepresenter.bindApiData(allbookingcountstr,allbookingscounttxt);




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

    public void noInternetPopUp()
    {
        final AlertDialog.Builder builder1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder1 = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        } else {
            builder1 = new AlertDialog.Builder(getActivity());
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



    public void allBookingsClick( )
    {
        navigatetoActivity(0);
    }
    public void confirmedClick( )
    {
        navigatetoActivity(1);
    }
    public void cancelledClick( )
    {
        navigatetoActivity(3);

    }
    public void unconfirmedClick( )
    {
        navigatetoActivity(4);

    }

    public void ticketedClick( ){
        navigatetoActivity(2);

    }
    public void navigatetoActivity(int activitytype){
        Bundle b=new Bundle();
        b.putInt("activitytype",activitytype);
        b.putLong("days",30);
        Fragment  f=new MainActivity();
        f.setArguments(b);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, f);
        transaction.commit();
    }
}
