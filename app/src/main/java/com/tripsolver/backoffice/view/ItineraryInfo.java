package com.tripsolver.backoffice.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.fragments.FlightDetails;
import com.tripsolver.backoffice.interfacefiles.FaredetContract;
import com.tripsolver.backoffice.model.FlightsDatalistResponse;
import com.tripsolver.backoffice.model.ItineraryDataResponse;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.QuoteBank;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import com.tripsolver.backoffice.presenter.ItineraryResponsePresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItineraryInfo extends AppCompatActivity implements FaredetContract.MainView {
    public static final String mPath = "cityvalues.txt";
    public static final String airlinedatafilePath = "airlinecodes.txt";
String nooftripsstr;
    ArrayList<ArrayList<String>> citylistvalueslist;
    HashMap<String, String> airlinevaluesmap;
    String flightdataresponse;
   public List<ItineraryDataResponse> itinerarydatalist;
    public  List<FlightsDatalistResponse> flightdetitinerarydatalist;
    ProgressDialog pdia;
    NumberFormat formatter;

    ItineraryResponsePresenter itineraryresponsepresenter;
    RecyclerView recyclerView;
    String itinselid,triptype;
    String    validatingcarrier;
    String urlvalue = "http://devffv1.tswork1.com/images/airline_logos_new/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triplistview);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.Itinerarytittle);
    triptype=((TicketDetails)getParent()).triptype;
itinselid=((TicketDetails)getParent()).itinselid;
      /*  itinselid="280322SB391";
        triptype="2";*/
        itineraryresponsepresenter = new ItineraryResponsePresenter(this);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);



        formatter = new DecimalFormat("#0.00");
getNamesFromCodes();
getAirlineCodesMatch();
        int status = NetworkUtil.getConnectivityStatus(this);


        if (status != 0) {
            callApiService();
        }
        else
        {
            noInternetPopUp();
        }
/*
        Bundle bundle = this.getIntent().getBundleExtra("farebundlevalues");*/




    }
    public void callFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FlightDetails hello = new FlightDetails();
        fragmentTransaction.add(R.id.fragment_container, hello, "HELLO");
        fragmentTransaction.commit();
    }

    public void callApiService(){
        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);
        Call<ResponseBody> call;
if(triptype.equalsIgnoreCase("3")||triptype.equalsIgnoreCase("4"))
{
 call = apiService.viewMulticityItinerary(itinselid,triptype);

}
else
{
   call = apiService.viewItinerary(itinselid);

}

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
                        flightdataresponse = response.body().string();
                        flightdataresponse = flightdataresponse.substring(1, flightdataresponse.length() - 1);

                        JSONObject jsonResponse = new JSONObject(flightdataresponse);
