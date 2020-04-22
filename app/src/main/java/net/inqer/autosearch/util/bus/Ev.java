package net.inqer.autosearch.util.bus;

import androidx.annotation.Nullable;

public abstract class Ev {
    public RxBusEvent.RxEventStatus status;
    public String message;
    public Boolean showDialog;
    @Nullable
    public Throwable throwable;


    class Error {

    }
}
