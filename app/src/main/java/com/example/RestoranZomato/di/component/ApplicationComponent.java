package com.example.RestoranZomato.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.example.RestoranZomato.RestoranZomato;
import com.example.RestoranZomato.data.DataManager;
import com.example.RestoranZomato.data.db.DbHelper;
import com.example.RestoranZomato.data.network.ApiClient;
import com.example.RestoranZomato.data.network.ApiHeader;
import com.example.RestoranZomato.data.prefs.PreferencesHelper;
import com.example.RestoranZomato.di.ApplicationContext;
import com.example.RestoranZomato.di.module.ApplicationModule;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    //inject app
    void inject(RestoranZomato app);

    @ApplicationContext
    Context applicationContext();

    RestoranZomato application();

    DataManager dataManager();

    ApiClient apiHelper();

    DbHelper dbHelper();

    ApiHeader apiHeader();

    PreferencesHelper preferenceHelper();
}
