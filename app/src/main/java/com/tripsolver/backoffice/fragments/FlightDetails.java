package com.tripsolver.backoffice.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.FaredetContract;
import com.tripsolver.backoffice.model.FlightsDatalistResponse;
import com.tripsolver.backoffice.model.ItineraryDataResponse;
import com.tripsolver.backoffice.model.QuoteBank;
import com.tripsolver.backoffice.presenter.ItineraryResponsePresenter;
import com.tripsolver.backoffice.view.ItineraryInfo;


public class FlightDetails  extends Fragment implements FaredetContract.MainView{
    List<ItineraryDataResponse> itinerarydatalist;
    List<FlightsDatalistResponse> flightdetitinerarydatalist;
RelativeLayout dialogcancellayout;
    TextView    srctimevalue,srcairportvalue   ,srcdatevalue ,srccityvalue,
     elapsedtimevalue, desttimevalue, destairportvalue,airlineflightnameno
    , destcityvalue, destdatevalue, layouttimevalue ,srccitycodevalue,destcitycodevalue/*, srcdatestoptimevalue*/;
    String ttype;
    HashMap<String, String> airlinevaluesmap;
    RelativeLayout infltlayovervalue ,infltheadervalue ;
    RelativeLayout inflatesrcdestvalue;
    LayoutInflater layinflt;
    ImageView headerarrow;
    public static final String mPath = "cityvalues.txt";
    public static final String airlinedatafilePath = "airlinecodes.txt";

    TextView classtypevalue;
    LinearLayout flightdetailsrootlayoutvalue;
    String urlvalue="http://devffv1.tswork1.com/images/airline_logos_new/";
    ArrayList<String> retdepdatevaluesarr,retarrivdatevaluesarr ,retarrivcitycodearr ,retelapsedtimearr ,retairlinenumberarr,airlinenumberarrno,retairlinenumberarrno,
            depdatevaluesarr  ,arrivdatevaluesarr ,arrivcitycodearr, elapsedtimearr ,airlinenumberarr,depcitycodearr,retdepcitycodearr;
   TextView journeytimeheader;
ImageView airlinelogo;
TextView layovercity;

    ArrayList<ArrayList<String>> citylistvalueslist;
    RelativeLayout layoverlayout;
    ItineraryResponsePresenter itineraryresponsepresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flightdetails, container, false);
        itineraryresponsepresenter = new ItineraryResponsePresenter(this);

    flightdetitinerarydatalist=((ItineraryInfo)getActivity()).flightdetitinerarydatalist;;
        srccitycodevalue =(TextView)view.findViewById(R.id.srccitycodeheader);
       destcitycodevalue =(TextView)view.findViewById(R.id.destcitycodeheader);
        airlinelogo=(ImageView)view.findViewById(R.id.airlinelogo);
         headerarrow=(ImageView)view.findViewById(R.id.headerjourneyimg);
        srctimevalue =(TextView)view.findViewById(R.id.srctime);
        srcairportvalue =(TextView)view.findViewById(R.id.srcairport);
        journeytimeheader=(TextView)view.findViewById(R.id.journeytimeheader);
        srcdatevalue=(TextView)view.findViewById(R.id.srcdate);
                srccityvalue=(TextView)view.findViewById(R.id.srccity);
                classtypevalue=(TextView)view.findViewById(R.id.classtypevalue);
/*
        airlinenamefligtdetvalue =(TextView)view.findViewById(R.id.airlinename);
*/
        airlineflightnameno =(TextView)view.findViewById(R.id.airlinenamefligtdet);

        layovercity=(TextView)view.findViewById(R.id.endlable);
        elapsedtimevalue =(TextView)view.findViewById(R.id.elapsedtime);
        desttimevalue=(TextView)view.findViewById(R.id.desttime);
        destairportvalue =(TextView)view.findViewById(R.id.destairport);
        destcityvalue =(TextView)view.findViewById(R.id.destcity);
                destdatevalue=(TextView)view.findViewById(R.id.destdate);
