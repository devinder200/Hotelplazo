package com.dashboard.config.security.service

interface TokenService {
    String getToken(String username, String password)
}