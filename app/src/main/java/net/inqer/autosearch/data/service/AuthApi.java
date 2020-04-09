package net.inqer.autosearch.data.service;


import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.model.RegisterCredentials;
import net.inqer.autosearch.data.model.api.AuthCheckResponse;
import net.inqer.autosearch.util.Constants;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthApi {

    @Headers(Constants.NO_AUTHENTICATION_COOKIE+": guest")
    @POST("auth/obtain_key/")
    Flowable<LoggedInUser> login(@Body LoginCredentials credentials);

    @Headers(Constants.NO_AUTHENTICATION_COOKIE+": guest")
    @POST("register/")
    Call<LoggedInUser> register(@Body RegisterCredentials credentials);

    @Headers(Constants.NO_AUTHENTICATION_COOKIE+": guest")
    @GET("auth/checkme/")
    Flowable<AuthCheckResponse> checkAuthentication(@Header("Authorization") String authToken);

}
