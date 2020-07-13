package com.example.RestoranZomato.ui.base;


public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();

    void handleApiError();
}
