package net.inqer.autosearch.data.source.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import net.inqer.autosearch.data.converter.DateConverter;
import net.inqer.autosearch.data.model.QueryFilter;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.source.local.dao.FilterDao;
import net.inqer.autosearch.data.source.local.dao.RegionDao;


@Database(entities = {QueryFilter.class, Region.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract FilterDao filterDao();

    public abstract RegionDao regionDao();
}
