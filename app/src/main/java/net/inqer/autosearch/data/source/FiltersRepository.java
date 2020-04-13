package net.inqer.autosearch.data.source;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import net.inqer.autosearch.data.model.Event;
import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.local.dao.FilterDao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltersRepository {
    private static final String TAG = "FiltersRepository";

    private final FilterDao dao;
    private final MainApi api;

    private MediatorLiveData<Event<List<Filter>>> observableFilters = new MediatorLiveData<>();

    @Inject
    public FiltersRepository(FilterDao filterDao, MainApi mainApi) {
        this.dao = filterDao;
        this.api = mainApi;

        subscribeObservers();
    }

    private void subscribeObservers() {
        observableFilters.addSource(dao.observeFilters(), filters -> {
            Log.d(TAG, "subscribeObservers: setting new Success event");
            observableFilters.postValue(Event.success(filters));
        });
    }

    public LiveData<Event<List<Filter>>> observeFilters() {
        return observableFilters;
    }

    public void resetFilterObserver() {
        observableFilters.removeSource(dao.observeFilters());
        subscribeObservers();
    }

    public void refreshFilters() {
        observableFilters.postValue(Event.loading(null));
        api.getFilters().enqueue(new Callback<PageResponse<Filter>>() {
            @Override
            public void onResponse(@NotNull Call<PageResponse<Filter>> call, @NotNull Response<PageResponse<Filter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reloadFilters(response.body().getResults());
                } else {
                    Log.w(TAG, "onResponse: failed to get filters" +
                            "\n code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PageResponse<Filter>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: Error: ", t);
                observableFilters.postValue(Event.error(String.valueOf(t.getMessage()), null));
            }
        });
    }


    public void createFilter() {
    }


    @SuppressWarnings("UnusedReturnValue")
    private Disposable saveFilters(List<Filter> filters) {
        return dao.insertAllFilters(filters)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    Log.d(TAG, "saveFilters: Successfully saved filters");
                }, throwable -> {
                    Log.e(TAG, "saveFilters: Error: ", throwable);
                });
    }

    @SuppressWarnings("UnusedReturnValue")
    public Disposable clearFilters() {
        return dao.deleteAllFilters()
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    Log.d(TAG, "clearFilters: Successfully deleted all filters");
                }, throwable -> {
                    Log.e(TAG, "clearFilters: Error: ", throwable);
                });
    }


    public Disposable reloadFilters(List<Filter> filters) {
        return dao.deleteAllFilters()
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    saveFilters(filters);
                }, throwable -> {
                    Log.e(TAG, "clearFilters: Error: ", throwable);
                });
    }
}
