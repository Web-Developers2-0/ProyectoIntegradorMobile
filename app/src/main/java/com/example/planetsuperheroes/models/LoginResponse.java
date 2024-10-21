package com.example.planetsuperheroes.models;

public class LoginResponse {
    private String token;
    private User user; // Agrega esto

    // Getter y setter para el token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter y setter para el usuario
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
