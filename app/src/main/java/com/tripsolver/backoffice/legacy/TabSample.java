package com.tripsolver.backoffice.legacy;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.view.MainActivity;


/**
 * @author Adil Soomro
 *
 */
public class TabSample extends TabActivity {
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity);
		setTabs() ;
	}
	private void setTabs()
	{
		addTab("CustomerInfo", R.drawable.tab_home_legacy, ArrowsActivity.class);
		addTab("FareDataInfo", R.drawable.tab_search_legacy, OptionsActivity.class);
		
		addTab("ItineraryInfo", R.drawable.tab_home_legacy, ArrowsActivity.class);
		addTab("PaymentInfo", R.drawable.tab_search_legacy, MainActivity.class);
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
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