package com.ranspektor.andproj;

import android.app.Application;
import android.content.Context;

public class ANDApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
