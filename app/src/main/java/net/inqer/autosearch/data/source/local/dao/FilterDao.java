package net.inqer.autosearch.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.inqer.autosearch.data.model.QueryFilter;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface FilterDao {
    @Query("SELECT * FROM QueryFilter")
    Flowable<List<QueryFilter>> observeFilters();

    @Query("SELECT * FROM QueryFilter")
    Single<List<QueryFilter>> getFilters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFilter(QueryFilter filter);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllFilters(List<QueryFilter> filters);

    @Delete
    Completable deleteFilter(QueryFilter filter);

    @Delete
    Completable deleteAll(List<QueryFilter> filters);

    @Query("DELETE FROM QueryFilter")
    Completable deleteAllFilters();
}
