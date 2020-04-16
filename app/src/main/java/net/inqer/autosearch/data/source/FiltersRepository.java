package net.inqer.autosearch.data.source;

import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;

import net.inqer.autosearch.data.model.Event;
import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.local.LocalDataSource;
import net.inqer.autosearch.data.source.testing.DataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FiltersRepository implements DataSource<Filter> {
    private static final String TAG = "FiltersRepository";

    private final LocalDataSource local;
    private final RemoteFilterDataSource remote;
    private final ConnectivityManager cm;

    private MediatorLiveData<Event<List<Filter>>> observableFilters = new MediatorLiveData<>();

    @Inject
    public FiltersRepository(LocalDataSource local, RemoteFilterDataSource remote, ConnectivityManager cm) {
        this.local = local;
        this.remote = remote;
        this.cm = cm;
//        subscribeObservers();
    }

    private boolean isNetworkAvailable() {
        boolean result = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        Log.d(TAG, "isNetworkAvailable: " + result);
        return result;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Flowable<List<Filter>> getAll() {
        return Flowable.concatArrayEager(
                // get items from db first
                local.getAll(),
                Flowable.defer(() -> {
                    // get items from api if Network is Available
                    if (isNetworkAvailable()) {
                        // get new items from api
                        return remote.getAll().subscribeOn(Schedulers.io())
                                // remove old items from db
//                                .flatMap(filters -> local.clear()
                                // save new items from api to db
                                .flatMap(filters -> local.saveAll(filters).toFlowable());
                    } else {
                        // or return empty
                        return Flowable.empty();
                    }
                })

        );
    }


//    @Override
//    public Flowable<List<Filter>> getAll() {
//        return local.getAll();
//    }

    public void refreshData() {
        Disposable rDisp = remote.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe(filters -> {

                    Disposable reload = local.clear()
                            .andThen(local.saveAll(filters))
                            .subscribe(() -> Log.i(TAG, "refreshData: completed"));

                }, throwable -> {
                    Log.e(TAG, "refreshData: error:", throwable);
                });
    }

    public Completable refreshFilters() {
        return remote.getAll()
                .flatMapCompletable(filters -> local.clear()
                        .andThen(local.saveAll(filters)));
    }

    @Override
    public Completable save(Filter instance) {
        return null;
    }

    @Override
    public Completable saveAll(List<Filter> list) {
        return null;
    }

    @Override
    public Completable delete(Filter instance) {
        return local.delete(instance);
    }

    @Override
    public Completable deleteAll(List<Filter> list) {
        return null;
    }

    @Override
    public Completable clear() {
        return local.clear();
    }
}
