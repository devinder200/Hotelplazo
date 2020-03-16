package com.dashboard.util

public class JwtAuthenticationResponse {
    String accessToken
    String tokenType = "Bearer"
    String username
    String uuid

    List<Authority> roles = new ArrayList<>()
}

