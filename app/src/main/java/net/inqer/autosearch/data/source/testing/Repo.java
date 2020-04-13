package net.inqer.autosearch.data.source.testing;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public abstract class Repo<T> implements DataSource<T> {
    private static final String TAG = "Repo";

    private final DataSource<T> api;
    private final DataSource<T> db;
    private final ConnectivityManager cm;


    public Repo(@Named("apiDataSource") DataSource<T> api,
                @Named("apiDataSource") DataSource<T> db,
                ConnectivityManager cm) {
        this.api = api;
        this.db = db;
        this.cm = cm;
    }

    private boolean isNetworkAvailable() {
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<List<T>> getAll() {
        return Observable.concatArrayEager(
                // get items from db first
                db.getAll().subscribeOn(Schedulers.io()),
                // get items from api if Network is Available
                Observable.defer(() -> {
                    if (isNetworkAvailable()) {
                        // get new items from api
                        return api.getAll().subscribeOn(Schedulers.io())
                                // remove old items from db
                                .flatMap(filters -> db.clear()
                                        // and Then save new items from api to db
                                        .andThen(db.saveAll(filters)));
                    } else {
                        // or return empty
                        return Observable.empty();
                    }
                }).subscribeOn(Schedulers.io())
        );
    }

    @Override
    public Observable<List<T>> getAll(Query<T> query) {
        return null;
    }


    @Override
    public Observable<List<T>> saveAll(List<T> list) {
        if (isNetworkAvailable()) {
            // save items to api first
            return api.saveAll(list).subscribeOn(Schedulers.io())
                    // save items to db
                    .flatMap(filters -> db.saveAll(list));
        } else return Observable.error(new IllegalAccessError("No Internet connection!"));
    }


    @Override
    public Completable removeAll(List<T> list) {
        return Completable.defer(() -> {
            if (isNetworkAvailable()) {
                return api.removeAll(list).subscribeOn(Schedulers.io())
                        .andThen(db.removeAll(list));
            } else
                return Completable.error(new IllegalAccessError("No Internet connection!"));
        });
    }

    @Override
    public Completable clear() {
        return null;
    }
}
