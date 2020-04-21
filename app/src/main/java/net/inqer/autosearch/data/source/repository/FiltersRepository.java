package net.inqer.autosearch.data.source.repository;

import androidx.lifecycle.MediatorLiveData;

import net.inqer.autosearch.data.model.Event;
import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.RemoteFilterDataSource;
import net.inqer.autosearch.data.source.local.LocalFilterDataSource;
import net.inqer.autosearch.data.source.testing.DataSource;
import net.inqer.autosearch.util.NetworkManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class FiltersRepository implements DataSource<Filter> {
    private static final String TAG = "FiltersRepository";

    private final LocalFilterDataSource local;
    private final RemoteFilterDataSource remote;
    private final NetworkManager nm;

    private MediatorLiveData<Event<List<Filter>>> observableFilters = new MediatorLiveData<>();

    @Inject
    public FiltersRepository(LocalFilterDataSource local, RemoteFilterDataSource remote, NetworkManager nm) {
        this.local = local;
        this.remote = remote;
        this.nm = nm;
//        subscribeObservers();
    }


    @SuppressWarnings("unchecked")
    @Override
    public Flowable<List<Filter>> getAll() {
//        return Flowable.concatArrayEager(
//                // get items from db first
//                local.getAll(),
//                Flowable.defer(() -> {
//                    // get items from api if Network is Available
//                    if (nm.isNetworkAvailable()) {
//                        // get new items from api
//                        return remote.getAll().subscribeOn(Schedulers.io())
//                                // reload new items into DB
//                                .flatMap(filters -> local.saveAll(filters).toFlowable());
//                    } else {
//                        // or return empty
//                        return Flowable.empty();
//                    }
//                })
//
//        );
        return local.getAll();
    }


    /**
     * @return Get all filters from Remote API, reload them into database, return Complete if successful
     */
    public Completable refreshData() {
        return remote.getAll()
                .flatMapCompletable(local::saveAll);
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
        return remote.delete(instance)
                .andThen(local.delete(instance));
//        return local.delete(instance).startWith(remote.delete(instance));
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
