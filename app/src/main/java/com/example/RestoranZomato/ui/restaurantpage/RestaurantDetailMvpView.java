package com.example.RestoranZomato.ui.restaurantpage;

import com.example.RestoranZomato.data.network.model.RestaurantDetailResponse;
import com.example.RestoranZomato.ui.base.MvpView;

public interface RestaurantDetailMvpView extends MvpView {
    void onRestaurantDetail(RestaurantDetailResponse restaurantDetailResponse);
}
