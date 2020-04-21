package net.inqer.autosearch.dagger.module;

import net.inqer.autosearch.dagger.module.viewmodel.FiltersViewModelModule;
import net.inqer.autosearch.dagger.module.viewmodel.ParametersViewModelModule;
import net.inqer.autosearch.dagger.module.viewmodel.SearchViewModelModule;
import net.inqer.autosearch.ui.dialog.RegionDialog;
import net.inqer.autosearch.ui.fragment.filters.FiltersFragment;
import net.inqer.autosearch.ui.fragment.filters.ResultsFragment;
import net.inqer.autosearch.ui.fragment.parameters.ParametersFragment;
import net.inqer.autosearch.ui.fragment.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = {MainModule.class, ParametersViewModelModule.class})
    abstract ParametersFragment contributeParametersFragment();

    @ContributesAndroidInjector(modules = {MainModule.class, FiltersViewModelModule.class})
    abstract FiltersFragment contributeFiltersFragment();

    @ContributesAndroidInjector(modules = {MainModule.class, SearchViewModelModule.class})
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector(modules = {MainModule.class, FiltersViewModelModule.class})
    abstract ResultsFragment contributeAddEditFilterFragment();


    // Dialogs
    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract RegionDialog contributeTestDialogFragment();
}
