package net.inqer.autosearch.util;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util {
    private static final String TAG = "Util";

    private static String apiUrl;

    public static String getApiUrl() {
        return apiUrl;
    }

    public static final String[] DISPLACEMENTS = {"Все", "0,6", "0,8", "1,0", "1,2", "1,4", "1,6",
            "1,8", "2,0", "2,2", "2,4", "2,6", "2,8", "3,0", "3,5", "4,0", "4,5", "5,0", "5,5", "6,0"};

    /**
     * TODO: This method may have a better place to be.
     *
     * @param client Working okHttp client to run some GET requests
     */
    public static String getActiveServerUrl(OkHttpClient client) {
        CountDownLatch countDownLatch = new CountDownLatch(Config.BASE_URL_SET.length);
        for (String currentUrl : Config.BASE_URL_SET) {
            Log.d(TAG, "provideRetrofitInstance: " + currentUrl);
            Log.d(TAG, "getActiveServerUrl: String format test: " + String.format(Config.BASE_URL_FORMAT, currentUrl));

            Request request = new Request.Builder()
                    .url(currentUrl)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(TAG, "onFailure: Failed to connect: " + e.getMessage());
                    countDownLatch.countDown();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    apiUrl = currentUrl;
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Log.e(TAG, "getActiveServerUrl: Interrupted: " + e.getMessage());
        }

        if (apiUrl == null) {
            Log.w(TAG, "getActiveServerUrl: Failed to find a working server URL!");
            return Config.BASE_URL_SET[0];
        }

        return apiUrl;
    }
}
