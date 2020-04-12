package net.inqer.autosearch.ui.fragment.parameters;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.service.MainApi;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParametersViewModel extends ViewModel {
    private static final String TAG = "ParametersViewModel";

    private final MainApi mainApi;
    private MutableLiveData<AccountProperties> accountProperties = new MutableLiveData<>();

    @Inject
    ParametersViewModel(MainApi api) {
        Log.d(TAG, "ParametersViewModel: viewmodel is working...");
        this.mainApi = api;
    }

    void updateAccountData() {
        Call<AccountProperties> call = mainApi.getAccountProperties();
        call.enqueue(new Callback<AccountProperties>() {
            @Override
            public void onResponse(@NotNull Call<AccountProperties> call, @NotNull Response<AccountProperties> response) {
                if (response.isSuccessful()) {
                    accountProperties.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AccountProperties> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: Error: ", t);
            }
        });
    }


    LiveData<AccountProperties> getAccountProperties() {
        return accountProperties;
    }
}
