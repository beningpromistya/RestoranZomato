package com.example.RestoranZomato.ui.home;

import com.example.RestoranZomato.data.network.model.RestaurantsResponse;
import com.example.RestoranZomato.ui.base.MvpView;

public interface HomeMvpView extends MvpView {

    void onRestaurantResponse(RestaurantsResponse restaurantsResponse);

    void onMoreRestaurantResponse(RestaurantsResponse restaurantsResponse);
}
