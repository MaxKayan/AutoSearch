package net.inqer.autosearch.dagger.module;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import net.inqer.autosearch.R;
import net.inqer.autosearch.util.Constants;
import net.inqer.autosearch.util.TokenInjectionInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
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
    @Named("TOKEN")
    static String provideInitToken() {
        return "";
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(TokenInjectionInterceptor tokenInterceptor) {
        return new OkHttpClient.Builder()
//                .addInterceptor(chain -> {
//                    Request originalRequest = chain.request();
//                    Request newRequest = originalRequest.newBuilder()
//                            .header("Authorization", "Token ")
//                            .build();
//                    return chain.proceed(newRequest);
//                })
                .addInterceptor(tokenInterceptor)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

}
