package net.inqer.autosearch.data.source.repository;

import net.inqer.autosearch.data.model.CarMark;
import net.inqer.autosearch.data.model.CarModel;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.source.api.MainApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class OptionsRepository {
    private static final String TAG = "OptionsRepository";

    private final MainApi api;

    @Inject
    public OptionsRepository(MainApi api) {
        this.api = api;
    }

    public Flowable<List<CarMark>> observeAllMarks() {
        return api.getCarMarks()
                .subscribeOn(Schedulers.io())
                .map(PageResponse::getResults)
                .toFlowable();
    }

    public Flowable<List<CarModel>> observeModelsByMark(CarMark carMark) {
        return api.getCarModelsByMark(carMark.getId())
                .subscribeOn(Schedulers.io())
                .map(PageResponse::getResults)
                .toFlowable();
    }
}
