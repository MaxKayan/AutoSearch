package net.inqer.autosearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "regions")
public class Region implements ListItem, Parcelable {

    private String name;

    @PrimaryKey
    @NonNull
    private String slug;

    private String avito;
    private String autoru;
    private String drom;
    private String youla;
    @SerializedName("popularCount")
    private int requestCount;
    private boolean isPopular;

    public Region(String name, String slug, String avito, String autoru, String drom, String youla, int requestCount, boolean isPopular) {
        this.name = name;
        this.slug = slug;
        this.avito = avito;
        this.autoru = autoru;
        this.drom = drom;
        this.youla = youla;
        this.requestCount = requestCount;
        this.isPopular = isPopular;
    }

    //    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
//
//    @Override
//    public boolean equals(@Nullable Object obj) {
//        return obj instanceof Region && this.getRegionSlug().equals(((Region) obj).getRegionSlug());
//    }

    @Override
    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
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


    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof Region && getSlug().equals(((Region) model).getSlug());
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        return this.equals(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region1 = (Region) o;
        return requestCount == region1.requestCount &&
                isPopular == region1.isPopular &&
                name.equals(region1.name) &&
                slug.equals(region1.slug) &&
                avito.equals(region1.avito) &&
                autoru.equals(region1.autoru) &&
                drom.equals(region1.drom) &&
                youla.equals(region1.youla);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(name, regionSlug, slug, avito, autoru, drom, youla, requestCount, isPopular, region);
        int hash = 12;
        hash = 26 * hash + getName().hashCode();
        hash = 26 * hash + getSlug().hashCode();
        hash = 26 * hash + getAvito().hashCode();
        hash = 26 * hash + getAutoru().hashCode();
        hash = 26 * hash + getDrom().hashCode();
        hash = 26 * hash + getYoula().hashCode();
        hash = 26 * hash + getRequestCount();
        hash = 26 * hash + (isPopular() ? 1 : 0);
        return hash;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.slug);
        dest.writeString(this.avito);
        dest.writeString(this.autoru);
        dest.writeString(this.drom);
        dest.writeString(this.youla);
        dest.writeInt(this.requestCount);
        dest.writeByte(this.isPopular ? (byte) 1 : (byte) 0);
    }

    protected Region(Parcel in) {
        this.name = in.readString();
        this.slug = in.readString();
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

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }
}
