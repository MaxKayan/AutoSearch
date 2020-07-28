package net.inqer.autosearch.ui.launcher;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.R;
import net.inqer.autosearch.SessionManager;
import net.inqer.autosearch.data.model.User;
import net.inqer.autosearch.data.source.api.AuthApi;
import net.inqer.autosearch.data.source.local.AuthParametersProvider;
import net.inqer.autosearch.util.TokenInjectionInterceptor;

import javax.inject.Inject;

public class LauncherViewModel extends ViewModel {
    private static final String TAG = "LauncherViewModel";

    private final SessionManager sessionManager;
    private final AuthApi authApi;
    private final AuthParametersProvider parametersProvider;
    private final Application application;
    private final TokenInjectionInterceptor interceptor;

    @Inject
    public LauncherViewModel(SessionManager sessionManager,
                             AuthApi authApi,
                             AuthParametersProvider parametersProvider,
                             Application application,
                             TokenInjectionInterceptor interceptor) {

        this.sessionManager = sessionManager;
        this.authApi = authApi;
        this.parametersProvider = parametersProvider;
        this.application = application;
        this.interceptor = interceptor;
    }

    void checkAuthenticationByToken(final String token) {
        // TODO: Rework this block, seems to be more complex than needed
//        sessionManager.authenticateWithCredentials(LiveDataReactiveStreams.fromPublisher(
//                authApi.checkAuthentication("Token " + token)
//                        .onErrorReturn(throwable -> {
//                            Log.w(TAG, "checkAuthenticationByToken: Error", throwable);
//                            return new User(throwable.getMessage());
//                        })
//                        .map((Function<User, AuthResource<User>>) loggedInUser -> {
//                            if (loggedInUser.getErrorCase() != null) {
//                                return AuthResource.error(loggedInUser.getErrorCase(), null);
//                            } else return AuthResource.authenticated(loggedInUser);
//                        })
//                        .subscribeOn(Schedulers.io())
//                )
//        );
        sessionManager.authenticateWithToken(token, authApi)
                .subscribe();
    }

    String getToken() {
        return parametersProvider.getValue(application.getString(R.string.saved_token_key));
    }

    void setSessionToken(String token) {
        interceptor.setSessionToken(token);
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return sessionManager.getAuthUser();
    }


}
