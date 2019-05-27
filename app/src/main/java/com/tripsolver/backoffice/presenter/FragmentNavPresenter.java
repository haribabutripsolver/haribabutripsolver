package com.tripsolver.backoffice.presenter;

import android.view.View;
import android.widget.TextView;

import com.tripsolver.backoffice.interfacefiles.BookingDetailsFragmentContract;
import com.tripsolver.backoffice.interfacefiles.FragmentNavigation;
import com.tripsolver.backoffice.interfacefiles.MainContract;
import com.tripsolver.backoffice.interfacefiles.MainPresenter;
import com.tripsolver.backoffice.view.MainActivity;

/**
 * Created by lenovo on 5/16/2019.
 */

public class FragmentNavPresenter implements FragmentNavigation.Presenter {
    private FragmentNavigation.MainView fragnavview;

    public FragmentNavPresenter(FragmentNavigation.MainView mainView) {
        this.fragnavview = mainView;
    }

    @Override
    public void onButtonClick() {
        if (fragnavview != null) {
            fragnavview.showProgress();
        }

       /* getQuoteInteractor.getNextQuote(this);*/
    }

    @Override
    public void bindApiData(String value, TextView view) {
        if (fragnavview != null) {
            fragnavview.setData(value,view);
        }
    }

    @Override
    public void setVisibleValue(int value, android.view.View view) {
        if (fragnavview != null) {
            fragnavview.changeVisibility(value,view);
        }
    }





  /*  @Override
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
    }*/

    @Override
    public void servicecalledComplete() {
        if (fragnavview != null) {
            fragnavview.hideProgress();
        }
    }

    @Override
    public void bindFilterData(String filtervalue, String selectedfilter) {
        if (fragnavview != null) {
            fragnavview.bindData(filtervalue, selectedfilter);
        } else if (fragnavview != null) {
            fragnavview.bindData(filtervalue, selectedfilter);
        } else if (fragnavview != null) {
            fragnavview.bindData(filtervalue, selectedfilter);

        }

    }

    @Override
    public void addFragment(MainActivity fragment) {
        if (fragnavview != null) {
            fragnavview.setFragment(fragment);

        }


    }


    @Override
    public void onDestroy() {
        fragnavview = null;
    }
}

