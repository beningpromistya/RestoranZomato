package com.example.RestoranZomato.di.component;

import dagger.Component;
import com.example.RestoranZomato.di.PerActivity;
import com.example.RestoranZomato.di.module.ActivityModule;
import com.example.RestoranZomato.ui.home.HomeActivity;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerActivity;
import com.example.RestoranZomato.ui.location.LocationActivity;
import com.example.RestoranZomato.ui.restaurantpage.RestaurantDetailActivity;
import com.example.RestoranZomato.ui.splash.SplashActivity;


@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    void inject(LocationActivity locationActivity);

    void inject(RestaurantDetailActivity restaurantDetailActivity);

    void inject(ImageViewerActivity imageViewerActivity);
}
