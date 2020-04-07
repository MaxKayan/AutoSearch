package net.inqer.autosearch.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import net.inqer.autosearch.data.repository.LoginDataSource;
import net.inqer.autosearch.data.repository.LoginRepository;

import javax.inject.Inject;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {
    @Inject
    LoginDataSource loginDataSource;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(loginDataSource));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
