package net.inqer.autosearch.dagger.module;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.service.AccountClient;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    @Named("logo")
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.ic_android);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(Application application, OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(application.getResources().getString(R.string.base_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
//                .addInterceptor(chain -> {
//                    Request originalRequest = chain.request();
//                    Request newRequest = originalRequest.newBuilder()
//                            .header("Interceptor-Header", "This is a dagger-provided instance")
//                            .build();
//                    return chain.proceed(newRequest);
//                })
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    AccountClient provideAccountClient(Retrofit retrofit) {
        return retrofit.create(AccountClient.class);
    }

}