/*
        srcdatestoptimevalue=(TextView)view.findViewById(R.id.srcdatestoptime);
*/
        flightdetailsrootlayoutvalue=(LinearLayout)view.findViewById(R.id.flightdetailsrootlayout);

        layouttimevalue=(TextView)view.findViewById(R.id.layouttime);

        infltlayovervalue =(RelativeLayout)view.findViewById(R.id.infltlayover);
        infltheadervalue=(RelativeLayout)view.findViewById(R.id.infltheader);
                inflatesrcdestvalue=(RelativeLayout) view.findViewById(R.id.inflatesrcdest);
        citylistvalueslist=new ArrayList<>();
        QuoteBank mQuoteBank = new QuoteBank(getActivity());
        List<String> mLines = mQuoteBank.readLine(mPath);
        StringBuffer stringBuffer=new StringBuffer();
        airlinevaluesmap=new HashMap<>();
        getAirlineCodesMatch();
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

        layinflt = (LayoutInflater)getActivity(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for(int j=0;j<flightdetitinerarydatalist.size();j++)
        {

            itinerarydatalist=flightdetitinerarydatalist.get(j).getFlightSegments();
            int flightsegmentssize=itinerarydatalist.size();

            if(j==0&&flightsegmentssize<=1) {
                infltlayovervalue.setVisibility(View.GONE);
            }

            for (int i=0;i<itinerarydatalist.size();i++) {
              String  airlinelogostr=itinerarydatalist.get(i).getOperationalairline();
                String       airlinenamefligtdetstr=airlinevaluesmap.get(itinerarydatalist.get(i).getOperationalairline());
                String  classtypevaluestr=itinerarydatalist.get(i).getClasstype();
                String        srctimestr=itinerarydatalist.get(i).getFlightdetsrcdepttime();
                String srcdatestr=itinerarydatalist.get(i).getSrcdate();
                String       srccitystr=itinerarydatalist.get(i).getSrccityname();
                String srcairportstr=itinerarydatalist.get(i).getSrcairport();

String destcitycodestr=itinerarydatalist.get(i).getDepcitycode();
        String srccitycodestr=itinerarydatalist.get(i).getArrcitycode();
                String     desttimestr=itinerarydatalist.get(i).getFlightdetdestarrvtime();
                String destdatestr=itinerarydatalist.get(i).getArrivdate();
                String      destcitystr=itinerarydatalist.get(i).getArrcityname();
                String destairportstr=itinerarydatalist.get(i).getArrivairport();
                String      elapsedtimestr=itinerarydatalist.get(i).getElapsedtime();
                String      flightnumberstr=itinerarydatalist.get(i).getFlightnumberarr();

                View vinfltheader = layinflt.inflate(R.layout.flightdetheader, null);
                View vinfltbody = layinflt.inflate(R.layout.flightdetailsbody, null);
                View vinfltlayover = layinflt.inflate(R.layout.flightdetailslayover, null);
                boolean pmdepthours=false;
                boolean  pmarrivhours=false;
                if (i == 0&&j==0) {
                    srccitycodevalue =(TextView)view.findViewById(R.id.srccitycodeheader);
                    destcitycodevalue =(TextView)view.findViewById(R.id.destcitycodeheader);
/*
                    srcdatestoptimevalue = (TextView)view.findViewById(R.id.srcdatestoptime);
*/
                    srctimevalue = (TextView) view.findViewById(R.id.srctime);
                    airlinelogo=(ImageView)view.findViewById(R.id.airlinelogo);
                    srcairportvalue = (TextView) view.findViewById(R.id.srcairport);
                    srcdatevalue = (TextView) view.findViewById(R.id.srcdate);
                    srccityvalue = (TextView) view.findViewById(R.id.srccity);
                    journeytimeheader=(TextView)view.findViewById(R.id.journeytimeheader);
                    headerarrow=(ImageView)view.findViewById(R.id.headerjourneyimg);
                    layovercity=(TextView)view.findViewById(R.id.endlable);
                    classtypevalue=(TextView)view.findViewById(R.id.classtypevalue);
                    layoverlayout=(RelativeLayout)view.findViewById(R.id.infltlayover);
/*
                    airlinenamefligtdetvalue = (TextView) view.findViewById(R.id.airlinename);
*/
                    airlineflightnameno =(TextView)view.findViewById(R.id.airlinenamefligtdet);

                    elapsedtimevalue = (TextView) view.findViewById(R.id.elapsedtime);
                    desttimevalue = (TextView) view.findViewById(R.id.desttime);
                    destairportvalue = (TextView) view.findViewById(R.id.destairport);
                    destcityvalue = (TextView) view.findViewById(R.id.destcity);
                    destdatevalue = (TextView) view.findViewById(R.id.destdate);


                    layouttimevalue = (TextView) view.findViewById(R.id.layouttime);
                }
                else
                {
                    journeytimeheader=(TextView)vinfltheader.findViewById(R.id.journeytimeheader);
                    layoverlayout=(RelativeLayout)vinfltlayover.findViewById(R.id.infltlayover);
                    srccitycodevalue =(TextView)vinfltheader.findViewById(R.id.srccitycodeheader);
                    destcitycodevalue =(TextView)vinfltheader.findViewById(R.id.destcitycodeheader);
/*
                    srcdatestoptimevalue = (TextView)vinfltheader.findViewById(R.id.srcdatestoptime);
*/
                    srctimevalue = (TextView)vinfltbody.findViewById(R.id.srctime);
                    airlinelogo=(ImageView)vinfltbody.findViewById(R.id.airlinelogo);
                    srcairportvalue = (TextView) vinfltbody.findViewById(R.id.srcairport);
                    srcdatevalue = (TextView)vinfltbody.findViewById(R.id.srcdate);
                    srccityvalue = (TextView) vinfltbody.findViewById(R.id.srccity);
                    headerarrow=(ImageView)vinfltheader.findViewById(R.id.headerjourneyimg);
                    layovercity=(TextView)vinfltlayover.findViewById(R.id.endlable);
                    classtypevalue=(TextView)vinfltbody.findViewById(R.id.classtypevalue);

/*
                    airlinenamefligtdetvalue = (TextView)vinfltbody.findViewById(R.id.airlinename);
*/
                    airlineflightnameno =(TextView)vinfltbody.findViewById(R.id.airlinenamefligtdet);

                    elapsedtimevalue = (TextView) vinfltbody.findViewById(R.id.elapsedtime);
                    desttimevalue = (TextView) vinfltbody.findViewById(R.id.desttime);
                    destairportvalue = (TextView)vinfltbody.findViewById(R.id.destairport);
                    destcityvalue = (TextView)vinfltbody.findViewById(R.id.destcity);
                    destdatevalue = (TextView)vinfltbody.findViewById(R.id.destdate);

                    layouttimevalue = (TextView) vinfltlayover.findViewById(R.id.layouttime);

                }

                itineraryresponsepresenter.bindApiData(srctimestr,srctimevalue);
                itineraryresponsepresenter.bindApiData(srcdatestr,srcdatevalue);


                itineraryresponsepresenter.bindApiData(destdatestr,destdatevalue);
                itineraryresponsepresenter.bindApiData(desttimestr,desttimevalue);
                itineraryresponsepresenter.bindApiData(destcitycodestr,destcitycodevalue);
                itineraryresponsepresenter.bindApiData(srccitycodestr,srccitycodevalue);

                itineraryresponsepresenter.bindApiData(destcitystr,destcityvalue);
                itineraryresponsepresenter.bindApiData(destairportstr,destairportvalue);
                itineraryresponsepresenter.bindApiData(srccitystr,srccityvalue);
                itineraryresponsepresenter.bindApiData(srcairportstr,srcairportvalue);
                elapsedtimestr  =getElapsedString(elapsedtimestr);


                itineraryresponsepresenter.bindApiData(elapsedtimestr,elapsedtimevalue);


                itineraryresponsepresenter.bindApiData(airlinenamefligtdetstr+" "+flightnumberstr,airlineflightnameno);

                itineraryresponsepresenter.bindApiData(classtypevaluestr,classtypevalue);








try {
    URL u = new URL(urlvalue + airlinelogostr + ".png");
    HttpURLConnection huc = (HttpURLConnection) u.openConnection();
    huc.setRequestMethod("GET");  //OR  huc.setRequestMethod ("HEAD");
    huc.connect();
    int code = huc.getResponseCode();
    if (code == HttpURLConnection.HTTP_OK) {
        Picasso.with(getActivity())
                .load(urlvalue + airlinelogostr + ".png")
                .resize(50, 50)
                .centerCrop()
                .into(airlinelogo);

    } else {
        Picasso.with(getActivity())
                .load("https://www.flipfares.com/images/imagefound.png")
                .resize(50, 50)
                .centerCrop()
                .into(airlinelogo);
    }
}
catch (Exception e)
{
    e.printStackTrace();
}

                if(i<itinerarydatalist.size()-1)
                {
                    String nextstartdepttime=itinerarydatalist.get(i+1).getSrcdatetime();
                    String startarrivaltime=itinerarydatalist.get(i).getDestdatetime();
                    nextstartdepttime=nextstartdepttime.replace("T"," ");
                    startarrivaltime=startarrivaltime.replace("T"," ");;
                    Date nextstartdepdate=null;
                    Date startarrivaldate=null;
                    SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy HHmm");
                    try {
                        nextstartdepdate = dformat.parse(nextstartdepttime);
                        startarrivaldate=dformat.parse(startarrivaltime);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


              long diff = nextstartdepdate.getTime() - startarrivaldate.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000);
                    int diffInDays = (int) ((diff) / (1000 * 60 * 60 * 24));
itineraryresponsepresenter.bindApiData(diffHours+"hr "+diffMinutes+"m",layouttimevalue);
                    itineraryresponsepresenter.bindApiData(" | "+destcityvalue.getText().toString(),layovercity);


                }
                if(i==0)
                {
                    String onewayjourneydate="";
                    SimpleDateFormat dformat = new SimpleDateFormat("dd MMM yyyy");
                    try {

                        itineraryresponsepresenter.bindApiData(itinerarydatalist.get(0).getDepcitycode(),srccitycodevalue);


                       itineraryresponsepresenter.bindApiData(itinerarydatalist.get(itinerarydatalist.size()-1).getArrcitycode(),destcitycodevalue);

                        itineraryresponsepresenter.bindApiData(flightdetitinerarydatalist.get(j).getJourneytime(),journeytimeheader);

                  /*      if(stopstr.equals("2")) {
                            itineraryresponsepresenter.bindApiData(journeydate+ " | "+stopstr+" stops  ",srcdatestoptimevalue);

                        }
                        else if(stopstr.equals("1"))
                        {
                            itineraryresponsepresenter.bindApiData(journeydate+ " | "+stopstr+" stop  ",srcdatestoptimevalue);
                        }
                        else if(stopstr.equals("0"))
                        {
                            layoverlayout.setVisibility(View.GONE);
                        }*/
                        if(j!=0) {

                            flightdetailsrootlayoutvalue.addView(vinfltheader);

                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                if(!(i==0&&j==0))
                {

                    flightdetailsrootlayoutvalue.addView(vinfltbody);

              if(i<itinerarydatalist.size()-1)
              {

                flightdetailsrootlayoutvalue.addView(vinfltlayover);

                }

                }

            }
        }




        flightdetailsrootlayoutvalue.setVisibility(View.VISIBLE);
return view;
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
    public void getAirlineCodesMatch() {

        QuoteBank mQuoteBank = new QuoteBank(getActivity());
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
    public String getElapsedString(String elapsedstr)
    {
        int elapsetimeint=Integer.parseInt(elapsedstr);
        int elapsehrs=elapsetimeint/60;
        int elapsemin=elapsetimeint%60;
        String elapsedhours=String.valueOf(elapsehrs);
        String elapsedmin=String.valueOf(elapsemin);
        return elapsedhours+"hr"+elapsedmin+"min";
    }



}
