package net.inqer.autosearch.util.bus;

import androidx.annotation.Nullable;

public class RxBusEvent {
    public final RxEventStatus status;
    public final String message;
    public final Boolean showDialog;
    @Nullable
    public Throwable throwable;
    @Nullable
    Class type;

    protected RxBusEvent(RxEventStatus status, String message, Boolean showDialog) {
        this.status = status;
        this.message = message;
        this.showDialog = showDialog;
    }

    protected RxBusEvent(RxEventStatus status, String message, Boolean showDialog, @Nullable Throwable throwable) {
        this.status = status;
        this.message = message;
        this.showDialog = showDialog;
        this.throwable = throwable;
    }

    public static RxBusEvent loading(String message, Boolean showDialog) {
        return new RxBusEvent(RxEventStatus.LOADING, message, showDialog);
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

    public static RxBusEvent success(String message, Boolean showDialog) {
        return new RxBusEvent(RxEventStatus.SUCCESS, message, showDialog);
    }

    public enum RxEventStatus {
        LOADING, ERROR, MESSAGE, SUCCESS
    }
}
