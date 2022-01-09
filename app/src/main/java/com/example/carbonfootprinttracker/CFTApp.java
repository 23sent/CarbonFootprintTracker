package com.example.carbonfootprinttracker;

import android.app.Application;
import android.content.Context;

public class CFTApp extends Application {
    private static CFTApp instance;
    public CFTApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
