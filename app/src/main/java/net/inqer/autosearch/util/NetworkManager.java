package net.inqer.autosearch.util;

import android.net.ConnectivityManager;
import android.util.Log;

import net.inqer.autosearch.util.bus.RxBus;
import net.inqer.autosearch.util.bus.RxBusEventTest;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkManager {
    private static final String TAG = "NetworkManager";

    private final ConnectivityManager cm;
    private final RxBus rxBus;
    private boolean lastResult;

    @Inject
    public NetworkManager(ConnectivityManager cm, RxBus rxBus) {
        this.cm = cm;
        this.rxBus = rxBus;
        lastResult = isNetworkAvailable();
    }

    public boolean isNetworkAvailable() {
        boolean result = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        Log.d(TAG, "isNetworkAvailable: " + result);
        lastResult = result;
        if (!result) {
            rxBus.publish(new RxBusEventTest.Error(true, "Потеряна связь с Интернетом"));
        }
        return result;
    }

    public boolean lastResult() {
        return lastResult;
    }
}
