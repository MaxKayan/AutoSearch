package net.inqer.autosearch.dagger.module.viewmodel;

import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.dagger.annotation.ViewModelKey;
import net.inqer.autosearch.ui.launcher.LauncherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LauncherViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel.class)
    public abstract ViewModel bindLauncherViewModel(LauncherViewModel viewModel);
}
