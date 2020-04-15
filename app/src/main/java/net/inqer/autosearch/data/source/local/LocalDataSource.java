package net.inqer.autosearch.data.source.local;

import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.testing.DataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalDataSource implements DataSource<Filter> {
    private static final String TAG = "LocalDataSource";

    private final FilterDao dao;

    @Inject
    public LocalDataSource(AppDatabase database) {
        this.dao = database.filterDao();
    }


    @Override
    public Flowable<List<Filter>> getAll() {
        return dao.observeFilters();
    }

    @Override
    public Completable save(Filter instance) {
        return dao.insertFilter(instance);
    }

    @Override
    public Completable saveAll(List<Filter> list) {
        return dao.insertAllFilters(list);
    }

    @Override
    public Completable delete(Filter instance) {
        return dao.deleteFilter(instance);
    }

    @Override
    public Completable deleteAll(List<Filter> list) {
        return dao.deleteAll(list);
    }

    @Override
    public Completable clear() {
        return dao.deleteAllFilters();
    }
}
