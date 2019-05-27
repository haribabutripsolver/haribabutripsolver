package com.tripsolver.backoffice.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.tripsolver.backoffice.Adapters.TicketBookingsAdapter;
import com.tripsolver.backoffice.interfacefiles.BookingDetailsFragmentContract;
import com.tripsolver.backoffice.interfacefiles.FragmentNavigation;
import com.tripsolver.backoffice.interfacefiles.MainContract;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.Session;
import com.tripsolver.backoffice.model.Validations;
import com.tripsolver.backoffice.presenter.FragmentNavPresenter;
import com.tripsolver.backoffice.presenter.TicketBookingResponsePresenter;
import com.tripsolver.backoffice.view.AppActivity;
import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import com.tripsolver.backoffice.model.TicketBookingsResponse;
import com.tripsolver.backoffice.view.MainActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adilsoomro on 8/19/16.
 */
public class TicketedFragment extends Fragment implements FragmentNavigation.MainView{
    List<TicketBookingsResponse> ticketlists;
    String returnBodyText = "";
    public List<TicketBookingsResponse> ticketbookingresponse;
    RecyclerView recyclerView;
    ProgressDialog pdia;
    String filtertype,filtervalue;
    TextView noresultsfoundtext;
    boolean isapicalled;
    SwipeRefreshLayout pullToRefresh;
    EditText searchedittxt;
    List<TicketBookingsResponse> filterticketbookresponse;
    ArrayList<String> itemlist;
    Session sess;
    Spinner spinneredit;
    int totalbookingscount;
    android.app.AlertDialog dialog;

    long activitytype=30;

