package net.inqer.autosearch.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "car_models")
public class CarModel implements ListItem {

    public static final Creator<CarModel> CREATOR = new Creator<CarModel>() {
        @Override
        public CarModel createFromParcel(Parcel in) {
            return new CarModel(in);
        }

        @Override
        public CarModel[] newArray(int size) {
            return new CarModel[size];
        }
    };

    @PrimaryKey
    private long id;

    @SerializedName("mark")
    private long markId;

    private String name;
    private Boolean isPopular;
    @SerializedName("popularCount")
    private Integer requestCount;

    public CarModel(long id, long markId, String name, Boolean isPopular, Integer requestCount) {
        this.id = id;
        this.markId = markId;
        this.name = name;
        this.isPopular = isPopular;
        this.requestCount = requestCount;
    }

    protected CarModel(Parcel in) {
        id = in.readLong();
        markId = in.readLong();
        name = in.readString();
        byte tmpIsPopular = in.readByte();
        isPopular = tmpIsPopular == 0 ? null : tmpIsPopular == 1;
        if (in.readByte() == 0) {
            requestCount = null;
        } else {
            requestCount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(markId);
        dest.writeString(name);
        dest.writeByte((byte) (isPopular == null ? 0 : isPopular ? 1 : 2));
        if (requestCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(requestCount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getMarkId() {
        return markId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModel carModel = (CarModel) o;
        return id == carModel.id &&
                markId == carModel.markId &&
                Objects.equals(name, carModel.name) &&
                Objects.equals(isPopular, carModel.isPopular) &&
                Objects.equals(requestCount, carModel.requestCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, markId, name, isPopular, requestCount);
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


}
