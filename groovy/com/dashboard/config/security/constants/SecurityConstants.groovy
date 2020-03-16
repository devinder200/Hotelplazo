package com.dashboard.config.security.constants

final class SecurityConstants {
    public static final String AUTH_HEADER_NAME = "authorization"
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 30;
    public static final String SIGNING_KEY = "JWTSuperSecretKey";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private SecurityConstants() {

    }
}

