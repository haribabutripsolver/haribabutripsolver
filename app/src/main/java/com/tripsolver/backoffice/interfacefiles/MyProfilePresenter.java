package com.tripsolver.backoffice.interfacefiles;

import android.view.View;
import android.widget.TextView;

public interface MyProfilePresenter {

    void onButtonClick();

    void bindApiData(String value, TextView view);
    void setVisibleValue(int value,View view);


    void servicecalledComplete();

    void onDestroy();
}
