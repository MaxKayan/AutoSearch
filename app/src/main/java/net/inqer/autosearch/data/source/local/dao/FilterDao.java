package net.inqer.autosearch.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.inqer.autosearch.data.model.Filter;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface FilterDao {
    @Query("SELECT * FROM filters")
    LiveData<List<Filter>> observeFilters();

    @Query("SELECT * FROM filters")
    Single<List<Filter>> getFilters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllFilters(List<Filter> filters);

    @Query("DELETE FROM filters")
    Completable deleteAllFilters();
}
