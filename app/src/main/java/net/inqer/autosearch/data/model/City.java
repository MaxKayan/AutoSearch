package net.inqer.autosearch.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "cities")
public class City implements ListItem {

    @PrimaryKey
    private long id;
    private String name;

    private String avito;
    private String autoru;
    private String drom;
    private String youla;

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
    @SerializedName("region")
    private long regionId;

    public City(long id, String name, String avito, String autoru, String drom, String youla, long regionId) {
        this.id = id;
        this.name = name;
        this.avito = avito;
        this.autoru = autoru;
        this.drom = drom;
        this.youla = youla;
        this.regionId = regionId;
    }

    protected City(Parcel in) {
        id = in.readLong();
        name = in.readString();
        avito = in.readString();
        autoru = in.readString();
        drom = in.readString();
        youla = in.readString();
        regionId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(avito);
        dest.writeString(autoru);
        dest.writeString(drom);
        dest.writeString(youla);
        dest.writeLong(regionId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getRegionId() {
        return regionId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof City && this.id == ((City) model).id;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        return this.equals(model);
    }

    public String getAvito() {
        return avito;
    }

    public String getAutoru() {
        return autoru;
    }

    public String getDrom() {
        return drom;
    }

    public String getYoula() {
        return youla;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
                regionId == city.regionId &&
                Objects.equals(name, city.name) &&
                Objects.equals(avito, city.avito) &&
                Objects.equals(autoru, city.autoru) &&
                Objects.equals(drom, city.drom) &&
                Objects.equals(youla, city.youla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avito, autoru, drom, youla, regionId);
    }

}
