package com.example.RestoranZomato.ui.location;

import com.example.RestoranZomato.data.network.model.LocationResponse;
import com.example.RestoranZomato.ui.base.MvpView;

public interface LocationMvpView extends MvpView {
    void onLocationList(LocationResponse locationResponse);

    void onBackPressed();
}
