package net.inqer.autosearch.data.repository;

import android.os.AsyncTask;
import android.util.Log;

import net.inqer.autosearch.data.model.AuthResult;
import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.model.User;
import net.inqer.autosearch.data.source.api.AuthApi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static final String TAG = "LoginDataSource";
    private final AuthApi authApi;

    @Inject
    public LoginDataSource(AuthApi authApi) {
        this.authApi = authApi;
    }

//    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//
//
//    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .addInterceptor(chain -> {
//                Request originalRequest = chain.request();
//                Request newRequest = originalRequest.newBuilder()
//                        .header("Interceptor-Header", "xyz")
//                        .build();
//
//                return chain.proceed(newRequest);
//            })
//            .addInterceptor(loggingInterceptor).build();
//
//
//    private Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://8fde093bb098.sn.mynetname.net/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build();
//
//
//    private AuthApi authApi = retrofit.create(AuthApi.class);


    private AuthResult result;

    @SuppressWarnings("unchecked")
    private Callback<User> loginCallback = new Callback<User>() {
        @Override
        public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
            if (response.isSuccessful()) {
                Log.i(TAG, "onResponse: Successfully logged in!");
                result = new AuthResult.Success(response.body());

            } else {
                Log.e(TAG, "onResponse: Response was not successful! Code = " + response.code());
                result = new AuthResult.Error(new IOException("onResponse: Response was not successful! Code = "));
            }
        }

        @Override
        public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
            result = new AuthResult.Error(new IOException("onFailure: Failed to send POST request! -- "+t.getMessage()));
        }
    };


    private static class LoginAsyncTask extends AsyncTask<Void, Void, User> {
        private final Call<User> call;

        LoginAsyncTask(Call<User> call) {
            this.call = call;
        }

        @Override
        protected User doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: LoginAsyncTask executed");
            try {
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    Log.w(TAG, "doInBackground: failed to obtain token");
                    return null;
                }
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: Failed to execute call", e);
                return null;
            }
        }
    }


    public AuthResult<User> login(String username, String password) {
        Log.d(TAG, "login: DataSource Login method called");
        result = null;

        LoginCredentials credentials = new LoginCredentials(username, password);

//        LoginAsyncTask task = new LoginAsyncTask(login_call);
//        task.execute();
//        try {
//            LoggedInUser receivedUser = task.get();
//            if (receivedUser != null) {
//                result = new Result.Success<>(receivedUser);
//            } else {
//                result = new Result.Error(new IOException("Failed to retrieve user data"));
//            }
//        } catch (ExecutionException | InterruptedException e) {
//            Log.e(TAG, "login: Failed to get task data!", e);
//            result = new Result.Error(e);
//        }

        return result;
    }


    public void logout() {
        // TODO: revoke authentication
    }
}
