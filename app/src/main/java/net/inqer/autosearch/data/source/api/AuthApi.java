package net.inqer.autosearch.data.source.api;


import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.model.RegisterCredentials;
import net.inqer.autosearch.data.model.User;
import net.inqer.autosearch.util.Config;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface AuthApi {

    @Headers(Config.NO_AUTHENTICATION_COOKIE + ": guest")
    @POST("auth/obtain_key/")
    Single<User> login(@Body LoginCredentials credentials);

    @Headers(Config.NO_AUTHENTICATION_COOKIE + ": guest")
    @POST("register/")
    Single<User> register(@Body RegisterCredentials credentials);

    @Headers(Config.NO_AUTHENTICATION_COOKIE + ": guest")
    @GET("auth/checkme/")
    Single<User> checkAuthentication(@Header("Authorization") String authToken);

    @GET
    Call<ResponseBody> checkHost(@Url String absoluteUrl);
}
