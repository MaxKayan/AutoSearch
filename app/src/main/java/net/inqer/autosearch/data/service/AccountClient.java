package net.inqer.autosearch.data.service;


import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.LoggedInUser;
import net.inqer.autosearch.data.model.LoginCredentials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountClient {

    @POST("login/")
    Call<LoggedInUser> login(@Body LoginCredentials credentials);

    @GET("cities/{city_slug}/")
    Call<City> getCityBySlug(@Header("Authorization") String authToken, @Path("city_slug") String slug);

}
