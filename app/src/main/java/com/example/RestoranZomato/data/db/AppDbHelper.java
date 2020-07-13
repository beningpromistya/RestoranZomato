package com.example.RestoranZomato.data.db;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.RestoranZomato.data.db.model.DaoMaster;
import com.example.RestoranZomato.data.db.model.DaoSession;




@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }
}
