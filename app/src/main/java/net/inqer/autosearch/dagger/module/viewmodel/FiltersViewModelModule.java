package net.inqer.autosearch.dagger.module.viewmodel;

import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.dagger.annotation.ViewModelKey;
import net.inqer.autosearch.ui.fragment.filters.FiltersViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class FiltersViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FiltersViewModel.class)
    public abstract ViewModel bindFiltersViewModel(FiltersViewModel viewModel);
}
