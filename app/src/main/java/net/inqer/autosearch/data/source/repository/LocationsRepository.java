package net.inqer.autosearch.data.source.repository;

import android.util.Log;

import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.local.dao.RegionDao;
import net.inqer.autosearch.util.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LocationsRepository {
    private static final String TAG = "LocationsRepository";

    private final RegionDao local;
    private final MainApi remote;
    private final NetworkManager nm;

    @Inject
    public LocationsRepository(RegionDao dao, MainApi api, NetworkManager nm) {
        Log.d(TAG, "LocationsRepository: instantiated");
        this.local = dao;
        this.remote = api;
        this.nm = nm;
    }


    public Flowable<List<Region>> getAllRegions() {
        return local.observeRegions()
                .doOnSubscribe(subscription -> {
                    Log.d(TAG, "getAllRegions: new sub: " + subscription.toString());
                    Disposable r = refreshRegions()
                            .subscribe(() -> {
                                Log.d(TAG, "getAllRegions: refreshed");
                            }, throwable -> {
                                Log.e(TAG, "getAllRegions: Error: "+throwable.getMessage(), throwable);
                            });
                });
    }


    public Completable refreshRegions() {
        // get new data from backend
        return remote.getRegions()
                .subscribeOn(Schedulers.io())
                // get current data from database
                .flatMapCompletable(newRegions -> local.getRegions()
                        .flatMapCompletable(savedRegions -> {
//                            find difference between old list and new list
                            List<Region> diff = new ArrayList<>(savedRegions);
                            diff.removeAll(newRegions);

                            // if current items are not in the new list, delete them, then save passed list
                            if (!diff.isEmpty()) {
                                Log.d(TAG, "saveAll: diff not empty, count: " + diff.size());
                                return deleteAllRegions(diff).andThen(local.insertAllRegions(newRegions));
                            }

                            // else, just save new data
                            return local.insertAllRegions(newRegions);
                        }));
    }


    public Single<Region> getRegionById(long id) {
        return local.getRegionById(id)
                .subscribeOn(Schedulers.io());
    }

    public Single<PageResponse<City>> getCitiesPageByRegion(Region region) {
        return remote.getCitiesByRegion(region.getId())
                .subscribeOn(Schedulers.io());
    }

    public Single<PageResponse<City>> getCitiesPage(String fullUrl) {
        return remote.getCitiesPage(fullUrl);
    }


    public Completable deleteAllRegions(List<Region> regions) {
        return local.deleteAllRegions();
    }
}
