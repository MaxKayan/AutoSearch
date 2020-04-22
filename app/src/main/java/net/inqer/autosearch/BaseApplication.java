package net.inqer.autosearch;

import android.content.Context;
import android.net.ConnectivityManager;

import net.inqer.autosearch.dagger.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {
    private static final String TAG = "BaseApplication";

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .connectivityManager((ConnectivityManager) this.getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE))
                .build();
    }

}
