package net.inqer.autosearch.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "cities")
public class City implements ListItem {
    private String name;
    private String region_slug;
    private String slug;

    private String avito;
    private String autoru;
    private String drom;
    private String youla;

    @SerializedName("popularCount")
    private Integer call_count;

    private Boolean isPopular;
    private String region;

    public City(String name, String region_slug, String slug, String avito, String autoru, String drom, String youla, Integer call_count, Boolean isPopular, String region) {
        this.name = name;
        this.region_slug = region_slug;
        this.slug = slug;
        this.avito = avito;
        this.autoru = autoru;
        this.drom = drom;
        this.youla = youla;
        this.call_count = call_count;
        this.isPopular = isPopular;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public String getRegion_slug() {
        return region_slug;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        return model instanceof City && this.slug.equals(((City) model).slug);
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

    public Integer getCall_count() {
        return call_count;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public String getRegion() {
        return region;
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
        return Objects.equals(name, city.name) &&
                Objects.equals(region_slug, city.region_slug) &&
                Objects.equals(slug, city.slug) &&
                Objects.equals(avito, city.avito) &&
                Objects.equals(autoru, city.autoru) &&
                Objects.equals(drom, city.drom) &&
                Objects.equals(youla, city.youla) &&
                Objects.equals(call_count, city.call_count) &&
                Objects.equals(isPopular, city.isPopular) &&
                Objects.equals(region, city.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, region_slug, slug, avito, autoru, drom, youla, call_count, isPopular, region);
    }
}
