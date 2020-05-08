package net.inqer.autosearch.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "car_marks")
public class CarMark {
    @PrimaryKey
    private String slug;
    private boolean isPopular;
    @SerializedName("popularCount")
    private Integer requestCount;
    private String name;

    public CarMark(String slug, boolean isPopular, Integer requestCount, String name) {
        this.slug = slug;
        this.isPopular = isPopular;
        this.requestCount = requestCount;
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public boolean isPopular() {
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
        CarMark carMark = (CarMark) o;
        return isPopular == carMark.isPopular &&
                Objects.equals(slug, carMark.slug) &&
                Objects.equals(requestCount, carMark.requestCount) &&
                Objects.equals(name, carMark.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, isPopular, requestCount, name);
    }
}
