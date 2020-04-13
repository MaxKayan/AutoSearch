package net.inqer.autosearch.data.source.testing;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DataSource<T> {

    Observable<List<T>> getAll();

    Observable<List<T>> getAll(Query<T> query);

    Observable<List<T>> saveAll(List<T> list);

    Completable removeAll(List<T> list);

    Completable clear();

}
