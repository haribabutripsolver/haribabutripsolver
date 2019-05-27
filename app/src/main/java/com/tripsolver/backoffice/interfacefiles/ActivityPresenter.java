package com.tripsolver.backoffice.interfacefiles;

import android.view.View;
import android.widget.TextView;

/**
 * Created by lenovo on 5/3/2019.
 */

public interface ActivityPresenter {

    void onButtonClick();

    void bindApiData(String value, TextView view);
    void setVisibleValue(int value,View view);


    void servicecalledComplete();

    void onDestroy();
}