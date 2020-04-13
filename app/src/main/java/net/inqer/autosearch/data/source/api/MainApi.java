package net.inqer.autosearch.data.source.api;

import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.data.model.api.PageResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface MainApi {

    @GET("account/")
    Call<AccountProperties> getAccountProperties();

    @GET("cities/{city_slug}/")
    Call<City> getCityBySlug(@Header("Authorization") String authToken, @Path("city_slug") String slug);

    @GET("filters/")
    Call<PageResponse<Filter>> getFilters();

    @POST("filters/")
    Call<Response> createFilter(@Body Filter filter);

    @GET
    Call<PageResponse<Object>> getPage(@Url String fullUrl);
}
