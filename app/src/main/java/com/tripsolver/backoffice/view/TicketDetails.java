package com.tripsolver.backoffice.view;

/**
 * Created by lenovo on 4/26/2019.
 */

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.model.PaymenInfoResponse;


/**
 * @author Adil Soomro
 *
 */
public class TicketDetails extends TabActivity {
    /** Called when the activity is first created. */
    String itinselid,bookingid,triptype;
    ImageView backbt;

    TextView pagetittle;
    public List<PaymenInfoResponse> paymentinfolist;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();

        itinselid=bundle.getString("itinselid");
        bookingid=bundle.getString("bookingid");
        triptype=bundle.getString("triptype");
        setContentView(R.layout.tab_activity);
       /* backbt=(ImageView)findViewById(R.id.backbt);
        pagetittle=(TextView) findViewById(R.id.pagetittle);*/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        setTabs();
    }
    private void setTabs()
    {
        addTab("Cust Details", R.drawable.customerinfo, CustomerInformation.class);
        addTab("Fare Summary", R.drawable.fareinfo, FareDetails.class);

      addTab("Payment Details", R.drawable.paymentinfo, PaymentInformation.class);
       addTab("View Itinerary", R.drawable.itineraryinfo, ItineraryInfo.class);
    }
   /* private void configureToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }*/
    private void addTab(String labelId, int drawableId, Class<?> c)
    {
        TabHost tabHost = getTabHost();
        Intent intent = new Intent(this, c);
        Bundle b=new Bundle();
        b.putString("itinselid",itinselid);
        b.putString("bookingid",bookingid);
        intent.putExtras(b);
        TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(labelId);
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);

        spec.setIndicator(tabIndicator);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }


}
