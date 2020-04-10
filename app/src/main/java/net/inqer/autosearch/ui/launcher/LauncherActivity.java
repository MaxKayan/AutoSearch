package net.inqer.autosearch.ui.launcher;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveDataReactiveStreams;

import net.inqer.autosearch.BaseActivity;
import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.preferences.AuthParametersProvider;
import net.inqer.autosearch.data.service.AuthApi;
import net.inqer.autosearch.ui.MainActivity;
import net.inqer.autosearch.ui.login.LoginActivity;
import net.inqer.autosearch.util.TokenInjectionInterceptor;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LauncherActivity extends BaseActivity {
    private static final String TAG = "LauncherActivity";

    @Inject
    AuthParametersProvider authSettings;

    @Inject
    AuthApi authApi;

    @Inject
    @Named("logo")
    Drawable logo;

    @Inject
    TokenInjectionInterceptor interceptor;

    private String token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        token = authSettings.getValue(getString(R.string.saved_token_key));

        if (token != null) {
            Log.d(TAG, "onCreate: Token is not empty, checking access...");

            sessionManager.authenticateWithCredentials(LiveDataReactiveStreams.fromPublisher(
                    authApi.checkAuthentication("Token " + token)
                            .onErrorReturn(throwable -> {
                                Log.d(TAG, "apply: ");
                                return null;
                            })
                           .map((Function<LoggedInUser, AuthResource<LoggedInUser>>) loggedInUser -> {
                               Log.d(TAG, "apply: ");
                               return null;
                           })
                            .subscribeOn(Schedulers.io())
                    )
            );

//            authApi.checkAuthentication("Token " + token)
//                    .toObservable()
//                    .singleOrError()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new SingleObserver<AuthCheckResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onSuccess(AuthCheckResponse authCheckResponse) {
//                            if (authCheckResponse.isSuccessful()) {
//                                selectMainActivity();
//                            } else {
//                                selectLoginActivity();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e(TAG, "onError: checkAuthentication:", e);
//                            showErrorAlertDialog(e.getMessage());
//                        }
//                    });

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
        interceptor.setSessionToken(token);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void showErrorAlertDialog(final String errorMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(LauncherActivity.this)
                .setTitle(R.string.launcher_fail_alert_title)
                .setMessage(getString(R.string.launcher_fail_alert_message) + "\n\n" + errorMessage)
                .setPositiveButton("Retry", (dialog, which) -> LauncherActivity.this.recreate())
                .setNegativeButton("Cancel", (dialog, which) -> finish())
                .setNeutralButton("Go Offline", (dialog, which) -> selectMainActivity())
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnCancelListener(dialog -> finish());

        alertDialog.show();
    }

}
