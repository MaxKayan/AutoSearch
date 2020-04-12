package net.inqer.autosearch.ui.fragment.filters;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.service.MainApi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltersViewModel extends ViewModel {
    private static final String TAG = "FiltersViewModel";
    @Inject
    MainApi api;
    private MutableLiveData<List<Filter>> filtersList = new MutableLiveData<>();

    @Inject
    FiltersViewModel() {
    }

    public void updateData() {
        api.getFilters().enqueue(new Callback<PageResponse<Filter>>() {
            @Override
            public void onResponse(@NotNull Call<PageResponse<Filter>> call, @NotNull Response<PageResponse<Filter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    filtersList.postValue(response.body().getResults());

                    for (Filter filter : response.body().getResults()) {
                        Log.d(TAG, filter.getCarMark() + " " + filter.getCarModel());
                    }
                } else {
                    Log.w(TAG, "onResponse: failed to get filters" +
                            "\n code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PageResponse<Filter>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: Error: ", t);
            }
        });
    }

    public LiveData<List<Filter>> observeFilters() {
        return filtersList;
    }
}
