package net.inqer.autosearch.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
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
import retrofit2.HttpException;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";
    private final AuthApi authApi;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MediatorLiveData<LoggedInUser> authUser = new MediatorLiveData<>();

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

    public LiveData<LoggedInUser> observeUser() {
        return authUser;
    }

    public void authenticateWithCredentials(final String email, final String password) {
        final LiveData<LoggedInUser> source = LiveDataReactiveStreams.fromPublisher(
                authApi.login(new LoginCredentials(email, password))
                        .subscribeOn(Schedulers.io())
        );

        authUser.addSource(source, loggedInUser -> {
            authUser.setValue(loggedInUser);
            authUser.removeSource(source);
        });
    }

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
                        loginResult.postValue(new LoginResult(new LoggedInUserView(loggedInUser.getUsername(), loggedInUser.getToken())));
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginResult.postValue(new LoginResult(R.string.login_failed, e.getMessage()));

                        if (e instanceof HttpException) {
                            Log.w(TAG, "onError: "+((HttpException) e).code());;
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


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
