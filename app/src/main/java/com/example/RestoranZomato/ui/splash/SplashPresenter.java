package com.example.RestoranZomato.ui.splash;

import android.os.Handler;

import javax.inject.Inject;

import com.example.RestoranZomato.data.DataManager;
import com.example.RestoranZomato.data.network.ApiHeader;
import com.example.RestoranZomato.ui.base.BasePresenter;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements SplashMvpPresenter<V> {

    private static final long SPLASH_DURATION_MILLIS = 2000;

    @Inject
    SplashPresenter(DataManager dataManager, ApiHeader apiHeader) {
        super(dataManager, apiHeader);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        takeToHomeScreen();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void handleApiError() {
        super.handleApiError();
    }

    private void takeToHomeScreen() {
        new Handler().postDelayed(() -> {
            if (!isViewAttached())
                return;
            getMvpView().openHomeScreen();
        }, SPLASH_DURATION_MILLIS);
    }
}
