package net.inqer.autosearch.data.source.testing;

import java.util.HashMap;
import java.util.Map;

public class Query<T> {
    private final DataSource<T> dataSource;

    public Query(final DataSource<T> dataSource) {
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

//    public Flowable<List<T>> findAll() {
//        return dataSource.getAll(this);
//    }

//    public Flowable<T> findFirst() {
//        return dataSource.getAll(this)
//                .filter(list -> !list.isEmpty())
//                .map(list -> list.get(0));
//    }

}
