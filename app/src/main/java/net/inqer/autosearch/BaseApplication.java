package net.inqer.autosearch;

import android.app.Application;

//import net.inqer.autosearch.dagger.component.DaggerAppComponent;

import net.inqer.autosearch.dagger.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {
    private static final String TAG = "BaseApplication";

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
//        return null;
    }
}
