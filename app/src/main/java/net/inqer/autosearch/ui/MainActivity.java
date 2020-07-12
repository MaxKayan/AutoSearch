package net.inqer.autosearch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.inqer.autosearch.R;
import net.inqer.autosearch.dagger.annotation.MainActivityScope;
import net.inqer.autosearch.data.source.local.AuthParametersProvider;
import net.inqer.autosearch.databinding.ActivityMainBinding;
import net.inqer.autosearch.ui.login.LoginActivity;
import net.inqer.autosearch.util.bus.RxBus;
import net.inqer.autosearch.util.bus.RxBusEvent;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@MainActivityScope
public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivity";

    ActivityMainBinding binding;

    @Inject
    AuthParametersProvider parametersProvider;

    @Inject
    RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTheme(R.style.AppTheme);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_fast_search, R.id.navigation_filters, R.id.navigation_parameters)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        subscribeObservers();
    }


    private void subscribeObservers() {
        Disposable bus = rxBus.listen(RxBusEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> {
                    switch (e.status) {
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case SUCCESS:
                            showProgressBar(false);
                        case MESSAGE:
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }, throwable -> {
                    Log.e(TAG, "subscribeObservers: Error: " + throwable.getMessage(), throwable);
                });
    }

    private void showProgressBar(boolean show) {

    }

    public void signOut() {
        Log.d(TAG, "signOut: called");
        parametersProvider.removeValue(getString(R.string.saved_token_key));
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
