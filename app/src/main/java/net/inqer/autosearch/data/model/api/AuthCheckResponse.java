package net.inqer.autosearch.data.model.api;

public class AuthCheckResponse {
    private Boolean successful;
    private String message;

    public AuthCheckResponse(Boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public Boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}
