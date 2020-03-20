package net.inqer.autosearch.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;

    @SerializedName("username")
    private String displayName;

    private String email;
    private String token;

    public LoggedInUser(String userId, String displayName, String email, String token) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
