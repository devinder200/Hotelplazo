package com.dashboard.util

import org.springframework.security.core.GrantedAuthority

enum Authority implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_SYSTEM

    static List<Authority> list() {
        return [ROLE_ADMIN, ROLE_USER, ROLE_SYSTEM]
    }

    @Override
    public String getAuthority() {
        return this.name()
    }

    static Authority fetchAuthorityFromString(String value) {
        value = value.toLowerCase()
        if (value == "admin") {
            return ROLE_ADMIN
        }
        return ROLE_USER
    }
}