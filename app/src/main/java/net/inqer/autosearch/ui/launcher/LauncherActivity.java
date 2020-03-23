package net.inqer.autosearch.ui.launcher;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.inqer.autosearch.MainActivity;
import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.model.api.AuthCheckResponse;
import net.inqer.autosearch.data.preferences.AuthParametersProvider;
import net.inqer.autosearch.data.service.AccountClient;
import net.inqer.autosearch.ui.login.LoginActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

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

//            Call<AccountProperties> call = accountClient.getAccountProperties("Token "+token);
//            call.enqueue(new Callback<AccountProperties>() {
//                @Override
//                public void onResponse(@NotNull Call<AccountProperties> call, @NotNull Response<AccountProperties> response) {
//                    Log.d(TAG, "onResponse: Launcher API call response code is - "+response.code());
//
//                    if (response.isSuccessful()) {
//                        AccountProperties accountProperties = response.body();
//                        Log.d(TAG, "onResponse: " + (accountProperties != null ? accountProperties.toString() : null));
//                        selectMainActivity();
//                    } else {
//                        Log.w(TAG, "onResponse: response wasn't successful");
//                        selectLoginActivity();
//                    }
//                }

            Call<AuthCheckResponse> call = accountClient.checkAuthentication("Token "+token);
            call.enqueue(new Callback<AuthCheckResponse>() {
                @Override
                public void onResponse(@NotNull Call<AuthCheckResponse> call, @NotNull Response<AuthCheckResponse> response) {
                    if (response.isSuccessful()) {
                        AuthCheckResponse result = response.body();
                        if (result != null && result.isSuccessful()) {
                            Log.d(TAG, "onResponse: "+result.getMessage());
                            selectMainActivity();
                        }

                    } else {
                        Log.d(TAG, "onResponse: not successful, code is - "+response.code());
                        selectLoginActivity();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AuthCheckResponse> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: Failed to enqueue API call!", t);
                    new AlertDialog.Builder(LauncherActivity.this)
                            .setTitle(R.string.launcher_fail_alert_title)
                            .setMessage(R.string.launcher_fail_alert_message)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LauncherActivity.this.recreate();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
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
        Toast.makeText(this, "Saved authentication is valid", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
