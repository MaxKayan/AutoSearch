package net.inqer.autosearch.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


public class EditableFilter {

    private String cities;

    private Region region;

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


    public EditableFilter() {

    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getHull() {
        return hull;
    }

    public void setHull(String hull) {
        this.hull = hull;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
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
        return Objects.equals(cities, that.cities) &&
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
        return Objects.hash(cities, carMark, carModel, hull, fuel, transmission, radius, priceMinimum, priceMaximum, manufactureYearMin, manufactureYearMax, engineDisplacementMin, engineDisplacementMax);
    }
}
