package com.example.RestoranZomato.data.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.example.RestoranZomato.data.db.model.DaoMaster;
import com.example.RestoranZomato.di.ApplicationContext;
import com.example.RestoranZomato.di.DatabaseInfo;
import com.example.RestoranZomato.util.AppLogger;



@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        AppLogger.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN "
                // + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
