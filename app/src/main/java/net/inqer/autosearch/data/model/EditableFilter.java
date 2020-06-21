package net.inqer.autosearch.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.inqer.autosearch.data.converter.CitySerializer;

import java.util.Objects;


public class EditableFilter {

    @SerializedName("cities")
    @JsonAdapter(CitySerializer.class)
    @Nullable
    private City city;

    @Nullable
    @JsonAdapter(CitySerializer.class)
    private Region region;

    @SerializedName("carname_mark")
    @Nullable
    @JsonAdapter(CitySerializer.class)
    private CarMark carMark;

    @SerializedName("carname_model")
    @Nullable
    @JsonAdapter(CitySerializer.class)
    private CarModel carModel;

    @Nullable
    private String hull;
    @Nullable
    private String fuel;

    @SerializedName("transm")
    @Nullable
    private String transmission;

    @Nullable
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


    public EditableFilter() {
    }


    @Nullable
    public Region getRegion() {
        return region;
    }

    public void setRegion(@Nullable Region region) {
        this.region = region;
    }

    @Nullable
    public City getCity() {
        return city;
    }

    public void setCity(@Nullable City city) {
        this.city = city;
    }

    @Nullable
    public CarMark getCarMark() {
        return carMark;
    }

    public void setCarMark(@Nullable CarMark carMark) {
        this.carMark = carMark;
    }

    @Nullable
    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(@Nullable CarModel carModel) {
        this.carModel = carModel;
    }

    @Nullable
    public String getHull() {
        return hull;
    }

    public void setHull(@Nullable String hull) {
        this.hull = hull;
    }

    @Nullable
    public String getFuel() {
        return fuel;
    }

    public void setFuel(@Nullable String fuel) {
        this.fuel = fuel;
    }

    @Nullable
    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(@Nullable String transmission) {
        this.transmission = transmission;
    }

    @Nullable
    public Integer getRadius() {
        return radius;
    }

    public void setRadius(@Nullable Integer radius) {
        this.radius = radius;
    }

    @Nullable
    public Integer getPriceMinimum() {
        return priceMinimum;
    }

    public void setPriceMinimum(@Nullable Integer priceMinimum) {
        this.priceMinimum = priceMinimum;
    }

    @Nullable
    public Integer getPriceMaximum() {
        return priceMaximum;
    }

    public void setPriceMaximum(@Nullable Integer priceMaximum) {
        this.priceMaximum = priceMaximum;
    }

    @Nullable
    public Integer getManufactureYearMin() {
        return manufactureYearMin;
    }

    public void setManufactureYearMin(@Nullable Integer manufactureYearMin) {
        this.manufactureYearMin = manufactureYearMin;
    }

    @Nullable
    public Integer getManufactureYearMax() {
        return manufactureYearMax;
    }

    public void setManufactureYearMax(@Nullable Integer manufactureYearMax) {
        this.manufactureYearMax = manufactureYearMax;
    }

    public String getEngineDisplacementMin() {
        return engineDisplacementMin;
    }

    public void setEngineDisplacementMin(String engineDisplacementMin) {
        this.engineDisplacementMin = engineDisplacementMin;
    }

    public String getEngineDisplacementMax() {
        return engineDisplacementMax;
    }

    public void setEngineDisplacementMax(String engineDisplacementMax) {
        this.engineDisplacementMax = engineDisplacementMax;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EditableFilter that = (EditableFilter) o;
        return Objects.equals(city, that.city) &&
                Objects.equals(region, that.region) &&
                Objects.equals(carMark, that.carMark) &&
                Objects.equals(carModel, that.carModel) &&
                Objects.equals(hull, that.hull) &&
                Objects.equals(fuel, that.fuel) &&
                Objects.equals(transmission, that.transmission) &&
                Objects.equals(radius, that.radius) &&
                Objects.equals(priceMinimum, that.priceMinimum) &&
                Objects.equals(priceMaximum, that.priceMaximum) &&
                Objects.equals(manufactureYearMin, that.manufactureYearMin) &&
                Objects.equals(manufactureYearMax, that.manufactureYearMax) &&
                Objects.equals(engineDisplacementMin, that.engineDisplacementMin) &&
                Objects.equals(engineDisplacementMax, that.engineDisplacementMax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, region, carMark, carModel, hull, fuel, transmission, radius, priceMinimum, priceMaximum, manufactureYearMin, manufactureYearMax, engineDisplacementMin, engineDisplacementMax);
    }
}
