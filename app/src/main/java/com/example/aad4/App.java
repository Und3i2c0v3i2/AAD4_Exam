package com.example.aad4;

import android.app.Application;

import com.example.aad4.db.DBHelper;
import com.example.aad4.db.DBRepositoryImpl;
import com.example.aad4.prefs.PrefsRepository;
import com.example.aad4.prefs.PrefsRepositoryImpl;


public class App extends Application {


    // for intents
    public static final String OBJECT_ID = "id";

    private static DBRepositoryImpl dbRepository;
    private static PrefsRepository prefsRepository;


    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper dbHelper = DBHelper.getInstance(this);
        dbRepository = new DBRepositoryImpl(dbHelper);
        prefsRepository = new PrefsRepositoryImpl(this);

    }


    public static DBRepositoryImpl getDbRepository() {
        return dbRepository;
    }

    public static PrefsRepository getPrefsRepository() {
        return prefsRepository;
    }



}
