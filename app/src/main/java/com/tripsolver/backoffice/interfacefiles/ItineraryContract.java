package com.tripsolver.backoffice.interfacefiles;

import android.view.View;
import android.widget.TextView;

/**
 * Created by lenovo on 4/25/2019.
 */



public interface ItineraryContract {

    interface MainView {
        void showProgress();

        void setData(String value, TextView view);
        void hideProgress();
        void changeVisibility(int value, View view);


        void setQuote(String string);
    }

    interface GetQuoteInteractor {
        interface OnFinishedListener {
            void onFinished(String string);
        }

        void getNextQuote(OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void onButtonClick();

        void onDestroy();
    }
}

