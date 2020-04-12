package net.inqer.autosearch.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import net.inqer.autosearch.util.Util;

import java.util.Date;

@Entity(tableName = "filters")
public class Filter {
    private static final String TAG = "Filter";

    @PrimaryKey
    private Integer id;
    private String slug;
    private Integer quantity;

    @SerializedName("created_at")
    private Date created_at;
    private String cities;

    @SerializedName("carname_mark")
    private String carMark;
    @SerializedName("carname_model")
    private String carModel;

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

    public Filter(Integer id,
                  String slug,
                  Integer quantity,
                  Date created_at,
                  String cities,
                  String carMark,
                  String carModel,
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
        this.slug = slug;
        this.quantity = quantity;
        this.created_at = created_at;
        this.cities = cities;
        this.carMark = carMark;
        this.carModel = carModel;
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


    public Integer getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getCities() {
        return cities;
    }

    public String getCarMark() {
        return carMark;
    }

    public String getCarModel() {
        return carModel;
    }

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
    public boolean equals(Object obj) {
        if (obj instanceof Filter) {
            Filter secFilter = (Filter) obj;

            return
//                        this.id.equals(secondFilter.id) &&
                    this.slug.equals(secFilter.slug) &&
                            this.quantity.equals(secFilter.quantity) &&
                            this.created_at.equals(secFilter.created_at) &&
                            this.cities.equals(secFilter.cities) &&
                            this.carMark.equals(secFilter.carMark) &&
                            this.carModel.equals(secFilter.carModel) &&
                            this.hull.equals(secFilter.hull) &&
                            this.fuel.equals(secFilter.fuel) &&
                            this.transmission.equals(secFilter.transmission) &&
                            this.radius.equals(secFilter.radius) &&
                            Util.equals(this.priceMinimum, secFilter.priceMinimum) &&
                            Util.equals(this.priceMaximum, secFilter.priceMaximum) &&
                            Util.equals(this.manufactureYearMin, secFilter.manufactureYearMin) &&
                            Util.equals(this.manufactureYearMax, secFilter.manufactureYearMax) &&
                            this.engineDisplacementMin.equals(secFilter.engineDisplacementMin) &&
                            this.engineDisplacementMax.equals(secFilter.engineDisplacementMax) &&
                            this.refreshCount.equals(secFilter.refreshCount);
        }
        return false;
    }
}
