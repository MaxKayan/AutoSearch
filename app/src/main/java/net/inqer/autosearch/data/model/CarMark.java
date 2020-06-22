package net.inqer.autosearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "car_marks")
public class CarMark implements ListItem, Parcelable {

    private String name;
    private String slug;
    private Boolean isPopular;
    @SerializedName("popularCount")
    private Integer requestCount;

    public CarMark(String name, String slug, Boolean isPopular, Integer requestCount) {
        this.name = name;
        this.slug = slug;
        this.isPopular = isPopular;
        this.requestCount = requestCount;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }


    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof CarMark && this.slug.equals(((CarMark) model).slug);
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        return this.equals(model);
    }

    public Boolean isPopular() {
        return isPopular;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarMark carMark = (CarMark) o;
        return name.equals(carMark.name) &&
                slug.equals(carMark.slug) &&
                isPopular.equals(carMark.isPopular) &&
                requestCount.equals(carMark.requestCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, slug, isPopular, requestCount);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.slug);
        dest.writeValue(this.isPopular);
        dest.writeValue(this.requestCount);
    }

    private CarMark(Parcel in) {
        this.name = in.readString();
        this.slug = in.readString();
        this.isPopular = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.requestCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<CarMark> CREATOR = new Creator<CarMark>() {
        @Override
        public CarMark createFromParcel(Parcel source) {
            return new CarMark(source);
        }

        @Override
        public CarMark[] newArray(int size) {
            return new CarMark[size];
        }
    };
}
