package net.inqer.autosearch.data.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class AccountProperties {
    private Integer id;
    private String email;
    private String username;

    private Date date_joined;
    private Date last_login;

    private Boolean is_admin;
    private Boolean is_active;
    private Boolean is_staff;
    private Boolean is_superuser;

    public AccountProperties(Integer id, String email, String username, Date date_joined, Date last_login, Boolean is_admin, Boolean is_active, Boolean is_staff, Boolean is_superuser) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.date_joined = date_joined;
        this.last_login = last_login;
        this.is_admin = is_admin;
        this.is_active = is_active;
        this.is_staff = is_staff;
        this.is_superuser = is_superuser;
    }

    @NotNull
    @Override
    public String toString() {
        return "AccountProperties{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", date_joined=" + date_joined +
                ", last_login=" + last_login +
                ", is_admin=" + is_admin +
                ", is_active=" + is_active +
                ", is_staff=" + is_staff +
                ", is_superuser=" + is_superuser +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Date getDate_joined() {
        return date_joined;
    }

    public Date getLast_login() {
        return last_login;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public Boolean getIs_staff() {
        return is_staff;
    }

    public Boolean getIs_superuser() {
        return is_superuser;
    }
}
