package net.inqer.autosearch.data.source.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import net.inqer.autosearch.data.model.CarMark;
import net.inqer.autosearch.data.model.CarModel;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.local.dao.CarMarkDao;
import net.inqer.autosearch.data.source.local.dao.CarModelDao;
import net.inqer.autosearch.data.source.local.dao.RegionDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public class OptionsRepository {
    private static final String TAG = "OptionsRepository";

    private final MainApi api;
    private final CarMarkDao carMarkDao;
    private final CarModelDao carModelDao;
    private final RegionDao regionDao;

    @Inject
    public OptionsRepository(MainApi api, CarMarkDao carMarkDao, CarModelDao carModelDao,
                             RegionDao regionDao) {
        this.api = api;
        this.carMarkDao = carMarkDao;
        this.carModelDao = carModelDao;
        this.regionDao = regionDao;
    }

    public Flowable<List<CarMark>> observeAllMarks() {
        return carMarkDao.observeCarMarks()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> {
                    Log.d(TAG, "observeAllMarks: doOnSubscribe called!");
                    refreshCarMarks();
                });
    }

    private void refreshCarMarks() {
        api.getCarMarks()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(carMarkDao::insertAllCarMarks)
                .subscribe(() -> {
                    Log.d(TAG, "refreshCarMarks: Success!");
                }, throwable -> {
                    Log.e(TAG, "refreshCarMarks: Failed! " + throwable.getMessage(), throwable);
                });
    }


    public Flowable<List<CarModel>> observeModelsByMark(CarMark carMark) {
        return carMarkDao.observeCarMarkWithModels(carMark.getId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> {
                    api.getCarModelsByMark(carMark.getId())
                            .subscribeOn(Schedulers.io())
                            .flatMapCompletable(carModelDao::insertAllCarModels)
                            .subscribe(() -> {
                            }, throwable -> {
                                Log.e(TAG, "observeModelsByMark: Failed: " + throwable.getMessage(), throwable);
                            });
                })
                .map(carMarkWithModels -> carMarkWithModels.carModels);

    }


    public Flowable<List<Region>> observeRegions() {
        return regionDao.observeRegions()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> {
                    api.getRegions()
                            .subscribeOn(Schedulers.io())
                            .flatMapCompletable(regionDao::insertAllRegions)
                            .subscribe(() -> {
                            }, throwable -> {
                                Log.e(TAG, "observerRegions: ", throwable);
                            });
                });
    }
}
