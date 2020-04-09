package net.inqer.autosearch.data.model;

public class RegisterCredentials {

    private String username;
    private String email;
    private String password;
    private String password2;


    public RegisterCredentials(String username, String email, String password, String password2) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.password2 = password2;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword2() {
        return password2;
    }
}
