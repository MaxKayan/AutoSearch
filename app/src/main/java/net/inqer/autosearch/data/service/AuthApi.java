package net.inqer.autosearch.data.service;


import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.model.api.AuthCheckResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("auth/obtain_key/")
    Flowable<LoggedInUser> login(@Body LoginCredentials credentials);

    @GET("account/")
    Flowable<AccountProperties> getAccountProperties(@Header("Authorization") String authToken);

    @GET("auth/checkme/")
    Flowable<AuthCheckResponse> checkAuthentication(@Header("Authorization") String authToken);

}
