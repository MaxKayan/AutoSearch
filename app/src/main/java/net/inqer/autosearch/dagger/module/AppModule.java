package net.inqer.autosearch.dagger.module;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import net.inqer.autosearch.AppDatabase;
import net.inqer.autosearch.R;
import net.inqer.autosearch.data.service.AccountClient;

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
    AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                AppDatabase.class,
                "application_database")
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.i(TAG, "RoomDatabase: onCreate: called!");
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Log.d(TAG, "RoomDatabase: onOpen: called!");
                    }
                })
                .build();
    }

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
