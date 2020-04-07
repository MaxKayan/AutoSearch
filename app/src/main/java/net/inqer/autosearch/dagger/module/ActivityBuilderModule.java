package net.inqer.autosearch.dagger.module;

import net.inqer.autosearch.ui.launcher.LauncherActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract LauncherActivity contributeLauncherActivity();
}
