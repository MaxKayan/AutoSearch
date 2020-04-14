package net.inqer.autosearch.data.source.local;

import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.testing.DataSourceRx;
import net.inqer.autosearch.data.source.testing.Query;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class LocalDataSource implements DataSourceRx<Filter> {
    private static final String TAG = "LocalDataSource";

    private final FilterDao dao;

    @Inject
    public LocalDataSource(AppDatabase database) {
        this.dao = database.filterDao();
    }


    @Override
    public Observable<List<Filter>> getAll() {
        return dao.observeFiltersRx();
    }

    @Override
    public Observable<List<Filter>> getAll(Query<Filter> query) {
        return null;
    }

    @Override
    public Completable saveAll(List<Filter> list) {
        return dao.saveAllFilters(list);
    }

    @Override
    public Completable removeAll(List<Filter> list) {
        return dao.deleteAllFilters();
    }

    @Override
    public Completable clear() {
        return null;
    }
}