storeResponseData(jsonResponse);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }}

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

			/*	hideProgressDialog();*/
                itineraryresponsepresenter.servicecalledComplete();
            }
        };


        call.enqueue(logincallback);



    }

    @Override
    public void showProgress() {
        pdia = new ProgressDialog(ItineraryInfo.this);
        pdia.setMessage("Loading...");
        pdia.setCancelable(false);
        pdia.show();
    }



    @Override
    public void setData(String value,TextView txtview) {

        txtview.setText(value);
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







    public void storeResponseData(JSONObject jsonResponse) {
        try{
    JSONArray jsonMainNode = jsonResponse.getJSONArray("FlightSearchRS");
    JSONObject root = jsonMainNode.getJSONObject(0);
    // now get the first element:
    JSONObject data = root.getJSONObject("Data");
 JSONArray   itineraries = data.getJSONArray("Itineraries");

            JSONObject itinerysobject = itineraries.getJSONObject(0);
    if (itinerysobject != null) {


        final JSONArray itinerary = itinerysobject.getJSONArray("Itinerary");
        JSONObject itineryobject = itinerary.getJSONObject(0);
        validatingcarrier = itineryobject.getString("ValidatingCarrier");

        JSONArray flightsarray = itineryobject.getJSONArray("Flights");
      JSONArray  totalflightsjsonarr=new JSONArray();
        nooftripsstr=String.valueOf(flightsarray.length());
        for (int k = 0; k < flightsarray.length(); k++) {
            JSONObject totalflightsjsonobj=new JSONObject();

            JSONObject totalflightsobject = flightsarray.getJSONObject(k);
String depstopsstr,duration="";
            JSONObject flightarrayobject=null;
            if (totalflightsobject != null) {
                if (totalflightsobject != null) {

                    JSONArray flightarray = totalflightsobject.getJSONArray("Flight");

                     flightarrayobject = flightarray.getJSONObject(0);
                     duration = flightarrayobject.getString("Duration");
                    depstopsstr = flightarrayobject.getString("Stops");

                }
                if (duration.contains(".")) {
                    int days = Integer.parseInt(duration.substring(0, duration.indexOf('.')));
                    int hours = Integer.parseInt(duration.substring(duration.indexOf('.') + 1, duration.indexOf(':')));
                    int min = Integer.parseInt(duration.substring(duration.indexOf(':') + 1, duration.lastIndexOf(':')));
                    hours = hours + days * 24;
                  String  depjourneytimestr = hours + "hrs" + min + "min";
                    totalflightsjsonobj.put("journeytime",depjourneytimestr);
/*
                                            journeytimevalue.setText(hours + "hrs" + min + "min");
*/
                } else {
                    int hours = Integer.parseInt(duration.substring(duration.indexOf('.') + 1, duration.indexOf(':')));
                    int min = Integer.parseInt(duration.substring(duration.indexOf(':') + 1, duration.lastIndexOf(':')));

                    String  depjourneytimestr = hours + "hrs" + min + "min";
                    totalflightsjsonobj.put("journeytime",depjourneytimestr);

                }
                depstopsstr = flightarrayobject.getString("Stops");
                totalflightsjsonobj.put("Stops",depstopsstr);
                totalflightsjsonobj.put("validatingcarrier",validatingcarrier);

                JSONArray FlightSegmentsarray = flightarrayobject.getJSONArray("FlightSegments");
                JSONArray flightarrjson=new JSONArray();
                for (int j = 0; j < FlightSegmentsarray.length(); j++) {
                    JSONObject flightsjsonobj=new JSONObject();
                    boolean pmarrivhours = false, pmdepthours = false;

                    JSONObject FlightSegmentsobject = FlightSegmentsarray.getJSONObject(j);
                    String flightnumber = "";
                    if (FlightSegmentsobject != null) {

                        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        SimpleDateFormat df2 = new SimpleDateFormat("dd");

                        JSONArray FlightSegmentarray = FlightSegmentsobject.getJSONArray("FlightSegment");

                        JSONObject FlightSegmentobject = FlightSegmentarray.getJSONObject(0);
//Displaying from depaturetime and arrivaltime

                        String startdepttime = FlightSegmentobject.getString("DepartureDateTime");
                        flightsjsonobj.put("srcdatetime",startdepttime);

                        flightsjsonobj.put("srcdate",startdepttime);
                        String classtypestr = FlightSegmentobject.getString("classtype");

                        flightsjsonobj.put("classtype",classtypestr);
                        String elapsedstr = FlightSegmentobject.getString("ElapsedTime");

                        int elapsetimeint=Integer.parseInt(elapsedstr);
                        int elapsehrs=elapsetimeint/60;
                        int elapsemin=elapsetimeint%60;
                        String elapsedhours=String.valueOf(elapsehrs);
                        String elapsedmin=String.valueOf(elapsemin);


                        flightsjsonobj.put("ElapsedTime",elapsedhours+"hr"+elapsedmin+"min");


                        String depdate = startdepttime.substring(0, startdepttime.indexOf('T'));
                        String deptime = startdepttime.substring(startdepttime.indexOf('T') + 1, (startdepttime.length()));
                        String hours = deptime.substring(0, 2);
                        String min = deptime.substring(2, (deptime.length()));
                        int inthours = Integer.parseInt(hours);
                        if (inthours >= 12) {
                            pmdepthours = true;
                            if (inthours > 12) {
                                inthours = inthours - 12;
                            }
                        }
                        depdate=depdate.replace('-','/');
                        Date deptdatevalue=null;
                        try {
                            deptdatevalue = df.parse(depdate);
                            String month = month_date.format(deptdatevalue);
                            String formatdate=df2.format(deptdatevalue);
                            month=month.substring(0,3);

                            String datevalue=df.format(deptdatevalue);
                            flightsjsonobj.put("srcdate",datevalue);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                            SimpleDateFormat df3 = new SimpleDateFormat("dd yyyy");


                            depdate = depdate.replace('-', '/');
                            Date d = null;
                            try {
                                d = df.parse(depdate);
                                String month = month_date.format(d);
                                String formatdateheader = df2.format(d);
                                String formatdate = df3.format(d);
                                month = month.substring(0, 3);
                                String week = sdf.format(d);
                                week = week.substring(0, 3);
                                                                       /*  sourcedatevalue.setText(month + "," + formatdate);*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        String depttime;
                        if (pmdepthours) {
                            pmdepthours = false;
                            if (inthours < 10) {
                                depttime="0" + inthours + ":" + min + "PM";
                            } else {
                                depttime=inthours + ":" + min + "PM";
                            }

                        } else {
                            if (inthours < 10) {
                                depttime="0" + inthours + ":" + min + "AM";
                            } else {
                                depttime=inthours + ":" + min + "AM";
                            }

                        }
                            if (j == 0) {


                                flightsjsonobj.put("srcdepttime",depttime);
                            }

                                flightsjsonobj.put("flightdetsrcdepttime",depttime);




                        String startarrivaltime = FlightSegmentobject.getString("ArrivalDateTime");
                        flightsjsonobj.put("destdatetime",startarrivaltime);

                        String arrivaldate = startarrivaltime.substring(0, startarrivaltime.indexOf('T'));



                        String arrivaltime = startarrivaltime.substring(startarrivaltime.indexOf('T') + 1, (startarrivaltime.length()));

                        String arrivhours = arrivaltime.substring(0, 2);
                        String arrivmin = arrivaltime.substring(2, (arrivaltime.length()));
                        int arrivinthours = Integer.parseInt(arrivhours);

                        if (arrivinthours >= 12) {
                            pmarrivhours = true;

                            if (arrivinthours > 12) {
                                arrivinthours = arrivinthours - 12;
                            }
                        }
                        String arrivtime;
                        if (pmarrivhours) {
                            pmarrivhours = false;
                            if (inthours < 10) {
                                arrivtime="0" + arrivinthours + ":" + arrivmin + "PM";
                            } else {
                                arrivtime=arrivinthours + ":" + arrivmin + "PM";
                            }
                        } else {
                            if (inthours < 10) {
                                arrivtime="0" + arrivinthours + ":" + arrivmin + "AM";
                            } else {
                                arrivtime=arrivinthours + ":" + arrivmin + "AM";
                            }
                        }
                        if (j == FlightSegmentsarray.length() - 1) {


                            flightsjsonobj.put("destarrvtime",arrivtime);

                        }

                        flightsjsonobj.put("flightdetdestarrvtime",arrivtime);

                        arrivaldate=arrivaldate.replace('-','/');
                        Date arrivdatevalue=null;
                        try {
                            arrivdatevalue = df.parse(arrivaldate);
                            String month = month_date.format(arrivdatevalue);
                            String formatdate=df2.format(arrivdatevalue);
                            month=month.substring(0,3);

                            String datevalue=df.format(arrivdatevalue);
                            flightsjsonobj.put("arrivdate",datevalue);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                        String stopquantity = FlightSegmentobject.getString("StopQuantity");
                        flightnumber = FlightSegmentobject.getString("FlightNumber");
                        flightsjsonobj.put("FlightNumber",flightnumber);


                        String elapsedtime = FlightSegmentobject.getString("ElapsedTime");
                        flightsjsonobj.put("ElapsedTime",elapsedtime);



                        String availableseats = FlightSegmentobject.getString("AvailableSeats");



                        JSONArray startdepairportarray = FlightSegmentobject.getJSONArray("DepartureAirport");

                        JSONObject startdepairportobject = startdepairportarray.getJSONObject(0);
                        String startdeptlocationcode = startdepairportobject.getString("LocationCode");
                        flightsjsonobj.put("depcitycode",startdeptlocationcode);
                        JSONArray startarrairportarray = FlightSegmentobject.getJSONArray("ArrivalAirport");

                        JSONObject startarrairportobject = startarrairportarray.getJSONObject(0);
                        String startdeptarrivalcode = startarrairportobject.getString("LocationCode");
                        flightsjsonobj.put("arrcitycode",startdeptarrivalcode);

                        for(int c=0;c<citylistvalueslist.size();c++)
                        {
                            ArrayList<String> temparr=new ArrayList<>();
                            temparr= citylistvalueslist.get(c);
                            if(startdeptarrivalcode.equals(temparr.get(0)))
                            {
                                flightsjsonobj.put("arrcityname",temparr.get(2));
                                flightsjsonobj.put("arrivairport",temparr.get(1));

                            }
                            if(startdeptlocationcode.equals(temparr.get(0)))
                            {

                                flightsjsonobj.put("srccityname",temparr.get(2));
                                flightsjsonobj.put("srcairport",temparr.get(1));
                            }



                        }

                        JSONArray startoperation = FlightSegmentobject.getJSONArray("OperatingAirline");
                        JSONObject startoperationobject = startoperation.getJSONObject(0);
                      String  operationairlinecode = startoperationobject.getString("Code");
                        flightsjsonobj.put("operatingairline",operationairlinecode);

                        String operairlineflightnumber = startoperationobject.getString("FlightNumber");
                        flightsjsonobj.put("flightnumberarr",operairlineflightnumber);
                        flightarrjson.put(flightsjsonobj);

                    }
                    totalflightsjsonobj.put("FlightSegments",flightarrjson);
                }
                totalflightsjsonarr.put(totalflightsjsonobj);
            }
        }
        setObjectBinding(totalflightsjsonarr);
    }

        }
    catch (Exception e)
    {
    e.printStackTrace();
    }
}

public void setObjectBinding(JSONArray flightdataarr)
{
    flightdataresponse= flightdataarr.toString();

    Gson gson = new Gson();
    TypeToken<List<FlightsDatalistResponse>> flighttypeToken = new TypeToken<List<FlightsDatalistResponse>>() {
    };


    TypeToken<List<ItineraryDataResponse>> typeToken = new TypeToken<List<ItineraryDataResponse>>() {
    };

    flightdetitinerarydatalist = gson.fromJson(flightdataresponse, flighttypeToken.getType());


    setItinerayLayoutValues();
    callFragment();
}
        @Override
        public void onDestroy () {
            super.onDestroy();

        }
        public void getNamesFromCodes()
        {
            citylistvalueslist=new ArrayList<>();
            QuoteBank mQuoteBank = new QuoteBank(ItineraryInfo.this);
            List<String> mLines = mQuoteBank.readLine(mPath);
            StringBuffer stringBuffer=new StringBuffer();


            for (String string : mLines)
                stringBuffer.append(string);
            try {
                JSONObject jsonResponse = new JSONObject(stringBuffer.toString());
                JSONArray jsonMainNode = jsonResponse.optJSONArray("Flightdet");
                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    ArrayList<String> cityvalue = new ArrayList<String>();

                    String citycode = jsonChildNode.optString("AirportCode1").toString();
                    String cityname = jsonChildNode.optString("CityName").toString();
                    String airportname = jsonChildNode.optString("AirportName").toString();
                    String country = jsonChildNode.optString("Country").toString();
                    cityvalue.add(citycode);
                    cityvalue.add(airportname);
                    cityvalue.add(cityname);
                    cityvalue.add(country);
                    citylistvalueslist.add(cityvalue);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
  public void getAirlineCodesMatch() {
      airlinevaluesmap=new HashMap<>();
      QuoteBank mQuoteBank = new QuoteBank(ItineraryInfo.this);
      List<String> mLines = mQuoteBank.readLine(airlinedatafilePath);
      StringBuffer stringBuffer=new StringBuffer();


      for (String string : mLines)
          stringBuffer.append(string);
      try {
          JSONObject jsonResponse = new JSONObject(stringBuffer.toString());
          JSONArray jsonMainNode = jsonResponse.optJSONArray("AirlineCode");
          int lengthJsonArr = jsonMainNode.length();
          for (int i = 0; i < lengthJsonArr; i++) {
              JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
              ArrayList<String> cityvalue = new ArrayList<String>();

              String airlinecode = jsonChildNode.getString("AirlineCode1");
              String airlinename = jsonChildNode.optString("AirlineShort_Name").toString();
                  /*  if(airlinecode.contains(".")) {
                        airlinecode = airlinecode.substring(0, airlinecode.indexOf('.'));
                    }*/
              airlinevaluesmap.put(airlinecode, airlinename);

          }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    }

        public void setItinerayLayoutValues()
        {
            View view;
            LinearLayout addinflatelayout = (LinearLayout) findViewById(R.id.itinerarylayout);

            final LayoutInflater layinflt = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        if(nooftripsstr.equalsIgnoreCase("1"))
                        {
                            view = layinflt.inflate(R.layout.onelayoutitin, null);
                            ImageView airlinelogo;
                            TextView airlinename,sourcetime, sourcecity, desttime, destination, journeytime, stops;
                            String airlinenamestr,sourcetimestr,sourcedate,arrivdate, sourcecitystr, desttimestr, destinationstr
                                    , journeytimestr, stopsstr,airlinecodestr,sourcecityname,arrivcityname;

                            airlinelogo = (ImageView) view.findViewById(R.id.airlinelogo);
                            airlinename = (TextView) view.findViewById(R.id.airlinename);

                            sourcetime = (TextView) view.findViewById(R.id.sourcetime);
                            sourcecity = (TextView) view.findViewById(R.id.sourcecity);
                            desttime = (TextView) view.findViewById(R.id.desttime);
                            destination = (TextView) view.findViewById(R.id.destination);
                            journeytime = (TextView) view.findViewById(R.id.journeytime);
                            stops = (TextView) view.findViewById(R.id.stops);


                            stopsstr=flightdetitinerarydatalist.get(0).getStops();

                           journeytimestr= flightdetitinerarydatalist.get(0).getJourneytime();
                            itinerarydatalist = flightdetitinerarydatalist.get(0).getFlightSegments();

                            airlinecodestr=flightdetitinerarydatalist.get(0).getValidatecarrier();

                            int flightsegmentssize = itinerarydatalist.size();
                            sourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            sourcecitystr= itinerarydatalist.get(0).getDepcitycode();

                            destinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            desttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();
                            airlinenamestr= airlinevaluesmap.get(airlinecodestr);

                            sourcedate=itinerarydatalist.get(0).getSrcdate();
                            arrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();

                            sourcecityname=itinerarydatalist.get(0).getSrccityname();
                            arrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();



                            itineraryresponsepresenter.bindApiData(stopsstr+ "stop(s)",stops);
                            itineraryresponsepresenter.bindApiData(journeytimestr,journeytime);
                            itineraryresponsepresenter.bindApiData(airlinenamestr,airlinename);

                            itineraryresponsepresenter.bindApiData(sourcecityname+", "+sourcecitystr,sourcecity);
                            itineraryresponsepresenter.bindApiData(arrivcityname+", "+destinationstr,destination);

                            itineraryresponsepresenter.bindApiData(sourcedate+", "+sourcetimestr,sourcetime);
                            itineraryresponsepresenter.bindApiData(arrivdate+", "+desttimestr,desttime);


                            Picasso.with(this)
                                    .load(urlvalue + airlinecodestr + ".png")
                                    .resize(50, 50)
                                    .centerCrop()
                                    .into(airlinelogo);
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);

                            addinflatelayout.addView(view);

                        }
                      else if(nooftripsstr.equalsIgnoreCase("2")) {
                        view = layinflt.inflate(R.layout.twolayoutitin, null);
                            ImageView airlinelogo;
                            TextView airlinename, sourcetime, sourcecity, desttime, destination, journeytime, stops;
                            String airlinenamestr,sourcetimestr, sourcecitystr, desttimestr, destinationstr
                                    , journeytimestr, stopsstr,airlinecodestr,sourcedate,arrivdate,sourcecityname,arrivcityname ;

                        TextView secondsourcetime, secondsourcecity,  seconddesttime, seconddestination, secondjourneytime,secondstops;
                            String secondsourcetimestr, secondsourcecitystr, seconddesttimestr,
                                    seconddestinationstr,secondjourneytimestr,secondstopsstr,secondsourcedate,secondarrivdate,
                                    secondarrivcityname,secondsourcecityname;

                        airlinelogo = (ImageView) view.findViewById(R.id.airlinelogo);
                        airlinename = (TextView) view.findViewById(R.id.airlinename);

                        sourcetime = (TextView) view.findViewById(R.id.sourcetime);
                        sourcecity = (TextView) view.findViewById(R.id.sourcecity);
                        desttime = (TextView) view.findViewById(R.id.desttime);
                        destination = (TextView) view.findViewById(R.id.destination);
                        journeytime = (TextView) view.findViewById(R.id.journeytime);
                        stops = (TextView) view.findViewById(R.id.stops);

                        secondsourcetime = (TextView) view.findViewById(R.id.secondsourcetime);
                            secondsourcecity = (TextView) view.findViewById(R.id.secondsourcecity);
                            seconddesttime = (TextView) view.findViewById(R.id.seconddesttime);
                            seconddestination = (TextView) view.findViewById(R.id.seconddestination);
                            secondjourneytime= (TextView) view.findViewById(R.id.secondjourneytime);
                                    secondstops= (TextView) view.findViewById(R.id.secondstops);

                            stopsstr=flightdetitinerarydatalist.get(0).getStops();
                            journeytimestr= flightdetitinerarydatalist.get(0).getJourneytime();
                            itinerarydatalist = flightdetitinerarydatalist.get(0).getFlightSegments();

                            airlinecodestr=flightdetitinerarydatalist.get(0).getValidatecarrier();

                            int flightsegmentssize = itinerarydatalist.size();
                            sourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            sourcecitystr= itinerarydatalist.get(0).getDepcitycode();
                            sourcecityname=itinerarydatalist.get(0).getSrccityname();
                            arrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();


                            destinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            desttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();
                            airlinenamestr= airlinevaluesmap.get(airlinecodestr);
                            sourcedate=itinerarydatalist.get(0).getSrcdate();
                            arrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();


                            itineraryresponsepresenter.bindApiData(stopsstr+ "stop(s)",stops);
                            itineraryresponsepresenter.bindApiData(journeytimestr,journeytime);
                            itineraryresponsepresenter.bindApiData(airlinenamestr,airlinename);
                            itineraryresponsepresenter.bindApiData(sourcecityname+", "+sourcecitystr,sourcecity);
                            itineraryresponsepresenter.bindApiData(arrivcityname+", "+destinationstr,destination);


                            itineraryresponsepresenter.bindApiData(sourcedate+", "+sourcetimestr,sourcetime);
                            itineraryresponsepresenter.bindApiData(arrivdate+", "+desttimestr,desttime);


                            Picasso.with(this)
                                    .load(urlvalue + airlinecodestr + ".png")
                                    .resize(50, 50)
                                    .centerCrop()
                                    .into(airlinelogo);
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);


                            itinerarydatalist = flightdetitinerarydatalist.get(1).getFlightSegments();
                            secondjourneytimestr= flightdetitinerarydatalist.get(1).getJourneytime();
                                    secondstopsstr= flightdetitinerarydatalist.get(1).getStops();

                             flightsegmentssize = itinerarydatalist.size();
                            secondsourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            secondsourcecitystr= itinerarydatalist.get(0).getDepcitycode();

                            seconddestinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            seconddesttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();

                            secondsourcedate=itinerarydatalist.get(0).getSrcdate();
                            secondarrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();
                            secondsourcecityname=itinerarydatalist.get(0).getSrccityname();
                            secondarrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();



                            itineraryresponsepresenter.bindApiData(secondsourcecityname+", "+secondsourcecitystr,secondsourcecity);
                            itineraryresponsepresenter.bindApiData(secondarrivcityname+", "+seconddestinationstr,seconddestination);

                            itineraryresponsepresenter.bindApiData(secondjourneytimestr,secondjourneytime);
                            itineraryresponsepresenter.bindApiData(secondstopsstr+ "stop(s)",secondstops);

                            itineraryresponsepresenter.bindApiData(secondsourcedate+", "+secondsourcetimestr,secondsourcetime);
                            itineraryresponsepresenter.bindApiData(secondarrivdate+", "+seconddesttimestr,seconddesttime);

                            addinflatelayout.addView(view);

                    }
                   else if(nooftripsstr.equalsIgnoreCase("3")) {
                        view = layinflt.inflate(R.layout.threewaylayout, null);

                            ImageView airlinelogo;
                            TextView airlinename, sourcetime, sourcecity, desttime, destination, journeytime, stops;
                            String airlinenamestr,sourcetimestr, sourcecitystr, desttimestr, destinationstr
                                    , journeytimestr, stopsstr,airlinecodestr,sourcedate,arrivdate,sourcecityname,arrivcityname;

                            TextView secondsourcetime, secondsourcecity,  seconddesttime, seconddestination,
                                    secondjourneytime,secondstops;
                            String secondsourcetimestr, secondsourcecitystr, seconddesttimestr,secondsourcecityname,
                                    seconddestinationstr,secondjourneytimestr,secondstopsstr,secondsourcedate,secondarrivdate
                                    ,secondarrivcityname;
                            TextView thirdsourcetime, thirdsourcecity, thirddesttime, thirddestination,thirdjourneytime,thirdstops;
                            String thirdsourcetimestr, thirdsourcecitystr,
                                    thirddesttimestr, thirddestinationstr,thirdjourneytimestr,thirdstopsstr,thirdsourcecityname,
                                    thirdsourcedate,thirdarrivdate,thirdarrivcityname;
                            airlinelogo = (ImageView) view.findViewById(R.id.airlinelogo);
                            airlinename = (TextView) view.findViewById(R.id.airlinename);

                            sourcetime = (TextView) view.findViewById(R.id.sourcetime);
                            sourcecity = (TextView) view.findViewById(R.id.sourcecity);
                            desttime = (TextView) view.findViewById(R.id.desttime);
                            destination = (TextView) view.findViewById(R.id.destination);
                            journeytime = (TextView) view.findViewById(R.id.journeytime);
                            stops = (TextView) view.findViewById(R.id.stops);

                            secondsourcetime = (TextView) view.findViewById(R.id.secondsourcetime);
                            secondsourcecity = (TextView) view.findViewById(R.id.secondsourcecity);
                            seconddesttime = (TextView) view.findViewById(R.id.seconddesttime);
                            seconddestination = (TextView) view.findViewById(R.id.seconddestination);
                            secondjourneytime= (TextView) view.findViewById(R.id.secondjourneytime);
                            secondstops= (TextView) view.findViewById(R.id.secondstops);

                            thirdsourcetime = (TextView) view.findViewById(R.id.thirdsourcetime);
                            thirdsourcecity = (TextView) view.findViewById(R.id.thirdsourcecity);
                            thirddesttime = (TextView) view.findViewById(R.id.thirddesttime);
                            thirddestination = (TextView) view.findViewById(R.id.thirddestination);
                            thirdjourneytime= (TextView) view.findViewById(R.id.thirdjourneytime);
                            thirdstops= (TextView) view.findViewById(R.id.thirdstops);

                            stopsstr=flightdetitinerarydatalist.get(0).getStops();
                            journeytimestr= flightdetitinerarydatalist.get(0).getJourneytime();
                            itinerarydatalist = flightdetitinerarydatalist.get(0).getFlightSegments();

                            airlinecodestr=flightdetitinerarydatalist.get(0).getValidatecarrier();

                            int flightsegmentssize = itinerarydatalist.size();
                            sourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            sourcecitystr= itinerarydatalist.get(0).getDepcitycode();

                            sourcedate=itinerarydatalist.get(0).getSrcdate();
                            arrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();

                            destinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            desttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();
                            airlinenamestr= airlinevaluesmap.get(airlinecodestr);

                            sourcecityname=itinerarydatalist.get(0).getSrccityname();
                            arrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();



                            itineraryresponsepresenter.bindApiData(stopsstr+ "stop(s)",stops);
                            itineraryresponsepresenter.bindApiData(journeytimestr,journeytime);
                            itineraryresponsepresenter.bindApiData(airlinenamestr,airlinename);

                            itineraryresponsepresenter.bindApiData(sourcecityname+", "+sourcecitystr,sourcecity);
                            itineraryresponsepresenter.bindApiData(arrivcityname+", "+destinationstr,destination);

                            itineraryresponsepresenter.bindApiData(sourcedate+", "+sourcetimestr,sourcetime);
                            itineraryresponsepresenter.bindApiData(arrivdate+", "+desttimestr,desttime);


                            Picasso.with(this)
                                    .load(urlvalue + airlinecodestr + ".png")
                                    .resize(50, 50)
                                    .centerCrop()
                                    .into(airlinelogo);
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);


                            itinerarydatalist = flightdetitinerarydatalist.get(1).getFlightSegments();
                            secondjourneytimestr= flightdetitinerarydatalist.get(1).getJourneytime();
                            secondstopsstr= flightdetitinerarydatalist.get(1).getStops();

                            flightsegmentssize = itinerarydatalist.size();
                            secondsourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            secondsourcecitystr= itinerarydatalist.get(0).getDepcitycode();

                            seconddestinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            seconddesttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();

                            secondsourcedate=itinerarydatalist.get(0).getSrcdate();
                            secondarrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();
                            secondsourcecityname=itinerarydatalist.get(0).getSrccityname();
                            secondarrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();



                            itineraryresponsepresenter.bindApiData(secondsourcecityname+", "+secondsourcecitystr,secondsourcecity);
                            itineraryresponsepresenter.bindApiData(secondarrivcityname+", "+seconddestinationstr,seconddestination);

                            itineraryresponsepresenter.bindApiData(secondjourneytimestr,secondjourneytime);
                            itineraryresponsepresenter.bindApiData(secondstopsstr+ "stop(s)",secondstops);

                            itineraryresponsepresenter.bindApiData(secondsourcedate+", "+secondsourcetimestr,secondsourcetime);
                            itineraryresponsepresenter.bindApiData(secondarrivdate+", "+seconddesttimestr,seconddesttime);



                            itinerarydatalist = flightdetitinerarydatalist.get(2).getFlightSegments();
                            thirdjourneytimestr= flightdetitinerarydatalist.get(2).getJourneytime();
                            thirdstopsstr= flightdetitinerarydatalist.get(2).getStops();

                            flightsegmentssize = itinerarydatalist.size();
                            thirdsourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            thirdsourcecitystr= itinerarydatalist.get(0).getDepcitycode();

                            thirddestinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            thirddesttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();
                            thirdsourcecityname=itinerarydatalist.get(0).getSrccityname();
                            thirdarrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();

                            thirdsourcedate=itinerarydatalist.get(0).getSrcdate();
                            thirdarrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();



                            itineraryresponsepresenter.bindApiData(thirdsourcecityname+", "+thirdsourcecitystr,thirdsourcecity);
                            itineraryresponsepresenter.bindApiData(thirdarrivcityname+", "+thirddestinationstr,thirddestination);

                            itineraryresponsepresenter.bindApiData(thirdjourneytimestr,thirdjourneytime);
                            itineraryresponsepresenter.bindApiData(thirdstopsstr+ "stop(s)",thirdstops);

                            itineraryresponsepresenter.bindApiData(thirdsourcedate+", "+thirdsourcetimestr,thirdsourcetime);
                            itineraryresponsepresenter.bindApiData(thirdarrivdate+", "+thirddesttimestr,thirddesttime);



                            addinflatelayout.addView(view);

                    }
                        else if(nooftripsstr.equalsIgnoreCase("4")) {
                        view = layinflt.inflate(R.layout.fourwaylayout, null);

                            ImageView airlinelogo;
                            TextView airlinename, sourcetime, sourcecity, desttime, destination, journeytime, stops;
                            String airlinenamestr,sourcetimestr, sourcecitystr, desttimestr, destinationstr
                                    , journeytimestr, stopsstr,airlinecodestr,sourcedate,arrivdate,sourcecityname,arrivcityname;

                            TextView secondsourcetime, secondsourcecity,  seconddesttime, seconddestination,secondjourneytime,secondstops;
                            String secondsourcetimestr, secondsourcecitystr, seconddesttimestr,
                                    seconddestinationstr,
                            secondjourneytimestr,secondstopsstr,secondsourcedate,secondarrivdate,secondsourcecityname,secondarrivcityname;
                            TextView thirdsourcetime, thirdsourcecity, thirddesttime, thirddestination,thirdjourneytime,thirdstops;
                            String thirdsourcetimestr, thirdsourcecitystr,
                                    thirddesttimestr, thirddestinationstr,thirdjourneytimestr,thirdstopsstr,thirdsourcedate,thirdarrivdate
                                    ,thirdsourcecityname,thirdarrivcityname;
                            TextView fourthsourcetime, fourthsourcecity, fourthdesttime, fourthdestination,fourthjourneytime,fourthstops;
                            String fourthsourcetimestr, fourthsourcecitystr, fourthdesttimestr,
                                    fourthdestinationstr,fourthjourneytimestr,fourthstopsstr,fourthsourcedate,fourtharrivdate,
                                    fourthsourcecityname,fourtharrivcityname;
                            airlinelogo = (ImageView) view.findViewById(R.id.airlinelogo);
                            airlinename = (TextView) view.findViewById(R.id.airlinename);

                            sourcetime = (TextView) view.findViewById(R.id.sourcetime);
                            sourcecity = (TextView) view.findViewById(R.id.sourcecity);
                            desttime = (TextView) view.findViewById(R.id.desttime);
                            destination = (TextView) view.findViewById(R.id.destination);
                            journeytime = (TextView) view.findViewById(R.id.journeytime);
                            stops = (TextView) view.findViewById(R.id.stops);

                            secondsourcetime = (TextView) view.findViewById(R.id.secondsourcetime);
                            secondsourcecity = (TextView) view.findViewById(R.id.secondsourcecity);
                            seconddesttime = (TextView) view.findViewById(R.id.seconddesttime);
                            seconddestination = (TextView) view.findViewById(R.id.seconddestination);
                            secondjourneytime= (TextView) view.findViewById(R.id.secondjourneytime);
                            secondstops= (TextView) view.findViewById(R.id.secondstops);


                            thirdsourcetime = (TextView) view.findViewById(R.id.thirdsourcetime);
                            thirdsourcecity = (TextView) view.findViewById(R.id.thirdsourcecity);
                            thirddesttime = (TextView) view.findViewById(R.id.thirddesttime);
                            thirddestination = (TextView) view.findViewById(R.id.thirddestination);
                            thirdjourneytime= (TextView) view.findViewById(R.id.thirdjourneytime);
                            thirdstops= (TextView) view.findViewById(R.id.thirdstops);

                            fourthsourcetime = (TextView) view.findViewById(R.id.foursourcetime);
                            fourthsourcecity = (TextView) view.findViewById(R.id.foursourcecity);
                            fourthdesttime = (TextView) view.findViewById(R.id.fourdesttime);
                            fourthdestination = (TextView) view.findViewById(R.id.fourdestination);
                            fourthjourneytime= (TextView) view.findViewById(R.id.fourjourneytime);
                            fourthstops= (TextView) view.findViewById(R.id.fourstops);



                            stopsstr=flightdetitinerarydatalist.get(0).getStops();
                            journeytimestr= flightdetitinerarydatalist.get(0).getJourneytime();
                            itinerarydatalist = flightdetitinerarydatalist.get(0).getFlightSegments();

                            airlinecodestr=flightdetitinerarydatalist.get(0).getValidatecarrier();
                            int flightsegmentssize = itinerarydatalist.size();

                            arrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();

                            sourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            sourcecitystr= itinerarydatalist.get(0).getDepcitycode();
                            sourcecityname=itinerarydatalist.get(0).getSrccityname();

                            destinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            desttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();
                            airlinenamestr= airlinevaluesmap.get(airlinecodestr);

                            sourcedate=itinerarydatalist.get(0).getSrcdate();
                            arrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();



                            itineraryresponsepresenter.bindApiData(stopsstr+ "stop(s)",stops);
                            itineraryresponsepresenter.bindApiData(journeytimestr,journeytime);
                            itineraryresponsepresenter.bindApiData(airlinenamestr,airlinename);

                            itineraryresponsepresenter.bindApiData(sourcecityname+", "+sourcecitystr,sourcecity);
                            itineraryresponsepresenter.bindApiData(arrivcityname+", "+destinationstr,destination);

                            itineraryresponsepresenter.bindApiData(sourcedate+", "+sourcetimestr,sourcetime);
                            itineraryresponsepresenter.bindApiData(arrivdate+", "+desttimestr,desttime);

                            Picasso.with(this)
                                    .load(urlvalue + airlinecodestr + ".png")
                                    .resize(50, 50)
                                    .centerCrop()
                                    .into(airlinelogo);
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);


                            itinerarydatalist = flightdetitinerarydatalist.get(1).getFlightSegments();
                            secondjourneytimestr= flightdetitinerarydatalist.get(1).getJourneytime();
                            secondstopsstr= flightdetitinerarydatalist.get(1).getStops();
                            secondarrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();

                            flightsegmentssize = itinerarydatalist.size();
                            secondsourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            secondsourcecitystr= itinerarydatalist.get(0).getDepcitycode();

                            seconddestinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            seconddesttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();
                            secondsourcecityname=itinerarydatalist.get(0).getSrccityname();
                            secondsourcedate=itinerarydatalist.get(0).getSrcdate();
                            secondarrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();




                            itineraryresponsepresenter.bindApiData(secondsourcecityname+", "+secondsourcecitystr,secondsourcecity);                            itineraryresponsepresenter.bindApiData(seconddestinationstr,seconddestination);

                            itineraryresponsepresenter.bindApiData(secondarrivcityname+", "+seconddestinationstr,seconddestination);
                            itineraryresponsepresenter.bindApiData(secondjourneytimestr,secondjourneytime);
                            itineraryresponsepresenter.bindApiData(secondstopsstr+ "stop(s)",secondstops);
                            itineraryresponsepresenter.bindApiData(secondsourcedate+", "+secondsourcetimestr,secondsourcetime);
                            itineraryresponsepresenter.bindApiData(secondarrivdate+", "+seconddesttimestr,seconddesttime);

                            itinerarydatalist = flightdetitinerarydatalist.get(2).getFlightSegments();
                            thirdjourneytimestr= flightdetitinerarydatalist.get(2).getJourneytime();
                            thirdstopsstr= flightdetitinerarydatalist.get(2).getStops();

                            flightsegmentssize = itinerarydatalist.size();
                            thirdsourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            thirdsourcecitystr= itinerarydatalist.get(0).getDepcitycode();

                            thirddestinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            thirddesttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();

                            thirdsourcecityname=itinerarydatalist.get(0).getSrccityname();

                            thirdsourcedate=itinerarydatalist.get(0).getSrcdate();
                            thirdarrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();
                            thirdarrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();

                            itineraryresponsepresenter.bindApiData(thirdsourcecityname+", "+thirdsourcecitystr,thirdsourcecity);                            itineraryresponsepresenter.bindApiData(thirddestinationstr,thirddestination);

                            itineraryresponsepresenter.bindApiData(thirdjourneytimestr,thirdjourneytime);
                            itineraryresponsepresenter.bindApiData(thirdstopsstr+ "stop(s)",thirdstops);
                            itineraryresponsepresenter.bindApiData(thirdarrivcityname+", "+thirddestinationstr,thirddestination);
                            itineraryresponsepresenter.bindApiData(thirdsourcedate+", "+thirdsourcetimestr,thirdsourcetime);
                            itineraryresponsepresenter.bindApiData(thirdarrivdate+", "+thirddesttimestr,thirddesttime);


                            itinerarydatalist = flightdetitinerarydatalist.get(3).getFlightSegments();
                            fourthjourneytimestr= flightdetitinerarydatalist.get(3).getJourneytime();
                            fourthstopsstr= flightdetitinerarydatalist.get(3).getStops();


                            flightsegmentssize = itinerarydatalist.size();
                            fourthsourcetimestr= itinerarydatalist.get(0).getSrcdepttime();
                            fourthsourcecitystr= itinerarydatalist.get(0).getDepcitycode();
                            fourthsourcecityname=itinerarydatalist.get(0).getSrccityname();

                            fourthdestinationstr=  itinerarydatalist.get(flightsegmentssize - 1).getArrcitycode();
                            fourthdesttimestr= itinerarydatalist.get(flightsegmentssize - 1).getDestarrvtime();

                            fourthsourcedate=itinerarydatalist.get(0).getSrcdate();
                            fourtharrivdate=itinerarydatalist.get(flightsegmentssize - 1).getArrivdate();
                            fourtharrivcityname=itinerarydatalist.get(flightsegmentssize - 1).getArrcityname();


                            itineraryresponsepresenter.bindApiData(fourthsourcecityname+", "+fourthsourcecitystr,fourthsourcecity);                            itineraryresponsepresenter.bindApiData(fourthdestinationstr,fourthdestination);

                            itineraryresponsepresenter.bindApiData(fourthjourneytimestr,fourthjourneytime);
                            itineraryresponsepresenter.bindApiData(fourthstopsstr+ "stop(s)",fourthstops);


                            itineraryresponsepresenter.bindApiData(fourtharrivcityname+", "+fourthdestinationstr,fourthdestination);
                            itineraryresponsepresenter.bindApiData(fourthsourcedate+", "+fourthsourcetimestr,fourthsourcetime);
                            itineraryresponsepresenter.bindApiData(fourtharrivdate+", "+fourthdesttimestr,fourthdesttime);


                            addinflatelayout.addView(view);

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

        }

