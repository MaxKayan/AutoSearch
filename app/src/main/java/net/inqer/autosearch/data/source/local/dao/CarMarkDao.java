package net.inqer.autosearch.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import net.inqer.autosearch.data.model.CarMark;
import net.inqer.autosearch.data.model.transactions.CarMarkWithModels;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CarMarkDao {
    @Query("SELECT * FROM car_marks")
    Flowable<List<CarMark>> observeCarMarks();

    @Query("SELECT * FROM car_marks")
    Single<List<CarMark>> getCarMarks();

    @Transaction
    @Query("SELECT * FROM car_marks WHERE id = :carMarkId")
    Flowable<CarMarkWithModels> observeCarMarkWithModels(long carMarkId);  // TODO: Might better be Single?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCarMark(CarMark carMark);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllCarMarks(List<CarMark> carMarks);

    @Delete
    Completable deleteCarMark(CarMark carMark);

    @Delete
    Completable deleteAll(List<CarMark> carMarks);

    @Query("DELETE FROM car_marks")
    Completable deleteAllCarMarks();
}
