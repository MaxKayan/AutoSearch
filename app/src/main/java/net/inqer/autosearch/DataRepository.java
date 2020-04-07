package net.inqer.autosearch;

import net.inqer.autosearch.data.model.AccountProperties;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataRepository {

    private static DataRepository instance;

    private final AppDatabase mDatabase;

    @Inject
    DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

//    public static DataRepository getInstance(final AppDatabase database) {
//        if (instance == null) {
//            synchronized (DataRepository.class) { //TODO: Understand this block better
//                if (instance == null) {
//                    instance = new DataRepository(database);
//                }
//            }
//        }
//        return instance;
//    }

    public AccountProperties getAccountById(final int accountId) {
        return mDatabase.accountDao().findById(accountId);
    }

    public AccountProperties getAccountByEmail(final String accountEmail) {
        return mDatabase.accountDao().findByEmail(accountEmail);
    }

    public List<AccountProperties> getAllAccounts() {
        return mDatabase.accountDao().getAll();
    }

    public void delete(AccountProperties accountProperties) {
        mDatabase.accountDao().delete(accountProperties);
    }
}
