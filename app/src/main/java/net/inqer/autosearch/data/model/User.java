package net.inqer.autosearch.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;


@Entity
public class User {

    private Integer userId;
    private String username;
    private String email;
    private String token;

    @Nullable
    private String errorCase;

    // Successful log-in
    public User(int userId, String username, String email, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    // Failed log-in
    public User(@Nullable String errorCase) {
        this.errorCase = errorCase;
    }


    public Integer getUserId() {
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

    @Nullable
    public String getErrorCase() {
        return errorCase;
    }
}
