package com.magdesiz.recursoshumanos.dto;

public class UsuarioDto {

    private String firstName;
    private String email;
    private String role;

    public UsuarioDto(String firstName, String email, String role) {
        this.firstName = firstName;
        this.email = email;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
