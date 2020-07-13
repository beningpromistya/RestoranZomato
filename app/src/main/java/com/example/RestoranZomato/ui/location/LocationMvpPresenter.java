package com.example.RestoranZomato.ui.location;

import com.example.RestoranZomato.di.PerActivity;
import com.example.RestoranZomato.ui.base.MvpPresenter;


@PerActivity
public interface LocationMvpPresenter<V extends LocationMvpView> extends MvpPresenter<V> {

    void fetchLocations(String query, Double lat, Double lng);

    void onBackPressed();

    void saveLocationInfo(boolean isMyLocation, Double latitude, Double longitude, String city, String locality);
}
