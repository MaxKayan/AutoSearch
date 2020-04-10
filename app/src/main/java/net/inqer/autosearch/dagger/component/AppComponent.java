package net.inqer.autosearch.dagger.component;

import android.app.Application;

import net.inqer.autosearch.BaseApplication;
import net.inqer.autosearch.SessionManager;
import net.inqer.autosearch.dagger.module.ActivityBuilderModule;
import net.inqer.autosearch.dagger.module.AppModule;
import net.inqer.autosearch.dagger.module.FragmentBuilderModule;
import net.inqer.autosearch.dagger.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        FragmentBuilderModule.class,
        ViewModelFactoryModule.class,
        AppModule.class
})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
