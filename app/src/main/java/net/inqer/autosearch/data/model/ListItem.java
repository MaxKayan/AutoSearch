package net.inqer.autosearch.data.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;

public interface ListItem extends Parcelable {
    String getName();
    String getSlug();
    <T> boolean isSameModelAs(@NonNull T model);
    <T> boolean isContentTheSameAs(@NonNull T model);
}
