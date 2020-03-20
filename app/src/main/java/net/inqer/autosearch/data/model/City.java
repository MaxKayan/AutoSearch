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
}
