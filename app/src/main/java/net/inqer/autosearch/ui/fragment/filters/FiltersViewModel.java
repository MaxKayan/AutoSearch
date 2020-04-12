package net.inqer.autosearch.ui.fragment.filters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.Event;
import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.FiltersRepository;

import java.util.List;

import javax.inject.Inject;

public class FiltersViewModel extends ViewModel {
    private static final String TAG = "FiltersViewModel";
    private final FiltersRepository repository;
    private MediatorLiveData<List<Filter>> filtersList = new MediatorLiveData<>();

    @Inject
    FiltersViewModel(FiltersRepository filtersRepository) {
        this.repository = filtersRepository;
        subscribeObservers();
    }

    private void subscribeObservers() {
        filtersList.addSource(repository.observeFilters(), event -> {
            if (event.status == Event.ResultStatus.SUCCESS) {
                filtersList.setValue(event.data);
            }
        });
    }

    LiveData<Event<List<Filter>>> observeFilterEvents() {
        return repository.observeFilters();
    }

    LiveData<List<Filter>> observeFilterData() {
        return filtersList;
    }

    void refreshData() {
        repository.refreshFilters();
    }

    void deleteFilters() {
        repository.clearFilters();
    }
}
