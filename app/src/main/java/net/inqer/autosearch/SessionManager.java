package net.inqer.autosearch;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.model.User;
import net.inqer.autosearch.data.source.api.AuthApi;
import net.inqer.autosearch.ui.launcher.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";

    private MutableLiveData<AuthResource<User>> cachedUser = new MutableLiveData<>();

    @Inject
    SessionManager() {
    }

//    public void authenticateWithCredentials(final LiveData<AuthResource<User>> source) {
//        if (cachedUser != null) {
//            cachedUser.setValue(AuthResource.loading(null));
//            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
//                @Override
//                public void onChanged(AuthResource<User> authResource) {
//                    cachedUser.setValue(authResource);
//                    cachedUser.removeSource(source);
//                }
//            });
//        }
//    }

    /**
     * @param token Raw user token value as string.
     * @param api   Working AuthApi instance, passed as a parameter because there is no need to store it
     *              at SessionManager for a whole App lifecycle.
     */
    @SuppressLint("CheckResult")
    public Single<User> authenticateWithToken(final String token, AuthApi api) {
        return api.checkAuthentication("Token " + token)
                .doOnError(throwable -> {
                    if (throwable.getMessage() != null) {
                        Log.w(TAG, "authenticateWithToken: " + throwable.getMessage() + throwable.getClass(), throwable);
                        cachedUser.postValue(AuthResource.error(throwable.getMessage(), null));
                    }
                })
                .doOnSuccess(user -> {
                    cachedUser.postValue(AuthResource.authenticated(user));
                })
                .subscribeOn(Schedulers.io());
    }

    public Single<User> authenticateWithCredentials(AuthApi api, final String email, final String password) {
        return api.login(new LoginCredentials(email, password))
                .doOnError(throwable -> {
                    if (throwable.getMessage() != null) {
                        Log.w(TAG, "authenticateWithCredentials: " + throwable.getMessage(), throwable);
                        cachedUser.postValue(AuthResource.error(throwable.getMessage(), null));
                    }
                })
                .onErrorResumeNext(throwable -> Single.just(new User(throwable.getMessage())))
                .doOnSuccess(user -> {
                    if (user.getErrorCase() != null) {
                        cachedUser.postValue(AuthResource.error(user.getErrorCase(), null));
                    }
                    cachedUser.postValue(AuthResource.authenticated(user));
                })
                .subscribeOn(Schedulers.io());
    }

    ;

    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.logout());
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }
}
