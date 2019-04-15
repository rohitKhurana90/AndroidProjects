package com.isha.delhi.attendance;

import android.app.Application;

public class IshaApp extends Application {


    private static IshaApp app;

    public static IshaApp getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

}
