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

import org.json.JSONArray;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.interfacefiles.PaymentInfoContract;
import com.tripsolver.backoffice.interfacefiles.PaymentInfoPresenter;
import com.tripsolver.backoffice.model.PaymenInfoResponse;
import com.tripsolver.backoffice.presenter.PaymentResponsePresenter;


public class PaymentInformation extends AppCompatActivity implements PaymentInfoContract.MainView {
    RelativeLayout dialogcancellayout;
    /*
        String adultbasefare,childbasefare,infantbasefare,infantbasefarelp,adulttax,grandtotal,childtax,infanttax,infanttaxlp;
    */
    public List<PaymenInfoResponse> paymentinfolist;

    JSONArray faredetjsonarr;
    String faredetresponse;
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
    LinearLayout giftlayout;
    View giftseperator;
    RelativeLayout fareruleslayout;
    TextView discount, discountvaluetxt,
            childdiscount ,childdiscountvalue,
            infantdiscount ,infantdiscountvalue;
    PaymentInfoPresenter paymentinfopresenter;
    RecyclerView recyclerView;
    String itinselid;
    PaymentResponsePresenter paymentresponsepresenter;
   TextView cardtypevalue,cardnovalue,verificationnovalue,
           cardholdernamevalue,expiredatevalue,addressvalue;
    ArrayList<String> countrycode,statecode,states,countrylist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentinformation);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.Paymenttittle);
        Bundle bundle=getIntent().getExtras();
        itinselid=bundle.getString("itinselid");
        paymentresponsepresenter = new PaymentResponsePresenter(this);

        cardtypevalue = (TextView)findViewById(R.id.cardtypevalue);
        cardnovalue = (TextView)findViewById(R.id.cardnovalue);
        verificationnovalue = (TextView)findViewById(R.id.verificationnovalue);
        cardholdernamevalue = (TextView)findViewById(R.id.cardholdernamevalue);
        expiredatevalue = (TextView)findViewById(R.id.expiredatevalue);

        expiredatevalue = (TextView)findViewById(R.id.expiredatevalue);
        addressvalue = (TextView)findViewById(R.id.addressvalue);
