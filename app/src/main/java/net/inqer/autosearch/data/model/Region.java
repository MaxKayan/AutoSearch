package net.inqer.autosearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "regions")
public class Region implements ListItem, Parcelable {

    @PrimaryKey
    private long id;
    private String name;
    private String avito;
    private String autoru;
    private String drom;
    private String youla;
    @SerializedName("popularCount")
    private int requestCount;
    private boolean isPopular;

    public Region(long id, String name, String avito, String autoru, String drom, String youla, int requestCount, boolean isPopular) {
        this.id = id;
        this.name = name;
        this.avito = avito;
        this.autoru = autoru;
        this.drom = drom;
        this.youla = youla;
        this.requestCount = requestCount;
        this.isPopular = isPopular;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
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

    public int getRequestCount() {
        return requestCount;
    }

    public boolean isPopular() {
        return isPopular;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof Region && this.id == ((Region) model).id;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        return this.equals(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return id == region.id &&
                requestCount == region.requestCount &&
                isPopular == region.isPopular &&
                Objects.equals(name, region.name) &&
                Objects.equals(avito, region.avito) &&
                Objects.equals(autoru, region.autoru) &&
                Objects.equals(drom, region.drom) &&
                Objects.equals(youla, region.youla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avito, autoru, drom, youla, requestCount, isPopular);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avito);
        dest.writeString(this.autoru);
        dest.writeString(this.drom);
        dest.writeString(this.youla);
        dest.writeInt(this.requestCount);
        dest.writeByte(this.isPopular ? (byte) 1 : (byte) 0);
    }

    protected Region(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.avito = in.readString();
        this.autoru = in.readString();
        this.drom = in.readString();
        this.youla = in.readString();
        this.requestCount = in.readInt();
        this.isPopular = in.readByte() != 0;
    }

    public static final Creator<Region> CREATOR = new Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel source) {
            return new Region(source);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };
}
