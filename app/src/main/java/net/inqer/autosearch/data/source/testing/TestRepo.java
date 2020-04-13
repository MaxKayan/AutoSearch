package net.inqer.autosearch.data.source.testing;

import android.net.ConnectivityManager;

import net.inqer.autosearch.data.model.Filter;

public class TestRepo extends Repo<Filter> {
    public TestRepo(DataSource<Filter> api, DataSource<Filter> db, ConnectivityManager cm) {
        super(api, db, cm);
    }
}
