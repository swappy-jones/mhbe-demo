package com.example.mhbesample;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        registerActivityLifecycleCallbacks(new LifeCycleCallbacks());

    }
}
