package net.inqer.autosearch.util.bus;

import androidx.annotation.Nullable;

public abstract class RxBusEventTest {

    public static class Progress {
        RxBusEvent.RxEventStatus status;
        Boolean showDialog;
        String message;

        public Progress(Boolean showDialog, String message) {
            this.showDialog = showDialog;
            this.message = message;
        }
    }


    public static class Error {
        Boolean showDialog;
        String message;
        @Nullable
        Throwable throwable;

        public Error(String message, Boolean showDialog) {
            this.showDialog = showDialog;
            this.message = message;
        }

        public Error(Boolean showDialog, String message, @Nullable Throwable throwable) {
            this.showDialog = showDialog;
            this.message = message;
            this.throwable = throwable;
        }
    }

    public static class Message {
        String message;

        public Message(String message) {
            this.message = message;
        }
    }
}
