package net.inqer.autosearch.ui.fragment.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.CarMark;
import net.inqer.autosearch.data.model.CarModel;
import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.EditableFilter;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.repository.LocationsRepository;
import net.inqer.autosearch.data.source.repository.OptionsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {
    private static final String TAG = "SearchViewModel";
    private final LocationsRepository locationsRepository;
    private final OptionsRepository optionsRepository;
    private final MainApi api;
    private MutableLiveData<EditableFilter> currentEditableFilter = new MutableLiveData<>(new EditableFilter());

    @Inject
    SearchViewModel(LocationsRepository locationsRepository, OptionsRepository optionsRepository, MainApi api) {
        this.locationsRepository = locationsRepository;
        this.optionsRepository = optionsRepository;
        this.api = api;
    }

    LiveData<EditableFilter> getCurrentFilter() {
        return currentEditableFilter;
    }

    public void reNew(EditableFilter instance) {
        currentEditableFilter.setValue(instance);
    }

    public Single<Region> getRegionById(long id) {
        return locationsRepository.getRegionById(id);
    }

    Single<List<City>> getCitiesByRegion(Region region) {
        return locationsRepository.getCitiesPageByRegion(region)
                .map(PageResponse::getResults);
    }

    Flowable<List<Region>> observeRegions() {
        return locationsRepository.getAllRegions();
    }

    Flowable<List<CarMark>> observeMarks() {
        return optionsRepository.observeAllMarks();
    }

    Flowable<List<CarModel>> observeModelsByMark(CarMark carMark) {
        return optionsRepository.observeModelsByMark(carMark);
    }

//    public void setRegion(String rSlug) {
//        Disposable d = locationsRepository.getRegionById(rSlug)
//                .subscribe(region -> {
//                    EditableFilter filter = currentEditableFilter.getValue();
//                    if (filter != null && region != null) {
//                        filter.setRegion(region);
//                        currentEditableFilter.postValue(filter);
//                    } else Log.d(TAG, "setRegion: editable filter should never be null");
//                }, throwable -> {
//                    Log.e(TAG, "setRegion: Error: ", throwable);
//                });
//    }

    /**
     * Submit new filter to backend via POST request.
     */
    void submitFilter() {
        EditableFilter filter = currentEditableFilter.getValue();
        if (filter != null) {
            Disposable d = api.createFilter(filter)
            .subscribeOn(Schedulers.io())
            .subscribe(() -> {
                Log.d(TAG, "submitFilter: completed");
            }, throwable -> {
                Log.e(TAG, "submitFilter: ", throwable);
            });
        }
    }

    public void setRegion(Region instance) {
        EditableFilter filter = currentEditableFilter.getValue();
        if (instance != null && filter != null) {
            filter.setRegion(instance);
            currentEditableFilter.setValue(filter);
        } else Log.e(TAG, "setMark: null data");
    }

    void setCity(City instance) {
        EditableFilter filter = currentEditableFilter.getValue();
        if (instance != null && filter != null) {
            filter.setCity(instance);
            currentEditableFilter.setValue(filter);
        } else Log.e(TAG, "setModel: null data");
    }

    void setMark(CarMark instance) {
        EditableFilter filter = currentEditableFilter.getValue();
        if (instance != null && filter != null) {
            filter.setCarMark(instance);
            currentEditableFilter.setValue(filter);
        } else Log.e(TAG, "setRegion: null data");
    }

    public void setModel(CarModel instance) {
        EditableFilter filter = currentEditableFilter.getValue();
        if (instance != null && filter != null) {
            filter.setCarModel(instance);
            currentEditableFilter.setValue(filter);
        } else Log.e(TAG, "setModel: null data");
    }
}
