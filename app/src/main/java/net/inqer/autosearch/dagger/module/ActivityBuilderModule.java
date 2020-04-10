package net.inqer.autosearch.dagger.module;

import net.inqer.autosearch.dagger.module.viewmodel.LoginViewModelModule;
import net.inqer.autosearch.ui.MainActivity;
import net.inqer.autosearch.ui.launcher.LauncherActivity;
import net.inqer.autosearch.ui.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {LauncherModule.class})
    abstract LauncherActivity contributeLauncherActivity();

    @ContributesAndroidInjector(modules = {LauncherModule.class, LoginViewModelModule.class})
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = {LauncherModule.class})
    abstract MainActivity contributeMainActivity();
}
