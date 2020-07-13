package com.example.RestoranZomato.ui.restaurantpage;

import android.util.Log;

import javax.inject.Inject;

import com.example.RestoranZomato.data.DataManager;
import com.example.RestoranZomato.data.network.ApiHeader;
import com.example.RestoranZomato.data.network.model.RestaurantDetailResponse;
import com.example.RestoranZomato.ui.base.BasePresenter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantDetailPresenter<V extends RestaurantDetailMvpView> extends BasePresenter<V>
        implements RestaurantDetailMvpPresenter<V> {

    @Inject
    RestaurantDetailPresenter(DataManager dataManager, ApiHeader apiHeader) {
        super(dataManager, apiHeader);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void handleApiError() {
        super.handleApiError();
    }

    @Override
    public void fetchRestaurantDetails(String restaurantId) {
        Log.d("Location", "Fetching Restaurant detail: " + restaurantId);

        getMvpView().showLoading();

        getDataManager().getRestaurantDetail(getApiHeaders(), restaurantId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurantDetailResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestaurantDetailResponse restaurantDetailResponse) {
                        if (!isViewAttached())
                            return;

                        getMvpView().onRestaurantDetail(restaurantDetailResponse);
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (!isViewAttached())
                            return;

                        Log.d("Rest", "onError: "+t.getCause().getMessage());
                        getMvpView().onError("Something went wrong");
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
