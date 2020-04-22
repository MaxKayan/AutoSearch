package net.inqer.autosearch.ui.fragment.filters;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.QueryFilter;
import net.inqer.autosearch.data.source.repository.FiltersRepository;
import net.inqer.autosearch.util.bus.RxBus;
import net.inqer.autosearch.util.bus.RxBusEvent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FiltersViewModel extends ViewModel {
    private static final String TAG = "FiltersViewModel";

    private final FiltersRepository repository;
    private final RxBus rxBus;

    private MutableLiveData<List<QueryFilter>> filtersLiveData = new MutableLiveData<>();
    private CompositeDisposable disposeBag = new CompositeDisposable();

    @Inject
    FiltersViewModel(FiltersRepository filtersRepository, RxBus rxBus) {
        this.repository = filtersRepository;
        this.rxBus = rxBus;
        subscribeObservers();
        refreshData();
    }

    private void subscribeObservers() {
        disposeBag.clear();

        Disposable filtersSub =
                repository.getAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(filters -> {
                            Log.d(TAG, "subscribeObservers: new data received: " + filters.size());
                            filtersLiveData.postValue(filters);
                        }, throwable -> {
                            Log.e(TAG, "subscribeObservers: Error: " + throwable.getMessage()
                                    + " " + throwable.getClass(), throwable);
                        }, () -> {
                            Log.w(TAG, "subscribeObservers: Flow Completed");
                        });

        disposeBag.add(filtersSub);
    }

    LiveData<List<QueryFilter>> observeFilters() {
        return filtersLiveData;
    }

    void refreshData() {
        rxBus.publish(RxBusEvent.loading("Загрузка фильтров", false));
        Disposable rf = repository.refreshData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    Log.i(TAG, "refreshData: refreshed data successfully!");
                    rxBus.publish(RxBusEvent.message("refreshed data successfully!", true));
                }, throwable -> {
                    Log.e(TAG, "refreshData: Failed to refresh: " + throwable.getMessage() + " " + throwable.getClass(), throwable);
                    rxBus.publish(RxBusEvent.error(throwable.getMessage(), true, throwable));
                });
    }


    Completable deleteFilterRx(QueryFilter filter) {
        return repository.delete(filter);
    }

    void deleteFilters() {
        Disposable clearSub =
                repository.clear()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.d(TAG, "deleteFilters: Completed!");
                        }, throwable -> {
                            Log.e(TAG, "deleteFilters: Error:", throwable);
                            rxBus.publish(RxBusEvent.error(throwable.getMessage(), true, throwable));
                        });
    }


    private void dispose(Disposable disposable) {
        disposable.dispose();
    }
}
