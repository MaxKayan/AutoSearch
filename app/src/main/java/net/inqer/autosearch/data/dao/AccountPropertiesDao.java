package net.inqer.autosearch.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import net.inqer.autosearch.data.model.AccountProperties;

import java.util.List;

@Dao
public interface AccountPropertiesDao {

    @Query("SELECT * FROM account_table")
    List<AccountProperties> getAll();

    @Query("SELECT * FROM account_table WHERE id LIKE :id LIMIT 1")
    AccountProperties findById(int id);

    @Query("SELECT * FROM account_table WHERE email LIKE :email LIMIT 1")
    AccountProperties findByEmail(String email);

    @Insert
    void insert(AccountProperties properties);

    @Delete
    void delete(AccountProperties properties);

}
