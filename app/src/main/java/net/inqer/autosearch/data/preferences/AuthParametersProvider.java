package net.inqer.autosearch.data.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import net.inqer.autosearch.R;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class AuthParametersProvider {
    private static final String TAG = "AuthParametersProvider";
    //    private Context context;
    private SharedPreferences sharedPreferences;


    @Inject
    public AuthParametersProvider(@NotNull Application context) {
        Log.i(TAG, "AuthParametersProvider: Instantiated!");
        int FILE_KEY = R.string.preference_auth_file_key;
        sharedPreferences = context.getSharedPreferences(context.getString(FILE_KEY), Context.MODE_PRIVATE);
    }

    public void saveValue(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void removeValue(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

}
