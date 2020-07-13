package com.example.RestoranZomato.ui.restaurantpage;

import com.example.RestoranZomato.di.PerActivity;
import com.example.RestoranZomato.ui.base.MvpPresenter;


@PerActivity
public interface RestaurantDetailMvpPresenter<V extends RestaurantDetailMvpView> extends MvpPresenter<V> {

    void fetchRestaurantDetails(String restaurantId);
}
