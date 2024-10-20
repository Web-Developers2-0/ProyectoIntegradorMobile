package com.example.planetsuperheroes.models;

public class UserCrudInfo {
    private String name;        // Nombre del usuario
    private String lastName;    // Apellido del usuario
    private String email;       // Email del usuario
    private String address;     // Dirección del usuario

    // Constructor
    public UserCrudInfo(String name, String lastName, String email, String address) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}


