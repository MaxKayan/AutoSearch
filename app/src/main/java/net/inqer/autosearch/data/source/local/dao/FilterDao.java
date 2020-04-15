package net.inqer.autosearch.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.inqer.autosearch.data.model.Filter;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface FilterDao {
    @Query("SELECT * FROM filters")
    Flowable<List<Filter>> observeFilters();

    @Query("SELECT * FROM filters")
    Single<List<Filter>> getFilters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFilter(Filter filter);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllFilters(List<Filter> filters);

    @Delete
    Completable deleteFilter(Filter filter);

    @Delete
    Completable deleteAll(List<Filter> filters);

    @Query("DELETE FROM filters")
    Completable deleteAllFilters();
}
