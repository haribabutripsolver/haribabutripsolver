package com.tripsolver.backoffice.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.FaredetContract;
import com.tripsolver.backoffice.model.FareDetResponse;
import com.tripsolver.backoffice.model.NetworkUtil;
import com.tripsolver.backoffice.model.RestApiCall;
import com.tripsolver.backoffice.model.RestApiInterface;
import com.tripsolver.backoffice.presenter.FaredetResponsePresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FareDetails extends AppCompatActivity implements FaredetContract.MainView {
RelativeLayout dialogcancellayout;
/*
    String adultbasefare,childbasefare,infantbasefare,infantbasefarelp,adulttax,grandtotal,childtax,infanttax,infanttaxlp;
*/
    TextView basefare,grandtotalvalue,basefarevalue,tax,taxvalue,childbasefaretext,childgrandtotalvalue,childbasefarevalue,childtaxtext,childtaxvalue
    ,infantbasefaretext,infantgrandtotalvalue,infantbasefarevalue,infanttaxtext,infanttaxvalue;
   TextView infantbasefaretextlp,infantgrandtotalvaluelp,infantbasefarevaluelp,infanttaxtextlp,infanttaxvaluelp;
    RelativeLayout basefarelayout,taxlayout,discountlayout,
    childbasefarelayout,childtaxlayout,childdiscountlayout,
            infantbasefarelayout,infanttaxlayout,infantdiscountlayout, infantbasefarelayoutlp,infanttaxlayoutlp;
    JSONArray faredetjsonarr;
String faredetresponse;
List<FareDetResponse> faredetresplist;
    ProgressDialog pdia;
    NumberFormat formatter;

 TextView totaldiscountvaluetxt;
 TextView  srccity,destcity,farevalue,farerules;
    String retjourneydatebundle,srcjourneydatebundle;
String discountvalue,giftamnt,giftimageurl;
TextView giftamnttxt;
ArrayList<String> individualdiscvaluesarr;
String itid;
boolean isrepricing;
   Double totalfarevalue;
RelativeLayout totaldiscountlayoutrel;
LinearLayout rootdiscountlayout;
    Double discdouble;
    Double totalvalue;
    RelativeLayout fareruleslayout;
    TextView discount, discountvaluetxt,
    childdiscount ,childdiscountvalue,
    infantdiscount ,infantdiscountvalue;
    FaredetResponsePresenter faredetPresenter;
    RecyclerView recyclerView;
    String itinselid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faredetails);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.faretittle);
        Bundle bundle=getIntent().getExtras();
        itinselid=bundle.getString("itinselid");
        faredetPresenter = new FaredetResponsePresenter(this);

        basefare = (TextView)findViewById(R.id.basefare);
/*
        headerfarevalue = (TextView) view.findViewById(R.id.farevalue);
*/
        basefarevalue = (TextView) findViewById(R.id.basefarevalue);
        tax = (TextView) findViewById(R.id.tax);
        taxvalue = (TextView) findViewById(R.id.taxvalue);
        basefarelayout = (RelativeLayout) findViewById(R.id.basefarevaluelayout);
        taxlayout = (RelativeLayout) findViewById(R.id.taxlayout);
        discountlayout = (RelativeLayout) findViewById(R.id.discountlayout);
        grandtotalvalue = (TextView) findViewById(R.id.grandtotalvalue);

        childbasefarevalue = (TextView) findViewById(R.id.childbasefarevalue);
        childtaxtext = (TextView) findViewById(R.id.childtax);
        childtaxvalue = (TextView) findViewById(R.id.childtaxvalue);
        childbasefarelayout = (RelativeLayout) findViewById(R.id.childbasefarevaluelayout);
        childtaxlayout = (RelativeLayout) findViewById(R.id.childtaxlayout);
        childdiscountlayout = (RelativeLayout) findViewById(R.id.childdiscountlayout);

        childbasefaretext = (TextView) findViewById(R.id.childbasefare);
        giftamnttxt=(TextView)findViewById(R.id.giftamnt);
        infantbasefarevalue = (TextView) findViewById(R.id.infantbasefarevalue);
        infanttaxtext = (TextView) findViewById(R.id.infanttax);
        infanttaxvalue = (TextView)findViewById(R.id.infanttaxvalue);
        infantbasefarelayout = (RelativeLayout) findViewById(R.id.infantbasefarevaluelayout);
        infanttaxlayout = (RelativeLayout) findViewById(R.id.infanttaxlayout);
        infantdiscountlayout = (RelativeLayout) findViewById(R.id.infantdiscountlayout);
        infantbasefaretext = (TextView) findViewById(R.id.infantbasefare);
