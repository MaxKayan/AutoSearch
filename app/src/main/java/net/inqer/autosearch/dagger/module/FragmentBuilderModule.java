package net.inqer.autosearch.dagger.module;

import net.inqer.autosearch.ui.fragment.parameters.ParametersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ParametersFragment contributeParametersFragment();
}
