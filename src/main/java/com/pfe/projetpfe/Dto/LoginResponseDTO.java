package com.pfe.projetpfe.Dto;

public class LoginResponseDTO {
    private final String jwt;

    public LoginResponseDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
