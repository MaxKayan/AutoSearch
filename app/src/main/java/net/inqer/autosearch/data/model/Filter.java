package net.inqer.autosearch.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Filter {

    @PrimaryKey
    private Integer id;
    private String slug;
    private String fid;
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

    @SerializedName("count")
    private Integer refreshCount;

    public Filter(Integer id,
                  String slug,
                  String fid,
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
        this.fid = fid;
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

    public String getFid() {
        return fid;
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
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Filter) {
            Filter secondFilter = (Filter) obj;

            return this.id == secondFilter.id &&
                    this.slug == secondFilter.slug &&
                    this.fid == secondFilter.fid &&
                    this.quantity == secondFilter.quantity &&
                    this.created_at == secondFilter.created_at &&
                    this.cities == secondFilter.cities &&
                    this.carMark == secondFilter.carMark &&
                    this.carModel == secondFilter.carModel &&
                    this.hull == secondFilter.hull &&
                    this.fuel == secondFilter.fuel &&
                    this.transmission == secondFilter.transmission &&
                    this.radius == secondFilter.radius &&
                    this.priceMinimum == secondFilter.priceMinimum &&
                    this.priceMaximum == secondFilter.priceMaximum &&
                    this.manufactureYearMin == secondFilter.manufactureYearMin &&
                    this.manufactureYearMax == secondFilter.manufactureYearMax &&
                    this.engineDisplacementMin == secondFilter.engineDisplacementMin &&
                    this.engineDisplacementMax == secondFilter.engineDisplacementMax &&
                    this.refreshCount == secondFilter.refreshCount;
        }
        return false;
    }
}
