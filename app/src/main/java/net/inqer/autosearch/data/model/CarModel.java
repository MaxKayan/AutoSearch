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
    private long id;
    private String name;
    private Boolean isPopular;
    @SerializedName("popularCount")
    private Integer requestCount;

    public CarModel(long id, String name, Boolean isPopular, Integer requestCount) {
        this.id = id;
        this.name = name;
        this.isPopular = isPopular;
        this.requestCount = requestCount;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }


    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof CarModel && this.id == ((CarModel) model).id;
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
        CarModel carModel = (CarModel) o;
        return id == carModel.id &&
                Objects.equals(name, carModel.name) &&
                Objects.equals(isPopular, carModel.isPopular) &&
                Objects.equals(requestCount, carModel.requestCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isPopular, requestCount);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeValue(this.isPopular);
        dest.writeValue(this.requestCount);
    }

    protected CarModel(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
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
