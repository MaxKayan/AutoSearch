package net.inqer.autosearch.util;

import android.net.ConnectivityManager;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkManager {
    private static final String TAG = "NetworkManager";

    private final ConnectivityManager cm;
    private boolean lastResult;

    @Inject
    public NetworkManager(ConnectivityManager cm) {
        this.cm = cm;
        lastResult = isNetworkAvailable();
    }

    public boolean isNetworkAvailable() {
        boolean result = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        Log.d(TAG, "isNetworkAvailable: " + result);
        lastResult = result;
        return result;
    }

    public boolean lastResult() {
        return lastResult;
    }
}
