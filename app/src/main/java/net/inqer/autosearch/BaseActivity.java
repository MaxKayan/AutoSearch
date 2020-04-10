package net.inqer.autosearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import net.inqer.autosearch.ui.login.LoginActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 *  Base activity which all other activities extend.
 *  Provides basic auth "response" listener.
 */
public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers() {
        sessionManager.getAuthUser().observe(this, authResource -> {
            if (authResource != null) {
                switch (authResource.status) {
                    case LOADING:
                    case NOT_AUTHENTICATED: {
                        navLoginScreen();
                        break;
                    }
                    case AUTHENTICATED: {
                        Log.d(TAG, "subscribeObservers: LOGIN SUCCESS: " + authResource.data.getEmail());
                        break;
                    }
                    case ERROR: {
                        Log.e(TAG, "onChanged: " + authResource.message);
                        break;
                    }
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
