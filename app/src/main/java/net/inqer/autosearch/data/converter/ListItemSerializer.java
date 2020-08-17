package net.inqer.autosearch.data.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.inqer.autosearch.data.model.ListItem;

import java.lang.reflect.Type;

public class ListItemSerializer implements JsonSerializer<ListItem> {
    @Override
    public JsonElement serialize(ListItem src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getId());
    }
}
