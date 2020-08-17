package net.inqer.autosearch.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.inqer.autosearch.data.model.City;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CityDao {
    @Query("SELECT * FROM cities")
    Flowable<List<City>> observeCities();

    @Query("SELECT * FROM cities")
    Single<List<City>> getCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllCities(List<City> cities);

    @Delete
    Completable deleteCity(City city);

    @Delete
    Completable deleteAll(List<City> cities);

    @Query("DELETE FROM cities")
    Completable deleteAllCities();
}
