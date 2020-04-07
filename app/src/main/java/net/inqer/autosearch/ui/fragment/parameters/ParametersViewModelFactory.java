package net.inqer.autosearch.ui.fragment.parameters;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class ParametersViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    public ParametersViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }
}
