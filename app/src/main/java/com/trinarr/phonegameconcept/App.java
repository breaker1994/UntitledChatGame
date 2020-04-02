package com.trinarr.phonegameconcept;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;

    public static int chatID = -1;
    public static int peopleID = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        //context = getApplicationContext();
    }
    public static Context getAppContext() {
        return context;
    }
}