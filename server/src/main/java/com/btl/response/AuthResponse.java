package com.btl.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String username;

    private String name;

    private String accessToken;
    private String role;

    public AuthResponse(String username, String name, String accessToken, String role) {
        this.username = username;
        this.name = name;
        this.accessToken = accessToken;
        this.role = role;
    }

}
