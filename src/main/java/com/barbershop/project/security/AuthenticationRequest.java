package com.barbershop.project.security;

//Passata nel body dell'api /login
public class AuthenticationRequest {

    public String username;
    public String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
