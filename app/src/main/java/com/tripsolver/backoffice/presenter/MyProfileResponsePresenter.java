package com.tripsolver.backoffice.presenter;



import android.view.View;
import android.widget.TextView;

import java.util.List;

import com.tripsolver.backoffice.interfacefiles.MyProfileContract;
import com.tripsolver.backoffice.interfacefiles.MyProfilePresenter;
import com.tripsolver.backoffice.model.MyProfileDataResponse;

/**
 * Created by bpn on 11/30/17.
 *
 * 0. In MVP the presenter assumes the functionality of the "middle-man". All presentation logic is pushed to the presenter.
 * 1. Listens to user action and model updates
 * 2. Updates model and view
 */


public class MyProfileResponsePresenter implements MyProfilePresenter{

    private MyProfileContract.MainView mainView;
    private MyProfileContract.GetQuoteInteractor getQuoteInteractor;
    List<MyProfileDataResponse> myprofiledataresponse;

    public MyProfileResponsePresenter(MyProfileContract.MainView mainView) {
        this.mainView = mainView;
       /* this.getQuoteInteractor = getQuoteInteractor;*/
    }

    @Override
    public void onButtonClick() {
        if (mainView != null) {
            mainView.showProgress();
        }

       /* getQuoteInteractor.getNextQuote(this);*/
    }

    @Override
    public void bindApiData(String value, TextView txtview) {
        if (mainView != null) {
            mainView.setData(value,txtview);
        }

    }


    @Override
    public void setVisibleValue(int value,View view) {
        if (mainView != null) {
            mainView.changeVisibility(value,view);
        }
    }
    @Override
    public void servicecalledComplete() {
        if (mainView != null) {
            mainView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }



 /*   @Override
    public void onFinished(String string) {
        if (mainView != null) {
            mainView.setQuote(string);
            mainView.hideProgress();
        }
    }*/
}

