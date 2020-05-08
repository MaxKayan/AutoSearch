package net.inqer.autosearch.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "car_models")
public class CarModel {
    @PrimaryKey
    private String slug;
    @SerializedName("parentMark")
    private CarMark mark;
    private Boolean isPopular;
    @SerializedName("popularCount")
    private Integer requestCount;
    private String name;

    public CarModel(String slug, CarMark mark, Boolean isPopular, Integer requestCount, String name) {
        this.slug = slug;
        this.mark = mark;
        this.isPopular = isPopular;
        this.requestCount = requestCount;
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public CarMark getMark() {
        return mark;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModel carModel = (CarModel) o;
        return Objects.equals(slug, carModel.slug) &&
                Objects.equals(mark, carModel.mark) &&
                Objects.equals(isPopular, carModel.isPopular) &&
                Objects.equals(requestCount, carModel.requestCount) &&
                Objects.equals(name, carModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, mark, isPopular, requestCount, name);
    }
}
