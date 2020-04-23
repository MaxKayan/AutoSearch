package net.inqer.autosearch.data.source.repository;

import android.util.Log;

import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.local.dao.RegionDao;
import net.inqer.autosearch.data.source.testing.DataSource;
import net.inqer.autosearch.util.NetworkManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class RegionsRepository implements DataSource<Region> {
    private static final String TAG = "RegionsRepository";

    private final RegionDao local;
    private final MainApi remote;
    private final NetworkManager nm;

    @Inject
    public RegionsRepository(RegionDao dao, MainApi api, NetworkManager nm) {
        Log.d(TAG, "RegionsRepository: instantiated");
        this.local = dao;
        this.remote = api;
        this.nm = nm;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Flowable<List<Region>> getAll() {
        return Flowable.concatArrayEager(
                // get items from db first
                local.observeRegions(),
                Flowable.defer(() -> {
                    // get items from api if Network is Available
                    if (nm.isNetworkAvailable()) {
                        // get new items from api
                        return remote.getRegions().subscribeOn(Schedulers.io())
                                // remove old items from db
//                                .flatMap(filters -> local.clear()
                                // save new items from api to db
                                .flatMap(filters -> local.insertAllRegions(filters).toFlowable());
                    } else {
                        // or return empty
                        return Flowable.empty();
                    }
                })

        );
    }

    @Override
    public Completable save(Region instance) {
        return null;
    }

    @Override
    public Completable saveAll(List<Region> list) {
        return null;
    }

    @Override
    public Completable delete(Region instance) {
        return null;
    }

    @Override
    public Completable deleteAll(List<Region> list) {
        return null;
    }

    @Override
    public Completable clear() {
        return null;
    }

    public Single<Region> getById(String slug) {
        return local.getRegionById(slug)
                .subscribeOn(Schedulers.io());
    }
}
