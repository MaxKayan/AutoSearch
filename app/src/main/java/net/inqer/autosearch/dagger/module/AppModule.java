package net.inqer.autosearch.dagger.module;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.source.local.AppDatabase;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.local.dao.RegionDao;
import net.inqer.autosearch.util.Constants;
import net.inqer.autosearch.util.TokenInjectionInterceptor;

import java.text.SimpleDateFormat;

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
    private static final String TAG = "AppModule";

    @Singleton
    @Provides
    @Named("logo")
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.ic_android);
    }

    @Singleton
    @Provides
    static AppDatabase provideAppDatabase(Application application) {
        Log.i(TAG, "provideAppDatabase: creating Room database...");
        return Room.databaseBuilder(application, AppDatabase.class, "autosearch_database")
                .fallbackToDestructiveMigration()
//                .addCallback(roomCallback)
                .build();
    }

    @Singleton
    @Provides
    static FilterDao provideFilterDao(AppDatabase appDatabase) {
        return appDatabase.filterDao();
    }

    @Singleton
    @Provides
    static RegionDao provideRegionDao(AppDatabase appDatabase) {
        return appDatabase.regionDao();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(TokenInjectionInterceptor tokenInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Singleton
    @Provides
    static SimpleDateFormat provideDateFormat(Application application) {
        return new SimpleDateFormat("dd MMMM yyyy 'в' HH:mm:ss", application.getResources().getConfiguration().locale);
    }

}
