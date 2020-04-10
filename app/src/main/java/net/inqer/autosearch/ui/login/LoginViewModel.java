package net.inqer.autosearch.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import net.inqer.autosearch.R;
import net.inqer.autosearch.SessionManager;
import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.service.AuthApi;
import net.inqer.autosearch.ui.launcher.AuthResource;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    private final AuthApi authApi;
    private SessionManager sessionManager;

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

//    private MediatorLiveData<AuthResource<LoggedInUser>> authUser = new MediatorLiveData<>();

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

    void authenticate(final String email, final String password) {
        sessionManager.authenticateWithCredentials(queryUser(email, password));
    }

    private LiveData<AuthResource<LoggedInUser>> queryUser(String email, String password) {
        return LiveDataReactiveStreams.fromPublisher(
                authApi.login(new LoginCredentials(email, password))

                        // Handle Error
                        .onErrorReturn(throwable -> {
                            Log.w(TAG, "authenticateWithCredentials: onError: ", throwable);
                            String errorMessage = throwable.getMessage();

                            if (throwable instanceof HttpException) {
                                HttpException httpException = ((HttpException) throwable);
                                Log.d(TAG, "authenticateWithCredentials: HTTP code: " + httpException.code());
                                switch (httpException.code()) {
                                    case 400: {
                                        errorMessage = "Wrong email or password. Please try again.";
                                        break;
                                    }
                                    case 500: {
                                        errorMessage = "Internal server error. Please try again.";
                                        break;
                                    }
                                }
                            }
                            return new LoggedInUser(errorMessage);
                        })

                        // Return user with error if there is one
                        .map((Function<LoggedInUser, AuthResource<LoggedInUser>>) loggedInUser -> {
                            if (loggedInUser.getErrorCase() != null) {
                                return AuthResource.error(loggedInUser.getErrorCase(), null);
                            }
                            return AuthResource.authenticated(loggedInUser);
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    LiveData<AuthResource<LoggedInUser>> observerAuthState() {
        return sessionManager.getAuthUser();
    }

//    public void login(final String email, final String password) {
//        authApi.login(new LoginCredentials(email, password))
//                .toObservable()
//                .singleElement()
//                .subscribeOn(Schedulers.io())
//                .subscribe(new MaybeObserver<LoggedInUser>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(LoggedInUser loggedInUser) {
//                        loginResult.postValue(new LoginResult(new LoggedInUserView(loggedInUser.getUsername(), loggedInUser.getToken())));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        loginResult.postValue(new LoginResult(R.string.login_failed, e.getMessage()));
//
//                        if (e instanceof HttpException) {
//                            Log.w(TAG, "onError: " + ((HttpException) e).code());
//                            ;
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

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
