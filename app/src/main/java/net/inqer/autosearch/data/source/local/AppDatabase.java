package net.inqer.autosearch.data.source.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import net.inqer.autosearch.data.converter.DateConverter;
import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.source.local.dao.FilterDao;


@Database(entities = {Filter.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract FilterDao filterDao();
}
