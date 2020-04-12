package net.inqer.autosearch.data.model;

import com.google.gson.annotations.SerializedName;

public class City {
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
}
