package net.inqer.autosearch.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import net.inqer.autosearch.R;

import org.jetbrains.annotations.NotNull;

public class AuthParametersProvider {
    private static final String TAG = "EncryptedSettings";

    //    private Context context;
    private SharedPreferences sharedPreferences;

    public AuthParametersProvider(@NotNull Context context) {
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
