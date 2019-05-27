package com.tripsolver.backoffice.presenter;



import android.view.View;
import android.widget.TextView;

import com.tripsolver.backoffice.interfacefiles.ReportsContract;
import com.tripsolver.backoffice.interfacefiles.ReportsdataPresenterInterface;


public class ReportDataPresenter implements ReportsdataPresenterInterface{

    private ReportsContract.MainView mainView;
    private ReportsContract.GetQuoteInteractor getQuoteInteractor;

    public ReportDataPresenter(ReportsContract.MainView mainView) {
        this.mainView = mainView;
       /* this.getQuoteInteractor = getQuoteInteractor;*/
    }

    @Override
    public void onButtonClick() {
        if (mainView != null) {
            mainView.showProgress();
        }


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

}

