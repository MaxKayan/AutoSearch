package net.inqer.autosearch.util.bus;

import androidx.annotation.Nullable;

public class RxBusEvent {
    public final RxEventStatus status;
    public final String message;
    public final Boolean showDialog;
    @Nullable
    public Throwable throwable;

    private RxBusEvent(RxEventStatus status, String message, Boolean showDialog) {
        this.status = status;
        this.message = message;
        this.showDialog = showDialog;
    }

    private RxBusEvent(RxEventStatus status, String message, Boolean showDialog, @Nullable Throwable throwable) {
        this.status = status;
        this.message = message;
        this.showDialog = showDialog;
        this.throwable = throwable;
    }

    public static RxBusEvent progress(String message, Boolean showDialog) {
        return new RxBusEvent(RxEventStatus.PROGRESS, message, showDialog);
    }

    public static RxBusEvent error(String message, Boolean showDialog) {
        return new RxBusEvent(RxEventStatus.ERROR, message, showDialog);
    }

    public static RxBusEvent error(String message, Boolean showDialog, Throwable throwable) {
        return new RxBusEvent(RxEventStatus.ERROR, message, showDialog, throwable);
    }

    public static RxBusEvent message(String message, Boolean showDialog) {
        return new RxBusEvent(RxEventStatus.MESSAGE, message, showDialog);
    }

    public enum RxEventStatus {
        PROGRESS, ERROR, MESSAGE
    }
}
