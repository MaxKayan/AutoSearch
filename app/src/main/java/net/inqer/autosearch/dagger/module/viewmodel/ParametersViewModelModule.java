package net.inqer.autosearch.dagger.module.viewmodel;

import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.dagger.annotation.ViewModelKey;
import net.inqer.autosearch.ui.fragment.parameters.ParametersViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ParametersViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ParametersViewModel.class)
    public abstract ViewModel bindParametersViewModel(ParametersViewModel viewModel);
}
