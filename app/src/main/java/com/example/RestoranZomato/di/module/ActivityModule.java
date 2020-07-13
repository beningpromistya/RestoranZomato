package com.example.RestoranZomato.di.module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import com.example.RestoranZomato.di.PerActivity;
import com.example.RestoranZomato.ui.home.HomeMvpPresenter;
import com.example.RestoranZomato.ui.home.HomeMvpView;
import com.example.RestoranZomato.ui.home.HomePresenter;
import com.example.RestoranZomato.ui.home.restaurantlist.RestaurantAdapter;
import com.example.RestoranZomato.ui.home.restaurantlist.RestaurantListInterface;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerMvpPresenter;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerMvpView;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerPresenter;
import com.example.RestoranZomato.ui.location.LocationMvpPresenter;
import com.example.RestoranZomato.ui.location.LocationMvpView;
import com.example.RestoranZomato.ui.location.LocationPresenter;
import com.example.RestoranZomato.ui.restaurantpage.RestaurantDetailMvpPresenter;
import com.example.RestoranZomato.ui.restaurantpage.RestaurantDetailMvpView;
import com.example.RestoranZomato.ui.restaurantpage.RestaurantDetailPresenter;
import com.example.RestoranZomato.ui.splash.SplashMvpPresenter;
import com.example.RestoranZomato.ui.splash.SplashMvpView;
import com.example.RestoranZomato.ui.splash.SplashPresenter;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    HomeMvpPresenter<HomeMvpView> provideHomePresenter(
            HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LocationMvpPresenter<LocationMvpView> provideLocationPresenter(
            LocationPresenter<LocationMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RestaurantDetailMvpPresenter<RestaurantDetailMvpView> provideRestaurantDetailPresenter(
            RestaurantDetailPresenter<RestaurantDetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ImageViewerMvpPresenter<ImageViewerMvpView> provideImageViewerPresenter(
            ImageViewerPresenter<ImageViewerMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RestaurantAdapter provideRestaurantAdapter(RestaurantListInterface restaurantListInterface) {
        return new RestaurantAdapter(mActivity, restaurantListInterface);
    }

    @Provides
    LinearLayoutManager providesLinearLayoutManager() {
        return new LinearLayoutManager(mActivity);
    }

    @Provides
    @PerActivity
    RestaurantListInterface providerRestaurantListInterface() {
        if (mActivity instanceof RestaurantListInterface)
            return (RestaurantListInterface) mActivity;
        return null;
    }
}
