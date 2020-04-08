package net.inqer.autosearch.ui.launcher;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import net.inqer.autosearch.MainActivity;
import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.api.AuthCheckResponse;
import net.inqer.autosearch.data.preferences.AuthParametersProvider;
import net.inqer.autosearch.data.service.AuthApi;
import net.inqer.autosearch.ui.login.LoginActivity;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LauncherActivity extends DaggerAppCompatActivity {
    private static final String TAG = "LauncherActivity";

    @Inject
    AuthParametersProvider authSettings;

    @Inject
    AuthApi authApi;

    @Inject
    @Named("logo")
    Drawable logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = authSettings.getValue(getString(R.string.saved_token_key));

        if (token != null) {
            Log.d(TAG, "onCreate: Token is not empty, checking access...");

            authApi.checkAuthentication("Token " + token)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<AuthCheckResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(AuthCheckResponse authCheckResponse) {
                            if (authCheckResponse.isSuccessful()) {
                                selectMainActivity();
                            } else {
                                Log.w(TAG, "onNext: checkAuthentication: failed to authenticate");
                                selectLoginActivity();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: checkAuthentication failed", e);
                            showErrorAlertDialog(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });


//            Call<AuthCheckResponse> call = authApi.checkAuthentication("Token " + token);
//            call.enqueue(new Callback<AuthCheckResponse>() {
//                @Override
//                public void onResponse(@NotNull Call<AuthCheckResponse> call, @NotNull Response<AuthCheckResponse> response) {
//                    if (response.isSuccessful()) {
//                        AuthCheckResponse result = response.body();
//                        if (result != null && result.isSuccessful()) {
//                            Log.d(TAG, "onResponse: " + result.getMessage());
//                            selectMainActivity();
//                        }
//
//                    } else {
//                        Log.w(TAG, "onResponse: not successful, code is - " + response.code());
//                        selectLoginActivity();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NotNull Call<AuthCheckResponse> call, @NotNull Throwable t) {
//                    Log.e(TAG, "onFailure: Failed to enqueue API call!", t);
//
//                    showErrorAlertDialog();
//                }
//            });

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


    private void showErrorAlertDialog(final String errorMessage) {
        new AlertDialog.Builder(LauncherActivity.this)
                .setTitle(R.string.launcher_fail_alert_title)
                .setMessage(R.string.launcher_fail_alert_message+"\n\n"+errorMessage)
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
                .setNeutralButton("Go Offline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectMainActivity();
                    }
                })
                .show();
    }
}
