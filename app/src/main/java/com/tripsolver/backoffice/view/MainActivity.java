package com.tripsolver.backoffice.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.fragments.AllBookingsFragment;
import com.tripsolver.backoffice.fragments.CancelledFragment;
import com.tripsolver.backoffice.fragments.ConfirmedFragment;
import com.tripsolver.backoffice.fragments.TicketedFragment;
import com.tripsolver.backoffice.fragments.UnConfirmedFragment;
import com.tripsolver.backoffice.interfacefiles.FragmentNavigation;
import com.tripsolver.backoffice.interfacefiles.MainContract;
import com.tripsolver.backoffice.interfacefiles.MainPresenter;
import com.tripsolver.backoffice.model.TicketBookingsResponse;
import com.tripsolver.backoffice.model.Validations;
import com.tripsolver.backoffice.presenter.FragmentNavPresenter;
import com.tripsolver.backoffice.presenter.TicketBookingResponsePresenter;


/**
 * @author Adil Soomro*/

 public class MainActivity extends Fragment implements MainContract.MainView {
	/** Called when the activity is first created. */
	EditText searchvalue;
	Spinner filteredit;
	String returnBodyText;
	public TextView noresultsfound;
	public List<TicketBookingsResponse> ticketbookingresponse;
	TextView tabonetittletxt,tabtwotittletxt,tabthreetittletxt,tabfourtittletxt,tabfivetittletxt;
	RelativeLayout tabonelayout,tabtwolayout,tabthreelayout,tabfourlayout,tabfivelayout;
ImageView triponelogo,triptwologo,tripthreelogo,tripfourlogo,tripfivelogo;
	ArrayList<String>	itemlist;
	int tabposition=0;
	AlertDialog dialog;
	Fragment selectedFragment;
	String filtertype,filtervalue;
Button searchbt;
	ViewPagerAdapter adapter;
	protected FragmentNavigation.Presenter navigationPresenter;
	Spinner spinneredit;
	TextView clearfilter;
	EditText	searchedittxt;
	AllBookingsFragment allbookingsfragment;
	ConfirmedFragment conffragment;
	TicketedFragment ticketedfragment;
	CancelledFragment cancelfragment;
	UnConfirmedFragment unconfirmedfragment;
public TicketBookingResponsePresenter ticketbookingpresenter;
	ViewPager viewPager;
	LinearLayout searchiconlayout;
	int activitytype=0;
	String days;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_main, container, false);
		String[] icons = {
		};
Bundle b=getArguments();
activitytype=b.getInt("activitytype");
days=b.getString("days");


		//Tablayoutview
		TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);

		  viewPager = (ViewPager) view.findViewById(R.id.main_tab_content);
		tabLayout.setupWithViewPager(viewPager);

		noresultsfound=(TextView)view.findViewById(R.id.noresultsfound);
		searchiconlayout = (LinearLayout)getActivity().findViewById(R.id.searchiconlayout);
		searchiconlayout.setVisibility(View.VISIBLE);


		ticketbookingpresenter= new TicketBookingResponsePresenter(this);

