package com.tripsolver.backoffice.interfacefiles;

import android.view.View;
import android.widget.TextView;

public interface BookingDetFragmentPresenter {

    void onButtonClick();

    void bindApiData(String value, TextView txtview);


    void setVisibleValue(int value,View view);


    void servicecalledComplete();
    void bindFilterData(String fitervalue,String selectedfilter);

    void onDestroy();
}
