package net.inqer.autosearch.ui.fragment.filters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.Event;
import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.FiltersRepository;

import java.util.List;

import javax.inject.Inject;

public class FiltersViewModel extends ViewModel {
    private static final String TAG = "FiltersViewModel";
    private final FiltersRepository repository;
    private MutableLiveData<List<Filter>> filtersList = new MutableLiveData<>();

    @Inject
    FiltersViewModel(FiltersRepository filtersRepository) {
        this.repository = filtersRepository;
        subscribeObservers();
    }

    private void subscribeObservers() {
//        filtersList.addSource(repository.observeFilters(), event -> {
//            if (event.status == Event.ResultStatus.SUCCESS) {
//                filtersList.setValue(event.data);
//            }
//        });

    }

    LiveData<Event<List<Filter>>> observeFilterEvents() {
//        return repository.observeFilters();
        return LiveDataReactiveStreams.fromPublisher(repository.getAll()
        .map(filters -> Event.success(filters)));
    }

//    LiveData<List<Filter>> observeFilterData() {
//        return filtersList;
//    }

    void resetFilterObserver() {
//        repository.resetFilterObserver();
    }

    void refreshData() {
//        repository.refreshFilters();
    }

    void deleteFilters() {
//        repository.clearFilters();
    }
}
