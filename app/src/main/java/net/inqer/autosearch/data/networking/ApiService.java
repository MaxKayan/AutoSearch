package net.inqer.autosearch.data.networking;

import android.content.res.Resources;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.service.AccountClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.inqer.autosearch.MainActivity.parametersProvider;

public class ApiService {

    private static final String BASE_URL = "http://8fde093bb098.sn.mynetname.net/api/";

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Token "+"ab4a768389057680f4f5a2cf7773281fefb791d5")
                        .build();

                return chain.proceed(newRequest);
            })
            .addInterceptor(loggingInterceptor).build();

    public static AccountClient retrofitService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(AccountClient.class);
    }
}
