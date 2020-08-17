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

    Single<List<City>> getCitiesByRegion(Region region) {
        return locationsRepository.getCitiesPageByRegion(region)
                .map(PageResponse::getResults);
    }

    Flowable<List<Region>> observeRegions() {
        return optionsRepository.observeRegions();
    }

    Flowable<List<CarMark>> observeMarks() {
        return optionsRepository.observeAllMarks()
                .doOnNext(carMarks -> {
                    Log.d(TAG, "observeMarks: next: " + carMarks);
                });
    }

    Flowable<List<CarModel>> observeModelsByMark(CarMark carMark) {
        return optionsRepository.observeModelsByMark(carMark);
    }


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
        editFilter(filter -> {
            if (filter.getRegion() != null && !filter.getRegion().isSameModelAs(instance)) {
                filter.setCity(null);
            }
            filter.setRegion(instance);
        });
    }

    void setCity(City instance) {
        editFilter(filter -> filter.setCity(instance));
    }

    void setMark(CarMark instance) {
        editFilter(filter -> {
            if (filter.getCarMark() != null && !filter.getCarMark().isSameModelAs(instance)) {
                filter.setCarModel(null);
            }
            filter.setCarMark(instance);
        });
    }

    public void setModel(CarModel instance) {
        editFilter(filter -> filter.setCarModel(instance));
    }

    public void setPrice(Integer from, Integer to) {
        editFilter(filter -> {
            filter.setPriceMinimum(from);
            filter.setPriceMaximum(to);
        });
    }

    public void setYear(Integer from, Integer to) {
        editFilter(filter -> {
            filter.setManufactureYearMin(from);
            filter.setManufactureYearMax(to);
        });
    }

    public void setDisplacement(String from, String to) {
        editFilter(filter -> {
            filter.setEngineDisplacementMin(from);
            filter.setEngineDisplacementMax(to);
        });
    }

    public void setRadius(Integer radius) {
        editFilter(filter -> filter.setRadius(radius));
    }

    public void setTransmission(String value) {
        editFilter(filter -> filter.setTransmission(value));
    }

    public void setHull(String value) {
        editFilter(filter -> filter.setHull(value));
    }

    public void setFuel(String value) {
        editFilter(filter -> filter.setFuel(value));
    }

    /**
     * Performs the obtaining of {@link EditableFilter} instance, runs the <var>operation</var>
     * callback if <var>filter</var> is not null.
     * Logs a new error if failed to retrieve the instance.
     *
     * @param operation Callback that edits current filter instance from {@link #currentEditableFilter}
     * @see FilterOperation
     */
    private void editFilter(FilterOperation operation) {
        EditableFilter filter = currentEditableFilter.getValue();
        if (filter != null) {
            operation.run(filter);
            currentEditableFilter.setValue(filter);
        } else {
            Log.e(TAG, "handleError: Current filter value in live data is null!");
        }
    }


    /**
     * Used to wrap various filter manipulations with common operations. <p> I.e.: Get filter from liveData
     * and check if it's not null, call {@link #run(EditableFilter)} afterwards.
     *
     * @see #editFilter(FilterOperation)
     */
    public interface FilterOperation {
        void run(EditableFilter filter);
    }
}
