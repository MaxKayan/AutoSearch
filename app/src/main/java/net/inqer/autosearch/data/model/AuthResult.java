package net.inqer.autosearch.data.model;

import org.jetbrains.annotations.NotNull;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class AuthResult<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private AuthResult() {
    }

    @NotNull
    @Override
    public String toString() {
        if (this instanceof AuthResult.Success) {
            AuthResult.Success success = (AuthResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof AuthResult.Error) {
            AuthResult.Error error = (AuthResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends AuthResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends AuthResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}
