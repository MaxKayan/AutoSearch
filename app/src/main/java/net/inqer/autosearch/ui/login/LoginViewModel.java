package net.inqer.autosearch.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.service.AuthApi;

import javax.inject.Inject;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
//    private LoginRepository loginRepository;

//    LoginViewModel(LoginRepository loginRepository) {
//        this.loginRepository = loginRepository;
//    }
    private final AuthApi authApi;

    @Inject
    public LoginViewModel(AuthApi api) {
        this.authApi = api;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

//    public void login(String username, String password) {
//        Log.d(TAG, "login: ViewModel login method called");
//        // can be launched in a separate asynchronous job
//        Result<LoggedInUser> result = loginRepository.login(username, password);
//
//        Log.d(TAG, "login: result is - "+result.toString()+" : "+result.getClass().getName());
//        if (result instanceof Result.Success) {
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(), data.getToken())));
//        } else {
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }
//    }

    public void login(final String email, final String password) {
        authApi.login(new LoginCredentials(email, password))
                .toObservable()
                .singleElement()
                .subscribeOn(Schedulers.io())
                .subscribe(new MaybeObserver<LoggedInUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(LoggedInUser loggedInUser) {
                        loginResult.postValue(new LoginResult(new LoggedInUserView(loggedInUser.getDisplayName(), loggedInUser.getToken())));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: authApi.login request failed", e);
                        loginResult.postValue(new LoginResult(R.string.login_failed, e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    new Observer<LoggedInUser>() {
//        @Override
//        public void onSubscribe(Disposable d) {
//
//        }
//
//        @Override
//        public void onNext(LoggedInUser loggedInUser) {
//            if (loggedInUser != null) {
//                loginResult.postValue(new LoginResult(new LoggedInUserView(loggedInUser.getDisplayName(), loggedInUser.getToken())));
//            } else {
//                loginResult.postValue(new LoginResult(R.string.login_failed, "Invalid data received"));
//            }
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            Log.e(TAG, "onError: authApi.login request failed", e);
//            loginResult.postValue(new LoginResult(R.string.login_failed, e.getMessage()));
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
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
        return password != null && password.trim().length() > 5;
    }
}
