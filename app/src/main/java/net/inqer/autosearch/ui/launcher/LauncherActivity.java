package net.inqer.autosearch.ui.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.inqer.autosearch.MainActivity;
import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.preferences.AuthParametersProvider;
import net.inqer.autosearch.data.service.AccountClient;
import net.inqer.autosearch.ui.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LauncherActivity extends AppCompatActivity {
    private static final String TAG = "LauncherActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthParametersProvider authSettings = new AuthParametersProvider(this);

        String token = authSettings.getValue(getString(R.string.saved_token_key));

        if (token != null) {
            Log.d(TAG, "onCreate: Token is not empty, checking access...");

//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.base_api_url))
                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
                    .build();

            AccountClient accountClient = retrofit.create(AccountClient.class);

            Call<AccountProperties> call = accountClient.getAccountProperties("Token "+token);
            call.enqueue(new Callback<AccountProperties>() {
                @Override
                public void onResponse(@NotNull Call<AccountProperties> call, @NotNull Response<AccountProperties> response) {
                    Log.d(TAG, "onResponse: Launcher API call response code is - "+response.code());

                    if (response.isSuccessful()) {
                        AccountProperties accountProperties = response.body();
                        Log.d(TAG, "onResponse: " + (accountProperties != null ? accountProperties.toString() : null));
                        selectMainActivity();
                    } else {
                        Log.w(TAG, "onResponse: response wasn't successful");
                        selectLoginActivity();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AccountProperties> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: Failed to enqueue API call!", t);
                }
            });

        } else {
            Log.w(TAG, "onCreate: Token is empty, creating Login Activity. -- ");
            selectLoginActivity();
        }

    }


    private void selectLoginActivity() {
        Log.d(TAG, "selectLoginActivity: Chosen Login");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void selectMainActivity() {
        Log.d(TAG, "selectMainActivity: Chosen Main");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
