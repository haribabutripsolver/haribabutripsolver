package com.tripsolver.backoffice.interfacefiles;

import android.view.View;
import android.widget.TextView;

import com.tripsolver.backoffice.view.MainActivity;

/**
 * Created by lenovo on 4/25/2019.
 */



public interface MainContract {

    interface MainView {
        void showProgress();

void setData(String value, TextView txtview);
        void hideProgress();
       void bindData(String filtervalue, String filtertype);
        void setFragment(MainActivity fragment);

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

