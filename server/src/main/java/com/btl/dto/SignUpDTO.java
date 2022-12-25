package com.btl.dto;

import lombok.Data;

@Data
public class SignUpDTO {
    private String name;
    private String username;
    private String password;
    private String role;
}