package net.inqer.autosearch.ui.fragment.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.EditableFilter;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.source.repository.LocationsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    private static final String TAG = "SearchViewModel";
    private final LocationsRepository locationsRepository;
    private MutableLiveData<EditableFilter> currentEditableFilter = new MutableLiveData<>(new EditableFilter());

    @Inject
    SearchViewModel(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }

    LiveData<EditableFilter> getCurrentFilter() {
        return currentEditableFilter;
    }

    public void reNew(EditableFilter instance) {
        currentEditableFilter.setValue(instance);
    }

    public Single<Region> getRegionById(String slug) {
        return locationsRepository.getRegionById(slug);
    }

    public Single<List<City>> getCitiesByRegion(Region region) {
        return locationsRepository.getCitiesPageByRegion(region)
                .map(PageResponse::getResults);
    }

    public Flowable<List<Region>> observeRegions() {
        return locationsRepository.getAllRegions();
    }

    public void setRegion(String rSlug) {
        Disposable d = locationsRepository.getRegionById(rSlug)
                .subscribe(region -> {
                    EditableFilter filter = currentEditableFilter.getValue();
                    if (filter != null && region != null) {
                        filter.setRegion(region);
                        currentEditableFilter.postValue(filter);
                    } else Log.d(TAG, "setRegion: editable filter should never be null");
                }, throwable -> {
                    Log.e(TAG, "setRegion: Error: ", throwable);
                });
    }
}
