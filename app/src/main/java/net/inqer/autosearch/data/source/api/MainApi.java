package net.inqer.autosearch.data.source.api;

import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.data.model.CarMark;
import net.inqer.autosearch.data.model.CarModel;
import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.EditableFilter;
import net.inqer.autosearch.data.model.QueryFilter;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.data.model.api.PageResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MainApi {

    @GET("account/")
    Call<AccountProperties> getAccountProperties();

    @GET("cities/{city_slug}/")
    Call<City> getCityBySlug(@Header("Authorization") String authToken, @Path("city_slug") String slug);


    // TODO: Move filter-related request to sub-component?
    @GET("filters/")
    Single<PageResponse<QueryFilter>> getFilters();

    @POST("filters/")
    Completable createFilter(@Body EditableFilter filter);

    @POST("filters/")
        // TODO: Implement this
    Completable createAllFilters(@Body List<QueryFilter> filters);


    @DELETE("filters/{itemId}/")
    Completable deleteFilter(@Path("itemId") int id);

    @DELETE("filters/")
        // TODO: Implement this
    Completable deleteAllFilters();

    @POST("filters/")
        // TODO: Implement this
    Completable clearFilters();

    @GET
    <T> Single<PageResponse<T>> getPage(@Url String fullUrl);

    @GET
    Single<PageResponse<Region>> getPageRegion(@Url String fullUrl);

    @GET("regions/")
    Single<List<Region>> getRegions();

    @GET("cities/")
    Single<PageResponse<City>> getCitiesByRegion(@Query("region") long regionId);

    @GET
    Single<PageResponse<City>> getCitiesPage(@Url String fullUrl);


    @GET("car_marks/")
    Single<PageResponse<CarMark>> getCarMarks();

    @GET("car_models/")
    Single<PageResponse<CarModel>> getCarModelsByMark(@Query("mark") long markId);
}
