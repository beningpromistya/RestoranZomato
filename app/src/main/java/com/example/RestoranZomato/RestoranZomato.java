package com.example.RestoranZomato;

import android.app.Application;

import java.io.File;

import com.example.RestoranZomato.di.component.ApplicationComponent;
import com.example.RestoranZomato.di.component.DaggerApplicationComponent;
import com.example.RestoranZomato.di.module.ApplicationModule;
import com.example.RestoranZomato.util.AppLogger;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

public class RestoranZomato extends Application {

    private ApplicationComponent mApplicationComponent;

    public void onCreate() {
        super.onCreate();

        File cacheFile = new File(getCacheDir(), "response");

        //App component
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);


        //Logger
        AppLogger.init();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}