package net.inqer.autosearch.data.source.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import net.inqer.autosearch.data.converter.DateConverter;
import net.inqer.autosearch.data.model.CarMark;
import net.inqer.autosearch.data.model.CarModel;
import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.QueryFilter;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.source.local.dao.CarMarkDao;
import net.inqer.autosearch.data.source.local.dao.CarModelDao;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.local.dao.RegionDao;


@Database(entities = {QueryFilter.class, Region.class, City.class, CarMark.class, CarModel.class},
        version = 6,
        exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract FilterDao filterDao();

    public abstract RegionDao regionDao();

    public abstract CarMarkDao carMarkDao();

    public abstract CarModelDao carModelDao();
}
