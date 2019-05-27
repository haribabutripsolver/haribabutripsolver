package com.tripsolver.backoffice.interfacefiles;

import android.view.View;
import android.widget.TextView;

import com.tripsolver.backoffice.view.MainActivity;

/**
 * Created by lenovo on 5/15/2019.
 */

public interface FragmentNavigation {
    interface MainView {
        void showProgress();
        void setData(String value, TextView view);
        void atachPresenter(Presenter presenter);
        void hideProgress();
        void bindData(String filtervalue, String filtertype);
        void setFragment(MainActivity fragment);

        void changeVisibility(int value, View view);
        void setQuote(String string);
    }
    interface Presenter {
        void addFragment(MainActivity fragment);
        void onButtonClick();

        void bindApiData(String value, TextView view);
        void setVisibleValue(int value,View view);


        void servicecalledComplete();

        void onDestroy();

void bindFilterData(String filtervalue,String filtertype);
    }
}