package net.inqer.autosearch.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.MainActivity;
import net.inqer.autosearch.R;
import net.inqer.autosearch.data.preferences.AuthParametersProvider;
import net.inqer.autosearch.databinding.ActivityLoginBinding;
import net.inqer.autosearch.util.TokenInjectionInterceptor;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class LoginActivity extends DaggerAppCompatActivity {
    private static final String TAG = "LoginActivity";
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    AuthParametersProvider authSettings;
    @Inject
    TokenInjectionInterceptor interceptor;

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTheme(R.style.AppTheme);

        loginViewModel = new ViewModelProvider(this, providerFactory).get(LoginViewModel.class);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(binding.username.getText().toString(),
                        binding.password.getText().toString());
            }
        };

        binding.username.addTextChangedListener(afterTextChangedListener);

        binding.password.addTextChangedListener(afterTextChangedListener);
        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d(TAG, "onEditorAction: Login called");
                attemptLogin();
            }
            return false;
        });

        binding.login.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Login called by button");
            binding.loading.setVisibility(View.VISIBLE);
            attemptLogin();
        });

        subscribeObservers();
    }

    private void subscribeObservers() {
        loginViewModel.observeUser().observe(this, loggedInUser -> {
            if (loggedInUser != null) {
                Log.d(TAG, "onChanged: " + loggedInUser.getEmail());
            }
        });

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            binding.login.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                binding.username.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                binding.password.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            binding.loading.setVisibility(View.GONE);

            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError(), loginResult.getExceptionMessage());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
                proceedToMain();
            }
            setResult(Activity.RESULT_OK);

        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        authSettings.saveValue(getString(R.string.saved_token_key), model.getToken());
        interceptor.setSessionToken(model.getToken());
        Log.i(TAG, "updateUiWithUser: Token saved! -- " + model.getToken());
        Toast.makeText(getApplicationContext(), welcome + " - " + model.getToken(), Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString, String exceptionMessage) {
        Toast.makeText(getApplicationContext(), getString(errorString) + "\n" + exceptionMessage, Toast.LENGTH_SHORT).show();
    }


    private void proceedToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void attemptLogin() {
//        loginViewModel.authenticateWithCredentials(binding.username.getText().toString(), binding.password.getText().toString());
        loginViewModel.login(binding.username.getText().toString(), binding.password.getText().toString());
    }

}
