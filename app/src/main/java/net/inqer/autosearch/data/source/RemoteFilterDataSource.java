package net.inqer.autosearch.data.source;

import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.testing.DataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class RemoteFilterDataSource implements DataSource<Filter> {
    private static final String TAG = "RemoteFilterDataSource";

    private final MainApi api;

    @Inject
    public RemoteFilterDataSource(MainApi api) {
        this.api = api;
    }

    @Override
    public Flowable<List<Filter>> getAll() {
        return api.getFilters()
                .map(PageResponse::getResults);
    }

    @Override
    public Completable save(Filter instance) {
        return api.createFilter(instance);
    }

    @Override
    public Completable saveAll(List<Filter> list) {
        return api.createAllFilters(list);
    }

    @Override
    public Completable delete(Filter instance) {
        return api.deleteFilter(instance);
    }

    @Override
    public Completable deleteAll(List<Filter> list) {
        return api.deleteAllFilters(list);
    }

    @Override
    public Completable clear() {
        return api.clearFilters();
    }
}
