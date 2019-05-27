package com.tripsolver.backoffice.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.tripsolver.backoffice.R;
import com.tripsolver.backoffice.fragments.SevenDayFragment;
import com.tripsolver.backoffice.fragments.SixtyDayFragment;
import com.tripsolver.backoffice.fragments.ThirtyDayFragment;
import com.tripsolver.backoffice.interfacefiles.ActivityContract;

/**
 * Created by lenovo on 5/3/2019.
 */


public class ActivityCount extends Fragment implements ActivityContract.MainView{
   TextView confirmedcount,ticketedcount,shoppingcancelledcount
           ,unconfirmedcount;
   CardView sevendaycardview,thirtydaycardview,sixtydaycardview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activitytabview, container, false);

       /* android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(R.string.activities);*/
        confirmedcount=(TextView)view.findViewById(R.id.confirmedcount);
                ticketedcount=(TextView)view.findViewById(R.id.ticketedcount);
        shoppingcancelledcount=(TextView)view.findViewById(R.id.shoppingcancelledcount);
                unconfirmedcount=(TextView)view.findViewById(R.id.unconfirmedcount);

        final TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);

        ViewPager viewPager = (ViewPager)view. findViewById(R.id.main_tab_content);
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager);


        View tabone =  LayoutInflater.from(getActivity()).inflate(R.layout.activitycustomtab, null);
    TextView    tabonetittletxt=(TextView)tabone.findViewById(R.id.tabtittle);
    sevendaycardview=(CardView)tabone.findViewById(R.id.cardview);
        tabonetittletxt.setText("7 Days Activity");

        View tabtwo =  LayoutInflater.from(getActivity()).inflate(R.layout.activitycustomtab, null);
     TextView   tabtwotittletxt=(TextView)tabtwo.findViewById(R.id.tabtittle);
        thirtydaycardview=(CardView)tabtwo.findViewById(R.id.cardview);

        tabtwotittletxt.setText("30 Days Activity");

        View tabthree =  LayoutInflater.from(getActivity()).inflate(R.layout.activitycustomtab, null);
        sixtydaycardview=(CardView)tabthree.findViewById(R.id.cardview);

        TextView  tabthreetittletxt=(TextView)tabthree.findViewById(R.id.tabtittle);
        tabthreetittletxt.setText("60 Days Activity");
        tabLayout.getTabAt(0).setCustomView(tabone);
        tabLayout.getTabAt(1).setCustomView(tabtwo);
        tabLayout.getTabAt(2).setCustomView(tabthree);

        sevendaycardview.setCardBackgroundColor(Color.parseColor("#3775dd"));
        thirtydaycardview.setCardBackgroundColor(Color.parseColor("#265094"));
        sixtydaycardview.setCardBackgroundColor(Color.parseColor("#265094"));
        tabLayout.setMinimumWidth(500);
        tabLayout.getTabAt(0).select();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {

                    case 0:

                    sevendaycardview.setCardBackgroundColor(Color.parseColor("#3775dd"));
                        thirtydaycardview.setCardBackgroundColor(Color.parseColor("#265094"));
                        sixtydaycardview.setCardBackgroundColor(Color.parseColor("#265094"));

                        break;
                    case 1:
                        sevendaycardview.setCardBackgroundColor(Color.parseColor("#265094"));
                        thirtydaycardview.setCardBackgroundColor(Color.parseColor("#3775dd"));
                        sixtydaycardview.setCardBackgroundColor(Color.parseColor("#265094"));

                        break;
                    case 2:

                        sevendaycardview.setCardBackgroundColor(Color.parseColor("#265094"));
                        thirtydaycardview.setCardBackgroundColor(Color.parseColor("#265094"));
                        sixtydaycardview.setCardBackgroundColor(Color.parseColor("#3775dd"));
                        break;

                    default:

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
return view;
    }
    private void setupViewPager(ViewPager viewPager) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.insertNewFragment(new SevenDayFragment());
        adapter.insertNewFragment(new ThirtyDayFragment());
        adapter.insertNewFragment(new SixtyDayFragment());
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



}
