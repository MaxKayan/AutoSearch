package net.inqer.autosearch.data.source.testing;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DataSourceRx<T> {

    Observable<List<T>> getAll();

    Observable<List<T>> getAll(Query<T> query);

    Completable saveAll(List<T> list);

    Completable removeAll(List<T> list);

    Completable clear();

}
