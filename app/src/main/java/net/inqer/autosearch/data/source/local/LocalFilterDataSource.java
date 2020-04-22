package net.inqer.autosearch.data.source.local;

import android.util.Log;

import net.inqer.autosearch.data.model.QueryFilter;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.testing.DataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalFilterDataSource implements DataSource<QueryFilter> {
    private static final String TAG = "LocalFilterDataSource";

    private final FilterDao dao;

    @Inject
    public LocalFilterDataSource(AppDatabase database) {
        this.dao = database.filterDao();
    }


    @Override
    public Flowable<List<QueryFilter>> getAll() {
        return dao.observeFilters();
    }

    @Override
    public Completable save(QueryFilter instance) {
        return dao.insertFilter(instance);
    }

    @Override
    public Completable saveAll(List<QueryFilter> list) {
        // get current filters from db
        return dao.getFilters()
                .flatMapCompletable(filters -> {
                    // find difference compared to new list
                    List<QueryFilter> diff = new ArrayList<>(filters);
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
    public Completable delete(QueryFilter instance) {
        return dao.deleteFilter(instance);
    }

    @Override
    public Completable deleteAll(List<QueryFilter> list) {
        return dao.deleteAll(list);
    }

    @Override
    public Completable clear() {
        return dao.deleteAllFilters();
    }
}
