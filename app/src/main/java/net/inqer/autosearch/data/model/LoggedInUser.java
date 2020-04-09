package net.inqer.autosearch.data.model;

import androidx.room.Entity;


@Entity
public class LoggedInUser {

    private String userId;
    private String username;
    private String email;
    private String token;

    public LoggedInUser(String userId, String username, String email, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.token = token;
    }


    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
