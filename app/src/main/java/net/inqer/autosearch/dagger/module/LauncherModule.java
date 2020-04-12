package net.inqer.autosearch.dagger.module;

import android.app.Application;

import net.inqer.autosearch.data.source.api.AuthApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class LauncherModule {

    @Provides
    static AuthApi provideAccountApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    @Provides
    @Named("launcher")
    String provideLauncherString(Application application) {
        return application.toString();
    }
}