if(allbookingsfragment==null) {
	allbookingsfragment = new AllBookingsFragment();
	allbookingsfragment.setArguments(b);
}
		if(conffragment==null) {

	 conffragment=new ConfirmedFragment();
            conffragment.setArguments(b);
		}
			if(ticketedfragment==null) {

				ticketedfragment=new TicketedFragment();
                ticketedfragment.setArguments(b);
			}
				if(cancelfragment==null) {

	 cancelfragment=new CancelledFragment();
                    cancelfragment.setArguments(b);
				}
					if(unconfirmedfragment==null) {

						unconfirmedfragment=new UnConfirmedFragment();
                        unconfirmedfragment.setArguments(b);

					}
		setupViewPager(viewPager);

		//
		View tabOne =  LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabonetittletxt=(TextView)tabOne.findViewById(R.id.tabtittle);
		tabonelayout=(RelativeLayout) tabOne.findViewById(R.id.tablayout);
		triponelogo=(ImageView)tabOne.findViewById(R.id.triplogo);


		final View tabtwo =  LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabtwotittletxt=(TextView)tabtwo.findViewById(R.id.tabtittle);
		tabtwolayout=(RelativeLayout)tabtwo.findViewById(R.id.tablayout);
		triptwologo=(ImageView)tabtwo.findViewById(R.id.triplogo);


		View tabthree =  LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabthreetittletxt=(TextView)tabthree.findViewById(R.id.tabtittle);
		tabthreelayout=(RelativeLayout)tabthree.findViewById(R.id.tablayout);
		tripthreelogo=(ImageView)tabthree.findViewById(R.id.triplogo);

		View tabfour =  LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabfourtittletxt=(TextView)tabfour.findViewById(R.id.tabtittle);
		tabfourlayout=(RelativeLayout)tabfour.findViewById(R.id.tablayout);
		tripfourlogo=(ImageView)tabfour.findViewById(R.id.triplogo);

		View tabfive =  LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
		tabfivetittletxt=(TextView)tabfive.findViewById(R.id.tabtittle);
		tabfivelayout=(RelativeLayout)tabfive.findViewById(R.id.tablayout);
		tripfivelogo=(ImageView)tabfive.findViewById(R.id.triplogo);

		tabonetittletxt.setText("All Bookings");
		tabtwotittletxt.setText("Confirmed/Blocked");
		tabthreetittletxt.setText("Ticketed");
		tabfourtittletxt.setText("Cancelled");
		tabfivetittletxt.setText("UnConfirmed");


		triponelogo.setBackgroundResource(R.drawable.allbookingsiconblack);
				triptwologo.setBackgroundResource(R.drawable.confirmed);
		tripthreelogo.setBackgroundResource(R.drawable.specialfare);
		tripfourlogo.setBackgroundResource(R.drawable.cancelled);
		tripfivelogo.setBackgroundResource(R.drawable.unconfirmed);



	tabonelayout.setBackgroundColor(Color.parseColor("#fdf8e5"));
		tabtwolayout.setBackgroundColor(Color.parseColor("#fdba52"));
		tabthreelayout.setBackgroundColor(Color.parseColor("#b9eeb9"));
		tabfourlayout.setBackgroundColor(Color.parseColor("#f6bcbc"));
		tabfivelayout.setBackgroundColor(Color.parseColor("#e6c295"));

		tabLayout.getTabAt(0).setCustomView(tabOne);
	tabLayout.getTabAt(1).setCustomView(tabtwo);
	tabLayout.getTabAt(2).setCustomView(tabthree);
		tabLayout.getTabAt(3).setCustomView(tabfour);
		tabLayout.getTabAt(4).setCustomView(tabfive);



		tabLayout.setMinimumWidth(500);
		/*for (int i = 0; i < icons.length; i++) {
			tabLayout.getTabAt(i).setText(icons[i]);

		}*/
		tabLayout.getTabAt(activitytype).select();
        tabposition=activitytype;
		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				switch (tab.getPosition())
				{

					case 0:

						tabposition=0;
						break;
					case 1:

						tabposition=1;
						break;
					case 2:

						tabposition=2;
						break;
					case 3:

						tabposition=3;
						break;
					case 4:

						tabposition=4;
						break;
					default:

						tabposition=0;
						 break;

				}
			}
				@Override
				public void onTabUnselected(TabLayout.Tab tab) {
				}
				@Override
				public void onTabReselected(TabLayout.Tab tab) {
				}
			});

		searchiconlayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


				filterPopup();
			}
		});


return view;
	}

	private void setupViewPager(ViewPager viewPager) {
		 adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

		adapter.insertNewFragment(allbookingsfragment);
		adapter.insertNewFragment(conffragment);
		adapter.insertNewFragment(ticketedfragment);

		adapter.insertNewFragment(cancelfragment);
		adapter.insertNewFragment(unconfirmedfragment);
		viewPager.setAdapter(adapter);
	}

	@Override
	public void showProgress() {

	}

	@Override
	public void setData(String value, TextView view) {

	}



	@Override
	public void hideProgress() {

	}

	@Override
	public void bindData(String filtervalue, String selectedfilter) {
		switch (tabposition)
		{

			case 0:
				 allbookingsfragment = (AllBookingsFragment)viewPager.getAdapter()
						.instantiateItem(viewPager, viewPager.getCurrentItem());
				allbookingsfragment.setFilterValues(filtervalue,selectedfilter);
				break;
			case 1:
				conffragment = (ConfirmedFragment) viewPager.getAdapter()
						.instantiateItem(viewPager, viewPager.getCurrentItem());
				conffragment.setFilterValues(filtervalue,selectedfilter);
				break;
			case 2:
				ticketedfragment = (TicketedFragment) viewPager.getAdapter()
						.instantiateItem(viewPager, viewPager.getCurrentItem());
				ticketedfragment.setFilterValues(filtervalue,selectedfilter);
				break;
			case 3:
				cancelfragment = (CancelledFragment) viewPager.getAdapter()
						.instantiateItem(viewPager, viewPager.getCurrentItem());
				cancelfragment.setFilterValues(filtervalue,selectedfilter);
				break;
			case 4:
				unconfirmedfragment  = (UnConfirmedFragment) viewPager.getAdapter()
						.instantiateItem(viewPager, viewPager.getCurrentItem());
				unconfirmedfragment.setFilterValues(filtervalue,selectedfilter);
				break;
			default:
				 allbookingsfragment = (AllBookingsFragment)viewPager.getAdapter()
						.instantiateItem(viewPager, viewPager.getCurrentItem());
				allbookingsfragment.setFilterValues(filtervalue,selectedfilter);
				break;

		}



	}

	@Override
	public void setFragment(MainActivity fragment) {

	}

	@Override
	public void changeVisibility(int value, View view) {

	}

	@Override
	public void setQuote(String string) {

	}


	class ViewPagerAdapter extends FragmentStatePagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		public ViewPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void insertNewFragment(Fragment fragment) {
			mFragmentList.add(fragment);
		}
	}

