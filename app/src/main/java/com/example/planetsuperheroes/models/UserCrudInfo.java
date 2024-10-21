package com.example.planetsuperheroes.models;

public class UserCrudInfo {
    private String first_name;        // Nombre del usuario
    private String last_name;    // Apellido del usuario
    private String email;       // Email del usuario
    private String address;     // Dirección del usuario

    // Constructor
    public UserCrudInfo(String name, String lastName, String email, String address) {
        this.first_name = name;
        this.last_name = lastName;
        this.email = email;
        this.address = address;
    }

    // Getters y Setters

    public String getName() {
        return first_name;
    }

    public void setName(String name) {
        this.first_name = name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + first_name + '\'' +
                ", lastName='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}


