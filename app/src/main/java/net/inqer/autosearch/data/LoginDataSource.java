package net.inqer.autosearch.data;

import android.os.AsyncTask;
import android.util.Log;

import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.service.AccountClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static final String TAG = "LoginDataSource";

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                    Request originalRequest = chain.request();

                    Request newRequest = originalRequest.newBuilder()
                            .header("Interceptor-Header", "xyz")
                            .build();

                    return chain.proceed(newRequest);
                }
            }).addInterceptor(loggingInterceptor).build();


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://8fde093bb098.sn.mynetname.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
            .build();


    private AccountClient accountClient = retrofit.create(AccountClient.class);


    private Result result;


    private Callback<LoggedInUser> loginCallback = new Callback<LoggedInUser>() {
        @Override
        public void onResponse(@NotNull Call<LoggedInUser> call, @NotNull Response<LoggedInUser> response) {
            if (response.isSuccessful()) {
                Log.i(TAG, "onResponse: Successfully logged in!");
                result = new Result.Success(response.body());

            } else {
                Log.e(TAG, "onResponse: Response was not successful! Code = " + response.code());
                result = new Result.Error(new IOException("onResponse: Response was not successful! Code = "));
            }
        }

        @Override
        public void onFailure(@NotNull Call<LoggedInUser> call, @NotNull Throwable t) {
            result = new Result.Error(new IOException("onFailure: Failed to send POST request! -- "+t.getMessage()));
        }
    };


    private static class LoginAsyncTask extends AsyncTask<Void, Void, LoggedInUser> {
        private final Call<LoggedInUser> call;

        LoginAsyncTask(Call<LoggedInUser> call) {
            this.call = call;
        }

        @Override
        protected LoggedInUser doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: LoginAsyncTask executed");
            try {
                Response<LoggedInUser> response = call.execute();
                return response.body();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: Failed to execute call", e);
                return null;
            }
        }
    }


    public Result<LoggedInUser> login(String username, String password) {
        Log.d(TAG, "login: DataSource Login method called");
        result = null;

        LoginCredentials credentials = new LoginCredentials(username, password);

        Call<LoggedInUser> login_call = accountClient.login(credentials);

        LoginAsyncTask task = new LoginAsyncTask(login_call);
        task.execute();
        try {
            result = new Result.Success<LoggedInUser>(task.get());
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "login: Failed to get task data!", e);
            result = new Result.Error(e);
        }

        return result;
    }


    public void logout() {
        // TODO: revoke authentication
    }
}
