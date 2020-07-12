package net.inqer.autosearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "car_marks")
public class CarMark implements ListItem, Parcelable {

    @PrimaryKey
    private long id;
    private String name;
    private Boolean isPopular;
    @SerializedName("popularCount")
    private Integer requestCount;

    public CarMark(long id, String name, Boolean isPopular, Integer requestCount) {
        this.id = id;
        this.name = name;
        this.isPopular = isPopular;
        this.requestCount = requestCount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof CarMark && this.id == ((CarMark) model).id;
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
        return id == carMark.id &&
                Objects.equals(name, carMark.name) &&
                Objects.equals(isPopular, carMark.isPopular) &&
                Objects.equals(requestCount, carMark.requestCount);
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

    protected CarMark(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
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
