package net.inqer.autosearch.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "regions")
public class Region implements SortedListAdapter.ViewModel {

    private String name;
    @SerializedName("region_slug")
    private String regionSlug;
    private String slug;
    private String avito;
    private String autoru;
    private String drom;
    private String youla;
    @SerializedName("popularCount")
    private int requestCount;
    private boolean isPopular;
    private String region;

    public Region(String name, String regionSlug, String slug, String avito, String autoru, String drom, String youla, int requestCount, boolean isPopular, String region) {
        this.name = name;
        this.regionSlug = regionSlug;
        this.slug = slug;
        this.avito = avito;
        this.autoru = autoru;
        this.drom = drom;
        this.youla = youla;
        this.requestCount = requestCount;
        this.isPopular = isPopular;
        this.region = region;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getName() {
        return name;
    }

    public String getRegionSlug() {
        return regionSlug;
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

    public String getRegion() {
        return region;
    }


    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof Region && this.getRegionSlug().equals(((Region) model).getRegionSlug());
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        return false;
    }
}
