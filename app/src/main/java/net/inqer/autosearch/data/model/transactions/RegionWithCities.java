package net.inqer.autosearch.data.model.transactions;

import androidx.room.Embedded;
import androidx.room.Relation;

import net.inqer.autosearch.data.model.City;
import net.inqer.autosearch.data.model.Region;

import java.util.List;

public class RegionWithCities {
    @Embedded
    public Region region;
    @Relation(
            parentColumn = "id",
            entityColumn = "regionId"
    )
    public List<City> cities;
}
