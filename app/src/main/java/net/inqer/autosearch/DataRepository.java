package net.inqer.autosearch;

import android.os.AsyncTask;
import android.util.Log;

import net.inqer.autosearch.data.model.AccountProperties;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataRepository {
    private static final String TAG = "DataRepository";

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
        try {
            GetterAsyncTask task = new GetterAsyncTask(mDatabase);
            task.execute();
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "getAllAccounts: error:", e);
            return null;
        }
    }

    public void delete(AccountProperties accountProperties) {
        mDatabase.accountDao().delete(accountProperties);
    }

    private static class GetterAsyncTask extends AsyncTask<Void, Void, List<AccountProperties>> {
        private final AppDatabase database;
        GetterAsyncTask(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected List<AccountProperties> doInBackground(Void... voids) {
            return database.accountDao().getAll();
        }
    }
}
