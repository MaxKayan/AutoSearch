package net.inqer.autosearch.dagger.module;

import android.app.Application;

import net.inqer.autosearch.dagger.annotation.MainActivityScope;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.local.AppDatabase;
import net.inqer.autosearch.data.source.local.dao.CarMarkDao;
import net.inqer.autosearch.data.source.local.dao.CarModelDao;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.local.dao.RegionDao;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainActivityScope
    @Provides
    static FilterDao provideFilterDao(AppDatabase appDatabase) {
        return appDatabase.filterDao();
    }

    @Provides
    String provideSomeString(Application application) {
        return application.toString();
    }

    @MainActivityScope
    @Provides
    static RegionDao provideRegionDao(AppDatabase appDatabase) {
        return appDatabase.regionDao();
    }

    @MainActivityScope
    @Provides
    static CarMarkDao provideCarMarkDao(AppDatabase appDatabase) {
        return appDatabase.carMarkDao();
    }

    @MainActivityScope
    @Provides
    static CarModelDao provideCarModelDao(AppDatabase appDatabase) {
        return appDatabase.carModelDao();
    }

    @MainActivityScope
    @Provides
    MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
