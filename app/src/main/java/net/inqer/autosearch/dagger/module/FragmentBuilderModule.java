package net.inqer.autosearch.dagger.module;

import net.inqer.autosearch.dagger.module.viewmodel.FiltersViewModelModule;
import net.inqer.autosearch.dagger.module.viewmodel.ParametersViewModelModule;
import net.inqer.autosearch.ui.fragment.filters.FiltersFragment;
import net.inqer.autosearch.ui.fragment.parameters.ParametersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = {MainModule.class, ParametersViewModelModule.class})
    abstract ParametersFragment contributeParametersFragment();

    @ContributesAndroidInjector(modules = {MainModule.class, FiltersViewModelModule.class})
    abstract FiltersFragment contributeFiltersFragment();
}
