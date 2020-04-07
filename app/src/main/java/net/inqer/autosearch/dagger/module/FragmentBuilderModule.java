package net.inqer.autosearch.dagger.module;

import net.inqer.autosearch.dagger.module.viewmodel.ParametersViewModelModule;
import net.inqer.autosearch.ui.fragment.parameters.ParametersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = {ParametersViewModelModule.class})
    abstract ParametersFragment contributeParametersFragment();
}
