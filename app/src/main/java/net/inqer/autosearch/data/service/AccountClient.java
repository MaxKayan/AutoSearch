package net.inqer.autosearch.data.service;


import android.accounts.Account;

import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.model.LoginCredentials;
import net.inqer.autosearch.data.model.api.AuthCheckResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountClient {

    @POST("auth/obtain_key/")
    Call<LoggedInUser> login(@Body LoginCredentials credentials);

    @GET("cities/{city_slug}/")
    Call<City> getCityBySlug(@Header("Authorization") String authToken, @Path("city_slug") String slug);

    @GET("account/")
    Call<AccountProperties> getAccountProperties(@Header("Authorization") String authToken);

    @GET("auth/checkme/")
    Call<AuthCheckResponse> checkAuthentication(@Header("Authorization") String authToken);

}
