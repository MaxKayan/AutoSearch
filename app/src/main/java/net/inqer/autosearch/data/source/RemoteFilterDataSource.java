package net.inqer.autosearch.data.source;

import net.inqer.autosearch.data.model.QueryFilter;
import net.inqer.autosearch.data.model.api.PageResponse;
import net.inqer.autosearch.data.source.api.MainApi;
import net.inqer.autosearch.data.source.testing.DataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class RemoteFilterDataSource implements DataSource<QueryFilter> {
    private static final String TAG = "RemoteFilterDataSource";

    private final MainApi api;

    @Inject
    public RemoteFilterDataSource(MainApi api) {
        this.api = api;
    }

    @Override
    public Flowable<List<QueryFilter>> getAll() {
        return api.getFilters()
                .map(PageResponse::getResults).toFlowable();
    }

    @Override
    public Completable save(QueryFilter instance) {
        return api.createFilter(instance);
    }

    @Override
    public Completable saveAll(List<QueryFilter> list) {
        return api.createAllFilters(list);
    }

    @Override
    public Completable delete(QueryFilter instance) {
        return api.deleteFilter(instance.getId());
    }

    @Override
    public Completable deleteAll(List<QueryFilter> list) {
        return api.deleteAllFilters();
    }

    @Override
    public Completable clear() {
        return api.clearFilters();
    }
}
