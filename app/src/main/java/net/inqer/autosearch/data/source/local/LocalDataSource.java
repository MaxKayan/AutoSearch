package net.inqer.autosearch.data.source.local;

import net.inqer.autosearch.data.source.testing.DataSource;
import net.inqer.autosearch.data.source.testing.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class LocalDataSource implements DataSource {
    @Override
    public Observable<List> getAll() {
        return null;
    }

    @Override
    public Observable<List> getAll(Query query) {
        return null;
    }

    @Override
    public Observable<List> saveAll(List list) {
        return null;
    }

    @Override
    public Completable removeAll(List list) {
        return null;
    }

    @Override
    public Completable clear() {
        return null;
    }
}
