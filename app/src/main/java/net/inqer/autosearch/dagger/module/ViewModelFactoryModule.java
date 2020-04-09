package net.inqer.autosearch.dagger.module;

import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.util.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelFactory);
}
