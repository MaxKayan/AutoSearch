package net.inqer.autosearch.ui.login;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.R;
import net.inqer.autosearch.SessionManager;
import net.inqer.autosearch.data.model.User;
import net.inqer.autosearch.data.source.api.AuthApi;
import net.inqer.autosearch.ui.launcher.AuthResource;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    private final AuthApi authApi;
    private SessionManager sessionManager;

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    @Inject
    LoginViewModel(AuthApi api, SessionManager sessionManager) {
        this.authApi = api;
        this.sessionManager = sessionManager;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    @SuppressLint("CheckResult")
    void authenticate(final String email, final String password) {
//        sessionManager.authenticateWithToken(queryUser(email, password));
        sessionManager.authenticateWithCredentials(authApi, email, password)
                .subscribe(user -> {
                    Log.d(TAG, "authenticate: " + user.toString());
                }, throwable -> {

                });
    }

    LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }

    void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!password.isEmpty() && !isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return !password.trim().isEmpty() && password.trim().length() > 5;
    }
}
