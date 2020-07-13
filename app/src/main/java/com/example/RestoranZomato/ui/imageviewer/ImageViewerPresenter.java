package com.example.RestoranZomato.ui.imageviewer;

import android.os.Handler;

import javax.inject.Inject;

import com.example.RestoranZomato.data.DataManager;
import com.example.RestoranZomato.data.network.ApiHeader;
import com.example.RestoranZomato.ui.base.BasePresenter;

public class ImageViewerPresenter<V extends ImageViewerMvpView> extends BasePresenter<V>
        implements ImageViewerMvpPresenter<V> {

    private static final long SPLASH_DURATION_MILLIS = 2000;

    @Inject
    ImageViewerPresenter(DataManager dataManager, ApiHeader apiHeader) {
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
