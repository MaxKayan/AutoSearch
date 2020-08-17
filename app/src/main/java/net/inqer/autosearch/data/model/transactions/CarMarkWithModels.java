package net.inqer.autosearch.data.model.transactions;

import androidx.room.Embedded;
import androidx.room.Relation;

import net.inqer.autosearch.data.model.CarMark;
import net.inqer.autosearch.data.model.CarModel;

import java.util.List;

public class CarMarkWithModels {
    @Embedded
    public CarMark carMark;
    @Relation(
            parentColumn = "id",
            entityColumn = "markId"
    )
    public List<CarModel> carModels;
}
