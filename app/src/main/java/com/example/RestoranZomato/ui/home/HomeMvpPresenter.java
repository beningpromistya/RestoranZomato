package com.example.RestoranZomato.ui.home;

import com.example.RestoranZomato.di.PerActivity;
import com.example.RestoranZomato.ui.base.MvpPresenter;


@PerActivity
public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void fetchRestaurants(String query, Double lat, Double lon, int skip);

    void saveMyLocation(double lat, double lng, String city, String street);

    void clearLocationPreference();
}
