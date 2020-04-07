package net.inqer.autosearch.ui.fragment.parameters;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.DataRepository;

import javax.inject.Inject;

public class ParametersViewModelFactory implements ViewModelProvider.Factory {
    private final DataRepository repository;

    @Inject
    public ParametersViewModelFactory(DataRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ParametersViewModel.class)) {
            return (T) new ParametersViewModel(repository);
        } else throw new IllegalArgumentException("ViewModel Not Found");
    }
}