/*        fareruleslayout = (RelativeLayout) view.findViewById(R.id.fareruleslayout);*/
        totaldiscountlayoutrel = (RelativeLayout) findViewById(R.id.totaldiscountlayout);
        rootdiscountlayout=(LinearLayout) findViewById(R.id.rootdiscountlayout);
        infantbasefarevaluelp = (TextView)findViewById(R.id.infantbasefarevaluelp);
        infanttaxtextlp = (TextView) findViewById(R.id.infanttaxlp);
        infanttaxvaluelp = (TextView) findViewById(R.id.infanttaxlpvalue);
        infantbasefarelayoutlp = (RelativeLayout)findViewById(R.id.infantbasefarevaluelayoutlp);
        infanttaxlayoutlp = (RelativeLayout) findViewById(R.id.infanttaxlayoutlp);
        infantbasefaretextlp = (TextView) findViewById(R.id.infantbasefarelp);
        totaldiscountvaluetxt = (TextView) findViewById(R.id.totaldiscountvalue);
       /* headerarrow = (ImageView) view.findViewById(R.id.headerarrow);
        srccity = (TextView) view.findViewById(R.id.srccity);
        destcity = (TextView) view.findViewById(R.id.destcity);
        farevalue = (TextView) view.findViewById(R.id.farevalue);
        farerules = (TextView) view.findViewById(R.id.farerules);*/


        discount  = (TextView) findViewById(R.id.discount);
                discountvaluetxt = (TextView) findViewById(R.id.discountvalue);
        childdiscount  = (TextView) findViewById(R.id.childdiscount);
                childdiscountvalue = (TextView) findViewById(R.id.childdiscountvalue);
        infantdiscount = (TextView) findViewById(R.id.infantdiscount);
                infantdiscountvalue = (TextView) findViewById(R.id.infantdiscountvalue);


         formatter = new DecimalFormat("#0.00");

        int status = NetworkUtil.getConnectivityStatus(this);


        if (status != 0) {
            callApiService();
        }
        else
        {
            noInternetPopUp();
        }/*
        Bundle bundle = this.getIntent().getBundleExtra("farebundlevalues");*/




    }

    public void callApiService(){
        RestApiInterface apiService =
                RestApiCall.getClient().create(RestApiInterface.class);

        Call<ResponseBody> call = apiService.faredetPost(itinselid);

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
                        faredetresponse = response.body().string();
                        faredetresponse=faredetresponse.substring(1,faredetresponse.length()-1);

                        JSONObject jsonResponsevolley = new JSONObject(faredetresponse);

                     /* JSONArray farejsonarray   = jsonResponsevolley.getJSONArray("FareBreakdowns");
                        JSONObject faredetjsonobj   =   farejsonarray.getJSONObject(0);
                     JSONArray faredetjsonarr  =   faredetjsonobj.getJSONArray("FareBreakdown");*/
                        JSONArray farebreakdownsarray = jsonResponsevolley.getJSONArray("FareBreakdowns");
                        JSONArray farejsonarr=new JSONArray();

                        for(int i=0;i<farebreakdownsarray.length();i++) {
                            JSONObject farejsondata=new JSONObject();
                            JSONObject farebreakdownsobject = farebreakdownsarray.getJSONObject(i);
                            JSONArray farebreakdownarray = farebreakdownsobject.getJSONArray("FareBreakdown");

                            JSONObject farebreakdownobject = farebreakdownarray.getJSONObject(0);


                            String passentype = farebreakdownobject.getString("Type");
                            farejsondata.put("Type",passentype);
                            String passentypecode= farebreakdownobject.getString("PaxTypeCode");
                            farejsondata.put("PaxTypeCode",passentypecode);
                            String quantitystr = farebreakdownobject.getString("Quantity");
                            farejsondata.put("Quantity",quantitystr);
                            JSONArray basefarearray = farebreakdownobject.getJSONArray("BaseFare");
                            JSONObject basefareobject = basefarearray.getJSONObject(0);
                            String basefare = basefareobject.getString("Value");
                            farejsondata.put("BaseFare",basefare);
                            JSONArray taxarray = farebreakdownobject.getJSONArray("Tax");
                            JSONObject taxobject = taxarray.getJSONObject(0);
                            String taxfare = taxobject.getString("Value");
                            farejsondata.put("Tax",taxfare);

                            JSONArray discountarray = farebreakdownobject.getJSONArray("Discount");

                            JSONObject discountobject = discountarray.getJSONObject(0);
                            String discountfare = discountobject.getString("Value");
                            farejsondata.put("Discount",discountfare);

                            JSONArray totalarray = farebreakdownobject.getJSONArray("Total");
                            JSONObject totalobject = totalarray.getJSONObject(0);

                            String totalfare = totalobject.getString("Value");
                            farejsondata.put("Total",totalfare);
                            farejsonarr.put(farejsondata);
                        }


                       /* faredetresponse = farejsondata.toString();*/


                        faredetresponse= farejsonarr.toString();

                        Gson gson = new Gson();

                        TypeToken<List<FareDetResponse>> typeToken = new TypeToken<List<FareDetResponse>>() {
                        };

                        faredetresplist = gson.fromJson(faredetresponse, typeToken.getType());


                        if (faredetresplist != null && faredetresplist.size() > 0) {
                            try {

bindDataLogic();
                               /* } else {
                                    faredetPresenter.servicecalledComplete();
                                   *//* usernameedit.setError("Please check UserName And Password");*//*
                                }*/

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
                    faredetPresenter.servicecalledComplete();
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
                faredetPresenter.servicecalledComplete();
                Toast.makeText(FareDetails.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };


        call.enqueue(logincallback);



    }

    @Override
    public void showProgress() {
        pdia = new ProgressDialog(FareDetails.this);
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


    public void bindDataLogic() {
        Double basefareval, taxfarevalue, grandtotaldiscount=0.0,grandtotalfare=0.0,totalfarevalue;

        for (FareDetResponse faredetresponse : faredetresplist) {
            //Declaration of values
            int quantityvalue=Integer.parseInt(faredetresponse.getQuantity());
            String passengertype="Adult";
            if(faredetresponse.getPaxcodetype().equalsIgnoreCase("ADT"))
            {
                passengertype="Adult";
            }
            else   if(faredetresponse.getPaxcodetype().equalsIgnoreCase("CHD"))
            {
                passengertype="Child";
            }
            else if(faredetresponse.getPaxcodetype().equalsIgnoreCase("INS")){
passengertype="Infant";
            }
            else if(faredetresponse.getPaxcodetype().equalsIgnoreCase("INL")){
                passengertype="InfantonLap";
            }
            basefareval=Double.parseDouble(faredetresponse.getBaseFare());
            taxfarevalue=Double.parseDouble(faredetresponse.getTax());
            totalfarevalue=Double.parseDouble(faredetresponse.getTotal());
            grandtotalfare=grandtotalfare+(quantityvalue*totalfarevalue);
            grandtotaldiscount=grandtotaldiscount+Double.parseDouble(faredetresponse.getDiscount());




            Double totalbasefare = basefareval * ((double)quantityvalue);

            totalbasefare = BigDecimal.valueOf(totalbasefare)
                    .doubleValue();

            Double totaltaxfare = taxfarevalue* ((double)quantityvalue);

             totaltaxfare = BigDecimal.valueOf(totaltaxfare)
                    .doubleValue();

            totalfarevalue = BigDecimal.valueOf(totalfarevalue)
                    .doubleValue();

            switch (passengertype){
                case "Adult":

                    // Displaying adult fare values

                    if (basefareval==null||basefareval.equals("0.00")||basefareval.equals("0")||basefareval.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",basefare);
                    else
                        faredetPresenter.bindApiData( (quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + basefareval + ")",basefare);


                    if (taxfarevalue==null||taxfarevalue.equals("0.00")||taxfarevalue.equals("0")||taxfarevalue.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",tax);

                    else
                    faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + taxfarevalue + ")",tax);



                    if (totalbasefare==null||totalbasefare.equals("0.00")||totalbasefare.equals("0")||totalbasefare.equals("null"))
                        faredetPresenter.bindApiData("--",basefarevalue);



                    else
                    faredetPresenter.bindApiData("$" + formatter.format((totalbasefare)),basefarevalue);


                    if (totaltaxfare==null||totaltaxfare.equals("0.00")||totaltaxfare.equals("0")||totaltaxfare.equals("null"))
                        faredetPresenter.bindApiData("--",taxvalue);


                    else
                    faredetPresenter.bindApiData("$" + formatter.format((totaltaxfare)),taxvalue);




                    break;

                case "Child":
                    changeVisibility(View.VISIBLE,childbasefarelayout);
                    changeVisibility(View.VISIBLE,childtaxlayout);

                    //Diaplaying child fare values

                    if (basefareval==null||basefareval.equals("0.00")||basefareval.equals("0")||basefareval.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",childbasefaretext);
                    else
                        faredetPresenter.bindApiData( (quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + basefareval + ")",childbasefaretext);


                    if (taxfarevalue==null||taxfarevalue.equals("0.00")||taxfarevalue.equals("0")||taxfarevalue.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",childtaxtext);

                    else
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + taxfarevalue + ")",childtaxtext);



                    if (totalbasefare==null||totalbasefare.equals("0.00")||totalbasefare.equals("0")||totalbasefare.equals("null"))
                        faredetPresenter.bindApiData("--",childbasefarevalue);



                    else
                        faredetPresenter.bindApiData("$" + formatter.format((totalbasefare)),childbasefarevalue);


                    if (totaltaxfare==null||totaltaxfare.equals("0.00")||totaltaxfare.equals("0")||totaltaxfare.equals("null"))
                        faredetPresenter.bindApiData("--",childtaxvalue);


                    else
                        faredetPresenter.bindApiData("$" + formatter.format((totaltaxfare)),childtaxvalue);





                    break;

                case "Infant":

                    changeVisibility(View.VISIBLE,infantbasefarelayout);
                    changeVisibility(View.VISIBLE,infanttaxlayout);
                    //Displaying infant fare values

                    if (basefareval==null||basefareval.equals("0.00")||basefareval.equals("0")||basefareval.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",infantbasefaretext);
                    else
                        faredetPresenter.bindApiData( (quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + basefareval + ")",infantbasefaretext);


                    if (taxfarevalue==null||taxfarevalue.equals("0.00")||taxfarevalue.equals("0")||taxfarevalue.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",infanttaxtext);

                    else
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + taxfarevalue + ")",infanttaxtext);



                    if (totalbasefare==null||totalbasefare.equals("0.00")||totalbasefare.equals("0")||totalbasefare.equals("null"))
                        faredetPresenter.bindApiData("--",infantbasefarevalue);



                    else
                        faredetPresenter.bindApiData("$" + formatter.format((totalbasefare)),infantbasefarevalue);


                    if (totaltaxfare==null||totaltaxfare.equals("0.00")||totaltaxfare.equals("0")||totaltaxfare.equals("null"))
                        faredetPresenter.bindApiData("--",infanttaxvalue);


                    else
                        faredetPresenter.bindApiData("$" + formatter.format((totaltaxfare)),infanttaxvalue);

                    break;
                case "InfantonLap":
                    changeVisibility(View.VISIBLE,infantbasefarelayoutlp);
                    changeVisibility(View.VISIBLE,infanttaxlayoutlp);

                    // Displaying infant on lap fare values

                    if (basefareval==null||basefareval.equals("0.00")||basefareval.equals("0")||basefareval.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",infantbasefaretextlp);
                    else
                        faredetPresenter.bindApiData( (quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + basefareval + ")",infantbasefaretextlp);


                    if (taxfarevalue==null||taxfarevalue.equals("0.00")||taxfarevalue.equals("0")||taxfarevalue.equals("null"))
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x NA",infanttaxtextlp);

                    else
                        faredetPresenter.bindApiData((quantityvalue) + passengertype + "(" + (quantityvalue) + "x" + taxfarevalue + ")",infanttaxtextlp);



                    if (totalbasefare==null||totalbasefare.equals("0.00")||totalbasefare.equals("0")||totalbasefare.equals("null"))
                        faredetPresenter.bindApiData("--",infantbasefarevaluelp);



                    else
                        faredetPresenter.bindApiData("$" + formatter.format((totalbasefare)),infantbasefarevaluelp);


                    if (totaltaxfare==null||totaltaxfare.equals("0.00")||totaltaxfare.equals("0")||totaltaxfare.equals("null"))
                        faredetPresenter.bindApiData("--",infanttaxvaluelp);


                    else
                        faredetPresenter.bindApiData("$" + formatter.format((totaltaxfare)),infanttaxvaluelp);
            }





        }
        //Display discount value
        String grandtotaldiscountstr=grandtotaldiscount.toString();
        if (grandtotaldiscountstr==null||grandtotaldiscountstr.equals("0.00")||grandtotaldiscountstr.equals("0.0")||grandtotaldiscountstr.equals("0")||grandtotaldiscountstr.equals("null")) {
             changeVisibility(View.GONE, rootdiscountlayout);

        }
        else{
            changeVisibility(View.VISIBLE, rootdiscountlayout);

            faredetPresenter.bindApiData("$" + formatter.format((grandtotaldiscount)), totaldiscountvaluetxt);
        }


        grandtotalvalue.setText("$" +formatter.format (grandtotalfare));


    }


   /* public void passingFilterData(Bundle bundle) {

        listener.filterSelected(bundle);
    }*/




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
    public void onDestroy() {
        super.onDestroy();

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
