package net.inqer.autosearch;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.ui.launcher.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<LoggedInUser>> cachedUser = new MediatorLiveData<>();

    @Inject
    SessionManager() {
    }

    public void authenticateWithCredentials(final LiveData<AuthResource<LoggedInUser>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading(null));
            cachedUser.addSource(source, new Observer<AuthResource<LoggedInUser>>() {
                @Override
                public void onChanged(AuthResource<LoggedInUser> authResource) {
                    cachedUser.setValue(authResource);
                    cachedUser.removeSource(source);
                }
            });
        }
    }



    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.logout());
    }

    public LiveData<AuthResource<LoggedInUser>> getAuthUser() {
        return cachedUser;
    }
}
