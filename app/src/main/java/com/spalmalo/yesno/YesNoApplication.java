package com.spalmalo.yesno;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class YesNoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
