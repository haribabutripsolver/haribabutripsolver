package com.tripsolver.backoffice.presenter;



import android.view.View;
import android.widget.TextView;

import java.util.List;

import com.tripsolver.backoffice.interfacefiles.BookingDetailsFragmentContract;
import com.tripsolver.backoffice.interfacefiles.FragmentNavigation;
import com.tripsolver.backoffice.interfacefiles.MainContract;
import com.tripsolver.backoffice.interfacefiles.MainPresenter;
import com.tripsolver.backoffice.model.TicketBookingsResponse;
import com.tripsolver.backoffice.view.MainActivity;

/**
 * Created by bpn on 11/30/17.
 *
 * 0. In MVP the presenter assumes the functionality of the "middle-man". All presentation logic is pushed to the presenter.
 * 1. Listens to user action and model updates
 * 2. Updates model and view
 */


 public class TicketBookingResponsePresenter implements MainPresenter,FragmentNavigation.Presenter {

    private MainContract.MainView mainView;
    private BookingDetailsFragmentContract.MainView fragmentview;
    private FragmentNavigation.MainView fragnavview;

    private MainContract.GetQuoteInteractor getQuoteInteractor;
    List<TicketBookingsResponse> ticketBookingsResponse;

    public TicketBookingResponsePresenter(MainContract.MainView mainView) {
        this.mainView = mainView;
       /* this.getQuoteInteractor = getQuoteInteractor;*/
    }

  public TicketBookingResponsePresenter(BookingDetailsFragmentContract.MainView mainView) {
        this.fragmentview = mainView;
    }
    public TicketBookingResponsePresenter(FragmentNavigation.MainView mainView) {
        this.fragnavview = mainView;
    }
    @Override
    public void onButtonClick() {
        if (mainView != null) {
            mainView.showProgress();
        }

       /* getQuoteInteractor.getNextQuote(this);*/
    }

    @Override
    public void bindApiData(String value, TextView view) {

    }

    @Override
    public void setVisibleValue(int value, android.view.View view) {

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
        if (mainView != null) {
            mainView.hideProgress();
        }
    }

    @Override
    public void bindFilterData(String filtervalue, String selectedfilter) {
        if (mainView != null) {
            mainView.bindData(filtervalue,selectedfilter);
        }
        else if(fragmentview!=null)
        {
            fragmentview.bindData(filtervalue,selectedfilter);
        }
        else if(fragnavview!=null)
        {
            fragnavview.bindData(filtervalue,selectedfilter);

        }

    }

    @Override
    public void addFragment(MainActivity fragment) {
        if(mainView!=null)
        {
            mainView.setFragment(fragment);

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

