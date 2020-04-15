package net.inqer.autosearch.data.source.testing;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface DataSource<T> {

    Flowable<List<T>> getAll();

    Completable save(T instance);

    Completable saveAll(List<T> list);

    Completable delete(T instance);

    Completable deleteAll(List<T> list);

    Completable clear();

}
