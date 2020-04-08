package net.inqer.autosearch.dagger.module;

import android.app.Application;

import net.inqer.autosearch.data.service.MainApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    String provideSomeString(Application application) {
        return application.toString();
    }
}
