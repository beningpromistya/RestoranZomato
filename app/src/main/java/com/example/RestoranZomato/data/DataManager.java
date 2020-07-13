package com.example.RestoranZomato.data;


import com.example.RestoranZomato.data.db.DbHelper;
import com.example.RestoranZomato.data.network.ApiClient;
import com.example.RestoranZomato.data.prefs.PreferencesHelper;



public interface DataManager extends DbHelper, PreferencesHelper, ApiClient {

    void updateApiHeader(String accessToken);
}
