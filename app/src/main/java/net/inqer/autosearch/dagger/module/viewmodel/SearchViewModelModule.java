package net.inqer.autosearch.dagger.module.viewmodel;

import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.dagger.annotation.ViewModelKey;
import net.inqer.autosearch.ui.fragment.search.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SearchViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    public abstract ViewModel bindSearchViewModel(SearchViewModel viewModel);
}
