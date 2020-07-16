package net.inqer.autosearch.util;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util {
    private static final String TAG = "Util";

    /**
     * TODO: This method may have a better place to be.
     *
     * @param client Working okHttp client to run some GET requests
     */
    public static String getActiveServerUrl(OkHttpClient client) {
        for (String currentUrl : Config.BASE_URL_SET) {
            Log.d(TAG, "provideRetrofitInstance: " + currentUrl);
            Log.d(TAG, "getActiveServerUrl: String format test: " + String.format(Config.BASE_URL_FORMAT, currentUrl));

            Request request = new Request.Builder()
                    .url(currentUrl)
                    .build();

            CountDownLatch countDownLatch = new CountDownLatch(1);

            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    return currentUrl;
                }
            } catch (IOException e) {
                Log.d(TAG, "getActiveServerUrl: failed to access " + currentUrl, e);
            }

        }

        Log.w(TAG, "getActiveServerUrl: Failed to find a working server URL!");
        return Config.BASE_URL_SET[0];
    }
}