    int activitytypeintvalue=0;
    boolean isLoading = false;
    FragmentNavPresenter ticketbookingpresenter;
    TicketBookingsAdapter mAdapter;
    int loadlimit=30;
    TextView sevendayactivity,thirtydayactivity,sixtydayactivity,totalcount,clearfilter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.brolticketlist, container, false);
        Bundle b=getArguments();
        activitytype=b.getLong("days");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        noresultsfoundtext = (TextView) view.findViewById(R.id.noresultsfound);
        sevendayactivity = (TextView) view.findViewById(R.id.sevendayactivity);
        thirtydayactivity = (TextView) view.findViewById(R.id.thirtydayactivity);
        sixtydayactivity = (TextView) view.findViewById(R.id.sixtydayactivity);

        totalcount = (TextView) view.findViewById(R.id.totalcount);

        clearfilter = (TextView) view.findViewById(R.id.clearfilter);

        ticketbookingpresenter = new FragmentNavPresenter(this);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        initScrollListener();
        ticketlists = new ArrayList<>();
        sess=new Session(getActivity());
        isapicalled=((AppActivity)getActivity()).iscancelledcall;

        if(!isapicalled)
        {
            int status = NetworkUtil.getConnectivityStatus(getActivity());
            if (status != 0) {
                callApiService(sess.getAgencyId(), "Ticketed");
            }
            else
            {
                noInternetPopUp();
            }

        }

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApiService(sess.getAgencyId(), "Ticketed");
                // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        activitytypeintvalue=(int)activitytype;
        switch (activitytypeintvalue)
        {
            case 7:
                sevendayactivity.setPaintFlags(sevendayactivity.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

                thirtydayactivity.setPaintFlags(thirtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));

                sixtydayactivity.setPaintFlags(sixtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                break;
            case 30:
                thirtydayactivity.setPaintFlags(thirtydayactivity.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                sevendayactivity.setPaintFlags(sevendayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                sixtydayactivity.setPaintFlags(sixtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                break;
            case 60:
                sixtydayactivity.setPaintFlags(sevendayactivity.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                thirtydayactivity.setPaintFlags(thirtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                sevendayactivity.setPaintFlags(thirtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                break;
        }


        clearfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindData("","");
                ticketbookingpresenter.setVisibleValue(View.GONE,clearfilter);
            }
        });

        sevendayactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketbookingpresenter.onButtonClick();
                activitytype=7;
                CallApiAsyncTaskRunner callapiasync=new CallApiAsyncTaskRunner();
                callapiasync.execute();

                sevendayactivity.setPaintFlags(sevendayactivity.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

                thirtydayactivity.setPaintFlags(thirtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                sixtydayactivity.setPaintFlags(sixtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));


            }
        });
        thirtydayactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketbookingpresenter.onButtonClick();
                activitytype=30;
                CallApiAsyncTaskRunner callapiasync=new CallApiAsyncTaskRunner();
                callapiasync.execute();
                thirtydayactivity.setPaintFlags(thirtydayactivity.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                sevendayactivity.setPaintFlags(sevendayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                sixtydayactivity.setPaintFlags(sixtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));

            }
        });

        sixtydayactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketbookingpresenter.onButtonClick();
                activitytype=60;
                CallApiAsyncTaskRunner callapiasync=new CallApiAsyncTaskRunner();
                callapiasync.execute();
                sixtydayactivity.setPaintFlags(sevendayactivity.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                thirtydayactivity.setPaintFlags(thirtydayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                sevendayactivity.setPaintFlags(sevendayactivity.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
            }
        });

        return view;
    }

    public void callApiService(String agencyid, String tickettype) {
        ticketbookingpresenter.onButtonClick();

        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);

        Call<ResponseBody> call = apiService.savePost(agencyid, tickettype);

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
                        if(jsonResponsevolley.getJSONObject("Header").getString("Message").equals("Data Not Found"))
                        {
                            ticketbookingpresenter.setVisibleValue(1,noresultsfoundtext);

                            ticketbookingresponse=new ArrayList<>();
                            CallApiAsyncTaskRunner callapiasync=new CallApiAsyncTaskRunner();
                            callapiasync.execute();

                        }
                        else{
                            JSONArray bookingsarray = jsonResponsevolley.getJSONArray("Bookingactivites");
                            returnBodyText = bookingsarray.toString();
                            Gson gson = new Gson();

                            TypeToken<List<TicketBookingsResponse>> typeToken = new TypeToken<List<TicketBookingsResponse>>() {
                            };
                            ticketbookingresponse=new ArrayList<>();

                            ticketbookingresponse = gson.fromJson(returnBodyText, typeToken.getType());

                            //get count of total bookings


                            // Filter response by activity types

                            List<TicketBookingsResponse> tempticketbookingresponse=getActivityTypeBookings();
                            if (tempticketbookingresponse != null && tempticketbookingresponse.size() > 0) {
                                try {
                                    CallApiAsyncTaskRunner callapiasync=new CallApiAsyncTaskRunner();
                                    callapiasync.execute();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            else
                            {
                                ticketbookingpresenter.servicecalledComplete();
                                ticketbookingpresenter.setVisibleValue(1,noresultsfoundtext);
                            }

                        /*if (registerResponse.isSuccessca()) {
								messageBuffer.append(registerResponse.getMessage());
							} else {
								messageBuffer.append("User register failed.");
							}*/
                        }

                    } catch (Exception ex) {
                        /*Log.e(TAG_RETROFIT_GET_POST, ex.getMessage());*/
                    }
                } else {
                    ticketbookingpresenter.servicecalledComplete();

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

    public void callRecyclerAdapter(List<TicketBookingsResponse> ticketBookingsResponse) {
        loadlimit=30;
        filterticketbookresponse = new ArrayList<>();

        filterticketbookresponse=ticketBookingsResponse;



        if (filterticketbookresponse != null&&filterticketbookresponse.size()>0) {
            isapicalled=true;
            recyclerView.setVisibility(View.VISIBLE);
            noresultsfoundtext.setVisibility(View.INVISIBLE);
            if(filterticketbookresponse.size()<=loadlimit)
            {
                loadlimit=filterticketbookresponse.size();
            }


            mAdapter = new TicketBookingsAdapter(filterticketbookresponse,getActivity(),"Ticketed",R.layout.brolticketed,loadlimit);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            noresultsfoundtext.setVisibility(View.VISIBLE);
        }
        ticketbookingpresenter.servicecalledComplete();

    }







    @Override
    public void showProgress() {

        pdia = new ProgressDialog(getActivity());
        pdia.setMessage("Loading...");
        pdia.setCancelable(false);
        pdia.show();

    }

    @Override
    public void setData(String value, TextView txtview) {
        txtview.setText(value);
    }

    @Override
    public void atachPresenter(FragmentNavigation.Presenter presenter) {

    }


    @Override
    public void hideProgress() {
        if(pdia!=null)
        {
            pdia.dismiss();
        }
    }

    @Override
    public void setFragment(MainActivity fragment) {

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
        if(mAdapter!=null) {
        this.filtertype=filtertype;
        this.filtervalue=filtervalue;
        mAdapter.setFiltertype(filtertype);
        mAdapter.getFilter().filter(filtervalue);
        }

    }
    public void setFilterValues(String filtervalue, String filtertype) {
        if(mAdapter!=null) {
            this.filtertype = filtertype;
            this.filtervalue = filtervalue;
            mAdapter.setFiltertype(filtertype);
            mAdapter.getFilter().filter(filtervalue);
            ticketbookingpresenter.setVisibleValue(View.VISIBLE, clearfilter);
        }


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


    public void filterResults( )
    {
        if(checkValidations())
        {
            ticketbookingpresenter.bindFilterData(filtervalue,filtertype);
        }

    }

    public boolean checkValidations()
    {
        if (Validations.isEmpty(searchedittxt.getText().toString(),searchedittxt , "search value")) {

        }


        boolean value=Validations.isEmpty(searchedittxt.getText().toString(),searchedittxt , "search value");


        return value;
    }


    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemposition= linearLayoutManager.findLastCompletelyVisibleItemPosition()+1;
                if (!isLoading) {
                    if (linearLayoutManager != null && itemposition == (loadlimit-1)) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        /* rowsArrayList.add(null);*/
        filterticketbookresponse.add(loadlimit,null);
        mAdapter.setLoadlimit(loadlimit+1);

        mAdapter.notifyItemInserted(loadlimit);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                filterticketbookresponse.remove(loadlimit);
                int scrollPosition = loadlimit;
                mAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

             /*   while (currentSize - 1 < nextLimit) {
                    rowsArrayList.add("Item " + currentSize);
                    currentSize++;
                }*/
                loadlimit=mAdapter.getItemCount()+30;
                if(filterticketbookresponse.size()>=loadlimit) {
                    mAdapter.setLoadlimit(loadlimit);
                }
                else {
                    mAdapter.setLoadlimit(filterticketbookresponse.size());
                }
                mAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);


    }







    public boolean getIsDaysActivity(String datevalue,long daysvalue,Date todaydate){
        String bookingcreatedstr="";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
            Date createddate=   sdf.parse(datevalue);
            long diff =todaydate.getTime()-createddate.getTime();

            long diffDays = diff / (24 * 60 * 60 * 1000);

            if(diffDays<=daysvalue)
            {
                return true;

            }





        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public List<TicketBookingsResponse> getActivityTypeBookings()
    {    List<TicketBookingsResponse> tempticketbookingresponse=null;

        try {
            tempticketbookingresponse = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());

            String todaydatestr = sdf.format(new Date());
            Date todaydate = sdf.parse(todaydatestr);
            for (TicketBookingsResponse ticketbookingobj : ticketbookingresponse) {
                String datevalue = ticketbookingobj.getCreateddatetime();
                // Check booking is in activity type
                if (getIsDaysActivity(datevalue, activitytype,todaydate)) {
                    tempticketbookingresponse.add(ticketbookingobj);
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tempticketbookingresponse;
    }

    private class CallApiAsyncTaskRunner extends AsyncTask<String, String, String> {
        List<TicketBookingsResponse> ticketbookresp;
        private CallApiAsyncTaskRunner()
        {
        }
        @Override
        protected String doInBackground(String... params) {
            try {


                ticketbookresp=getActivityTypeBookings();


            }  catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {

            totalbookingscount=ticketbookresp.size();


            ticketbookingpresenter.bindApiData("Bookings: "+totalbookingscount,totalcount);


            callRecyclerAdapter(ticketbookresp);

            // execution of result of Long time consuming operation

        }


        @Override
        protected void onPreExecute() {


        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }

    /*public class FooRequest {
        final String b2c_idn;
       final String ti

        FooRequest(String b2c_idn, String Fromdate,String Todate,String status,String source) {
            this.b2c_idn=b2c_idn;
            this.Fromdate=Fromdate;
            this.Todate=Todate;
            this.status=status;
            this.source=source;
        }
    }*/
}
