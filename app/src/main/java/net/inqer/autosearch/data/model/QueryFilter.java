package net.inqer.autosearch.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "filters")
public class QueryFilter {
    private static final String TAG = "QueryFilter";

    @PrimaryKey
    private long id;
    private Integer quantity;

    @SerializedName("created_at")
    private Date created_at;
//    private List<Region> regions;
//    private List<City> cities;

//    @SerializedName("car_marks")
//    private List<String> carMarks;
//    @SerializedName("car_models")
//    private List<String> carModels;

    private String hull;
    private String fuel;

    @SerializedName("transm")
    private String transmission;

    private Integer radius;

    @Nullable
    @SerializedName("price_from")
    private Integer priceMinimum;

    @Nullable
    @SerializedName("price_to")
    private Integer priceMaximum;

    @Nullable
    @SerializedName("year_from")
    private Integer manufactureYearMin;

    @Nullable
    @SerializedName("year_to")
    private Integer manufactureYearMax;

    @SerializedName("engine_from")
    private String engineDisplacementMin;

    @SerializedName("engine_to")
    private String engineDisplacementMax;

    @SerializedName("refresh_count")
    private Integer refreshCount;

    public QueryFilter(long id,
                       Integer quantity,
                       Date created_at,
//                       List<Region> regions,
//                       List<City> cities,
//                       List<String> carMarks,
//                       List<String> carModels,
                       String hull,
                       String fuel,
                       String transmission,
                       Integer radius,
                       @Nullable Integer priceMinimum,
                       @Nullable Integer priceMaximum,
                       @Nullable Integer manufactureYearMin,
                       @Nullable Integer manufactureYearMax,
                       String engineDisplacementMin,
                       String engineDisplacementMax,
                       Integer refreshCount) {
        this.id = id;
        this.quantity = quantity;
        this.created_at = created_at;
//        this.regions = regions;
//        this.cities = cities;
//        this.carMarks = carMarks;
//        this.carModels = carModels;
        this.hull = hull;
        this.fuel = fuel;
        this.transmission = transmission;
        this.radius = radius;
        this.priceMinimum = priceMinimum;
        this.priceMaximum = priceMaximum;
        this.manufactureYearMin = manufactureYearMin;
        this.manufactureYearMax = manufactureYearMax;
        this.engineDisplacementMin = engineDisplacementMin;
        this.engineDisplacementMax = engineDisplacementMax;
        this.refreshCount = refreshCount;
    }


    // --- GETTERS ---
    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

//    public List<Region> getRegions() {
//        return regions;
//    }
//
//    public List<City> getCities() {
//        return cities;
//    }
//
//    public List<String> getCarMarks() {
//        return carMarks;
//    }
//
//    public List<String> getCarModels() {
//        return carModels;
//    }

    public String getHull() {
        return hull;
    }

    public String getFuel() {
        return fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public Integer getRadius() {
        return radius;
    }

    @Nullable
    public Integer getPriceMinimum() {
        return priceMinimum;
    }

    @Nullable
    public Integer getPriceMaximum() {
        return priceMaximum;
    }

    @Nullable
    public Integer getManufactureYearMin() {
        return manufactureYearMin;
    }

    @Nullable
    public Integer getManufactureYearMax() {
        return manufactureYearMax;
    }

    public String getEngineDisplacementMin() {
        return engineDisplacementMin;
    }

    public String getEngineDisplacementMax() {
        return engineDisplacementMax;
    }

    public Integer getRefreshCount() {
        return refreshCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryFilter that = (QueryFilter) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(created_at, that.created_at) &&
//                Objects.equals(regions, that.regions) &&
//                Objects.equals(cities, that.cities) &&
//                Objects.equals(carMarks, that.carMarks) &&
//                Objects.equals(carModels, that.carModels) &&
                Objects.equals(hull, that.hull) &&
                Objects.equals(fuel, that.fuel) &&
                Objects.equals(transmission, that.transmission) &&
                Objects.equals(radius, that.radius) &&
                Objects.equals(priceMinimum, that.priceMinimum) &&
                Objects.equals(priceMaximum, that.priceMaximum) &&
                Objects.equals(manufactureYearMin, that.manufactureYearMin) &&
                Objects.equals(manufactureYearMax, that.manufactureYearMax) &&
                Objects.equals(engineDisplacementMin, that.engineDisplacementMin) &&
                Objects.equals(engineDisplacementMax, that.engineDisplacementMax) &&
                Objects.equals(refreshCount, that.refreshCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, created_at, hull, fuel, transmission, radius, priceMinimum, priceMaximum, manufactureYearMin, manufactureYearMax, engineDisplacementMin, engineDisplacementMax, refreshCount);
    }
}
