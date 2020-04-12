package net.inqer.autosearch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Event<T> {

    @NonNull
    public final ResultStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    private Event(@NonNull ResultStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Event<T> success (@Nullable T data) {
        return new Event<>(ResultStatus.SUCCESS, data, null);
    }

    public static <T> Event<T> error(@NonNull String msg, @Nullable T data) {
        return new Event<>(ResultStatus.ERROR, data, msg);
    }

    public static <T> Event<T> loading(@Nullable T data) {
        return new Event<>(ResultStatus.LOADING, data, null);
    }

    // Request states
    public enum ResultStatus {SUCCESS, ERROR, LOADING}

}
