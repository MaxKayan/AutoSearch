package net.inqer.autosearch.data.model;

import androidx.annotation.NonNull;

public interface ListItem {
    String getName();
    String getSlug();
    <T> boolean isSameModelAs(@NonNull T model);
    <T> boolean isContentTheSameAs(@NonNull T model);
}
