package net.inqer.autosearch.data.networking;

import android.content.res.Resources;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.preferences.AuthParametersProvider;
import net.inqer.autosearch.data.service.AccountClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.inqer.autosearch.MainActivity.parametersProvider;

public class RetrofitService {

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Authentication", "Token "+parametersProvider.getValue(Resources.getSystem().getString(R.string.saved_token_key)))
                        .build();

                return chain.proceed(newRequest);
            })
            .addInterceptor(loggingInterceptor).build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Resources.getSystem().getString(R.string.base_api_url))
            .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
            .build();

    public static AccountClient accountClient = retrofit.create(AccountClient.class);

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