intializearrays();
        bindDataLogic();


    }


    @Override
    public void showProgress() {
        pdia = new ProgressDialog(PaymentInformation.this);
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
        Double basefareval, taxfarevalue, grandtotaldiscount = 0.0, grandtotalfare = 0.0, totalfarevalue = 0.0;
        this.paymentinfolist = ((TicketDetails) getParent()).paymentinfolist;
        if (paymentinfolist.size() > 0)
        {
            PaymenInfoResponse paymentinforesponseobj = paymentinfolist.get(0);
        String cardtype, cardnumberstr, verificationnumberstr, cardholdersnamestr, expdatestr, addressvaluestr, cardtypecode,
                cityvaluestr, statecodestr, expmonth, expyear, streetaddress, state, countrycodestr, zipcode;
        cardtypecode = paymentinforesponseobj.getCardType();
        cardnumberstr = paymentinforesponseobj.getCardNumber();
        verificationnumberstr = paymentinforesponseobj.getVerificationnumber();
        cardholdersnamestr = paymentinforesponseobj.getCardholdername();
        expmonth = paymentinforesponseobj.getExp_month();
        expyear = paymentinforesponseobj.getExp_year();
        streetaddress = paymentinforesponseobj.getStreetAddress();
        cityvaluestr = paymentinforesponseobj.getCity();
        statecodestr = paymentinforesponseobj.getState();
        countrycodestr = paymentinforesponseobj.getCountry();
        zipcode = paymentinforesponseobj.getZipCode();
        expdatestr = expmonth + "/" + expyear;

        setAddress(streetaddress, cityvaluestr, statecodestr, countrycodestr, zipcode);
        setCardType(cardtypecode);
        String tempcard = "";
        String tempcvv = "";
        for (int i = 0; i < cardnumberstr.length() - 4; i++) {
            tempcard = tempcard.concat("*");
        }
        if (cardnumberstr.length() > 4) {
            tempcard = tempcard + cardnumberstr.substring(cardnumberstr.length() - 4, (cardnumberstr.length()));
        }
        for (int i = 0; i < verificationnumberstr.length(); i++) {
            tempcvv = tempcvv.concat("*");
        }
        paymentresponsepresenter.bindApiData(tempcard, cardnovalue);
        paymentresponsepresenter.bindApiData(tempcvv, verificationnovalue);
        paymentresponsepresenter.bindApiData(cardholdersnamestr, cardholdernamevalue);
        paymentresponsepresenter.bindApiData(expdatestr, expiredatevalue);


    }

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


public void intializearrays()
{
    countrycode=new ArrayList<>();
    statecode=new ArrayList<>();
    states=new ArrayList<>();
    countrylist=new ArrayList<>();

    countrylist.add("United States");


    states.add("Alabama");
    states.add("Alaska");
    states.add("Arizona");
    states.add("Arkansas");
    states.add("Armed Forces Americas (except Canada)");
    states.add("Armed Forces Canada, Europe, Africa, Middle East");
    states.add("Armed Forces Pacific");
    states.add("California");
    states.add("Colorado");
    states.add("Connecticut");
    states.add("Delaware");
    states.add("District Of Columbia");
    states.add("Florida");
    states.add("Georgia");
    states.add("Hawaii");
    states.add("Idaho");
    states.add("Illinois");
    states.add("Indiana");
    states.add("Iowa");
    states.add("Kansas");
    states.add("Kentucky");
    states.add("Louisiana");
    states.add("Maine");
    states.add("Maryland");
    states.add("Massachusetts");
    states.add("Michigan");
    states.add("Minnesota");
    states.add("Mississippi");
    states.add("Missouri");
    states.add("Montana");
    states.add("Nebraska");
    states.add("Nevada");
    states.add("New Hampshire");
    states.add("New Jersey");
    states.add("New Mexico");
    states.add("New York");
    states.add("North Carolina");
    states.add("North Dakota");
    states.add("Ohio");
    states.add("Oklahoma");
    states.add("Oregon");
    states.add("Pennsylvania");
    states.add("Puerto Rico");
    states.add("Rhode Island");
    states.add("South Carolina");
    states.add("South Dakota");
    states.add("Tennessee");
    states.add("Texas");
    states.add("Utah");
    states.add("Vermont");
    states.add("Virginia");
    states.add("Washington");
    states.add("West Virginia");
    states.add("Wisconsin");
    states.add("Wyoming");

    countrycode.add("US");
        /*countrycode.add("CA");*/
    statecode.add("AL");
    statecode.add("AK");
    statecode.add("AZ");
    statecode.add("AR");
    statecode.add("AA");
    statecode.add("AE");
    statecode.add("AP");
    statecode.add("CA");
    statecode.add("CO");
    statecode.add("CT");
    statecode.add("DE");
    statecode.add("DC");
    statecode.add("FL");
    statecode.add("GA");
    statecode.add("HI");
    statecode.add("ID");
    statecode.add("IL");
    statecode.add("IN");
    statecode.add("IA");
    statecode.add("KS");
    statecode.add("KY");
    statecode.add("LA");
    statecode.add("ME");
    statecode.add("MD");
    statecode.add("MA");
    statecode.add("MI");
    statecode.add("MN");
    statecode.add("MS");
    statecode.add("MO");
    statecode.add("MT");
    statecode.add("NE");
    statecode.add("NV");
    statecode.add("NH");
    statecode.add("NJ");
    statecode.add("NM");
    statecode.add("NY");
    statecode.add("NC");
    statecode.add("ND");
    statecode.add("OH");
    statecode.add("OK");
    statecode.add("OR");
    statecode.add("PA");
    statecode.add("PR");
    statecode.add("RI");
    statecode.add("SC");
    statecode.add("SD");
    statecode.add("TN");
    statecode.add("TX");
    statecode.add("UT");
    statecode.add("VT");
    statecode.add("VA");
    statecode.add("WA");
    statecode.add("WV");
    statecode.add("WI");
    statecode.add("WY");



}

public void setAddress(String streetvalue,String cityvalue,String statecodestr,String countrycodestr,String zipcode)
{
    String countryvalue,statevalue;
    int countryindex=0,stateindex=0;
    boolean iscountrycode=false,isstatecode=false;
    for (int i = 0; i < countrylist.size(); i++) {
        if (countrycode.get(i).equals(countrycodestr)) {
            countryindex = i;
            iscountrycode=true;
            i = countrylist.size();
            break;
        }
    }
    for (int j = 0; j < states.size(); j++) {
        if (statecode.get(j).equals(statecodestr)) {
            stateindex = j;
            isstatecode=true;
            j = states.size();
            break;
        }
    }
    if(iscountrycode)
    {
        countryvalue = countrylist.get(countryindex);

    }
    else
    {
        countryvalue = countrycodestr;

    }
    if(isstatecode)
    {
        statevalue= states.get(stateindex);

    }
    else
    {
        statevalue= statecodestr;

    }

    String addressvaluestr=streetvalue+", "+cityvalue+", "+statevalue+", "+countryvalue+", "+zipcode;
    paymentresponsepresenter.bindApiData(addressvaluestr,addressvalue);


}

public void setCardType(String cardtypecode)
{
    String cardtypevaluestr="";
    switch (cardtypecode)
    {
        case "VI":
            cardtypevaluestr="visa";
            break;


        case "CA":
            cardtypevaluestr="mastercard";
            break;
        case "AX":
            cardtypevaluestr="americanexpress";

            break;

    }
    paymentresponsepresenter.bindApiData(cardtypevaluestr,cardtypevalue);
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
