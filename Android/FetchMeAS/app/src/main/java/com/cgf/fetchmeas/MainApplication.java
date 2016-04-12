package com.cgf.fetchmeas;

import android.app.Application;
import android.util.Log;

/**
 * Created by chenguanfu on 3/28/2016.
 */
public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MainApplication----in----");
        // add some init before the activity like so resources
        Log.d(TAG, "MainApplication----out----");
    }
}
