package net.inqer.autosearch.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "car_models")
public class CarModel implements ListItem {

    @PrimaryKey
    private Integer id;
    private String name;
    private String slug;
    private Boolean isPopular;
    @SerializedName("popularCount")
    private Integer requestCount;

    public CarModel(String name, String slug, Boolean isPopular, Integer requestCount) {
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
        return model instanceof CarModel && this.slug.equals(((CarModel) model).slug);
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
        CarModel carMark = (CarModel) o;
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
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.slug);
        dest.writeValue(this.isPopular);
        dest.writeValue(this.requestCount);
    }

    private CarModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.slug = in.readString();
        this.isPopular = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.requestCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<CarModel> CREATOR = new Creator<CarModel>() {
        @Override
        public CarModel createFromParcel(Parcel source) {
            return new CarModel(source);
        }

        @Override
        public CarModel[] newArray(int size) {
            return new CarModel[size];
        }
    };
}
