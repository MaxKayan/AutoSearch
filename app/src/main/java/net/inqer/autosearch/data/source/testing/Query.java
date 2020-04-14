package net.inqer.autosearch.data.source.testing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class Query<T> {
    private final DataSourceRx<T> dataSource;

    public Query(final DataSourceRx<T> dataSource) {
        this.dataSource = dataSource;
    }

    private Map<String, String> params = new HashMap<>();

    public Boolean has(String property) {
        return params.get(property) != null;
    }

    public Query<T> where(String property, String value) {
        params.get(property);
        return this;
    }

    public Observable<List<T>> findAll() {
        return dataSource.getAll(this);
    }

    public Observable<T> findFirst() {
        return dataSource.getAll(this)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0));
    }

}
