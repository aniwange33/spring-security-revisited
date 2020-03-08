package com.example.demo.jwt;

import lombok.Data;

@Data
public class JwtUsernamePasswordAuthenticationFilterRequest {
    String password;
    String username;
}
