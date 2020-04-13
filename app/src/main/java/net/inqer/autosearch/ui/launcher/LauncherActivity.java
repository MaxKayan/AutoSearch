package net.inqer.autosearch.ui.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.BaseActivity;
import net.inqer.autosearch.R;
import net.inqer.autosearch.ui.MainActivity;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import javax.inject.Inject;

public class LauncherActivity extends BaseActivity {
    private static final String TAG = "LauncherActivity";
    @Inject
    ViewModelProviderFactory providerFactory;
    private String token;
    private LauncherViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Launcher activity created");
        viewModel = new ViewModelProvider(this, providerFactory).get(LauncherViewModel.class);
        subscribeObservers();

        token = viewModel.getToken();

        if (token != null) {
            Log.d(TAG, "onCreate: Token is not empty, checking access...");

            viewModel.checkAuthenticationByToken(token);

        } else {
            Log.w(TAG, "onCreate: Token is empty, creating Login Activity. -- ");
            navLoginScreen();
        }

    }

    private void subscribeObservers() {
        viewModel.getAuthUser().observe(this, authResource -> {
            switch (authResource.status) {
                case LOADING:
                    Log.d(TAG, "subscribeObservers: loading data...");
                    break;
                case ERROR:
                    showErrorAlertDialog(authResource.message);
                    break;
                case AUTHENTICATED:
                    selectMainActivity();
                    break;
            }
        });
    }

//    private void selectLoginActivity() {
//        Log.d(TAG, "selectLoginActivity: Chosen Login");
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
//    }

    private void selectMainActivity() {
        Log.d(TAG, "selectMainActivity: Chosen Main");
        viewModel.setSessionToken(token);
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
