package net.inqer.autosearch.data.source.local;

import android.util.Log;

import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.testing.DataSource;

import java.util.ArrayList;
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
        // get current filters from db
        return dao.getFilters()
                .flatMapCompletable(filters -> {
                    // find difference compared to new list
                    List<Filter> diff = new ArrayList<>(filters);
                    diff.removeAll(list);

                    // if current items are not in the new list, delete them, then save passed list
                    if (!diff.isEmpty()) {
                        Log.d(TAG, "saveAll: diff not empty, count: "+diff.size());
                        return deleteAll(diff).andThen(dao.insertAllFilters(list));
                    }

                    // else, just save passed list
                    return dao.insertAllFilters(list);
                });
//         dao.insertAllFilters(list);
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
