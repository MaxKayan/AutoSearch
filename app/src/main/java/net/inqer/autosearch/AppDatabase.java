package net.inqer.autosearch;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import net.inqer.autosearch.data.dao.AccountPropertiesDao;
import net.inqer.autosearch.data.model.AccountProperties;

@Database(entities = {AccountProperties.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AccountPropertiesDao accountDao();
}
