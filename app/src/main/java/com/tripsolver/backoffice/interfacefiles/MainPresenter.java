package com.tripsolver.backoffice.interfacefiles;

import android.view.View;
import android.widget.TextView;

public interface MainPresenter {

    void onButtonClick();


    void setVisibleValue(int value,View view);

    void servicecalledComplete();
    void bindFilterData(String fitervalue,String selectedfilter);

    void onDestroy();
}
