package net.inqer.autosearch.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.inqer.autosearch.data.model.Region;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface RegionDao {
    @Query("SELECT * FROM regions")
    Flowable<List<Region>> observeRegions();

    @Query("SELECT * FROM regions")
    Single<List<Region>> getRegions();

    @Query("SELECT * FROM regions WHERE slug = :slug")
    Single<Region> getRegionById(String slug);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRegion(Region region);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllRegions(List<Region> regions);

    @Delete
    Completable deleteRegion(Region region);

    @Delete
    Completable deleteAll(List<Region> regions);

    @Query("DELETE FROM regions")
    Completable deleteAllRegions();
}
