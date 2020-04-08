package net.inqer.autosearch.dagger.module.viewmodel;

import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.dagger.annotation.ViewModelKey;
import net.inqer.autosearch.ui.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}
