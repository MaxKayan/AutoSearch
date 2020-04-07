package net.inqer.autosearch.ui.fragment.parameters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.networking.ApiService;
import net.inqer.autosearch.data.service.AccountClient;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParametersViewModel extends ViewModel {

    private AccountClient accountClient = ApiService.retrofitService();
    private MutableLiveData<AccountProperties> accountProperties = new MutableLiveData<>();

    @Inject
    public ParametersViewModel() {
//        updateAccountData();
    }

    LiveData<AccountProperties> getAccountProperties() {
        return accountProperties;
    }

    void updateAccountData() {
        Call<AccountProperties> call = accountClient.getAccountProperties();
        call.enqueue(new Callback<AccountProperties>() {
            @Override
            public void onResponse(@NotNull Call<AccountProperties> call, @NotNull Response<AccountProperties> response) {
                if (response.isSuccessful()) {
                    accountProperties.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AccountProperties> call, @NotNull Throwable t) {

            }
        });
    }
}
