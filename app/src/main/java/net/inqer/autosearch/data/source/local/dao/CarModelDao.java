package net.inqer.autosearch.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.inqer.autosearch.data.model.CarModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CarModelDao {
    @Query("SELECT * FROM car_models")
    Flowable<List<CarModel>> observeCarModels();

    @Query("SELECT * FROM car_models")
    Single<List<CarModel>> getCarModels();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCarModel(CarModel carModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllCarModels(List<CarModel> carModels);

    @Delete
    Completable deleteCarModel(CarModel carModel);

    @Delete
    Completable deleteAll(List<CarModel> carModels);

    @Query("DELETE FROM car_models")
    Completable deleteAllCarModels();
}