public void callFilter(View v)
{
	if(!getActivity().isFinishing()&&getActivity()!=null) {
		final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
		dialog.setContentView(R.layout.searchlayout);
		Button searchrecordds;

		filteredit = (Spinner) dialog.findViewById(R.id.filtertype);
		searchvalue = dialog.findViewById(R.id.searchvalue);
		searchrecordds = dialog.findViewById(R.id.searchbt);
		dialog.show();
	}
	/*searchrecordds.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if(checkValidations())   {
		}
	};
	dialog.show();*/

}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {



	/*	switch (item.getItemId()) {

			case R.id.action_settings:
				Intent settingsIntent = new Intent(this, TabSample.class);
				startActivity(settingsIntent);
			default:
				return super.onOptionsItemSelected(item);
		}*/
	return false;
	}



	public void filterPopup()
	{
		if(getActivity()!=null) {
			if (!getActivity().isFinishing() && getActivity() != null) {
				android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

				LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View customView = layoutInflater.inflate(R.layout.searchlayout, null);
				spinneredit = (Spinner) customView.findViewById(R.id.filtertype);



										searchedittxt = (EditText) customView.findViewById(R.id.searchvalue);
				searchbt = (Button) customView.findViewById(R.id.searchbtvalue);
				clearfilter=(TextView)customView.findViewById(R.id.clearfilter);
					itemlist = new ArrayList<String>();
				itemlist.add("PNR");
				itemlist.add("Confirmation");
				itemlist.add("Booking Id");
				itemlist.add("First Name");
				itemlist.add("Last Name");
				itemlist.add("Phone");
				itemlist.add("Email_Id");



				builder.setCancelable(true);


				ArrayAdapter<String> adp = new ArrayAdapter<String>
						(getActivity(), android.R.layout.simple_list_item_1, itemlist);
          /*  adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    MainActivity.this, android.R.layout.simple_list_item_1, list);*/
				spinneredit.setAdapter(adp);

				builder.setView(customView);
				dialog = builder.create();
   /*         dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;*/

				Window window = dialog.getWindow();
				window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


				dialog.show();
				spinneredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
						filtertype = itemlist.get(i);
						filtervalue = searchedittxt.getText().toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> adapterView) {
						filtertype = itemlist.get(0);
						filtervalue = searchedittxt.getText().toString();
					}
				});

				searchbt.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(checkValidations()) {
							dialog.dismiss();
							filtervalue = searchedittxt.getText().toString();

							ticketbookingpresenter.bindFilterData(filtervalue, filtertype);
						}
					}
				});
				clearfilter.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
						ticketbookingpresenter.bindFilterData("","");

					}
				});

			}

		}

	}

	public boolean checkValidations()
	{

		/*if (Validations.isEmpty(filteredit.getText().toString(), filteredit, "filter type")) {
		}*/
		if (Validations.isEmpty(searchedittxt.getText().toString(), searchedittxt, "search type")) {
		}



		boolean value= /*Validations.isEmpty(filteredit.getText().toString(),filteredit , "filter type")&&*/
				Validations.isEmpty(searchedittxt.getText().toString(), searchedittxt, "search type");
		return value;

	}
}