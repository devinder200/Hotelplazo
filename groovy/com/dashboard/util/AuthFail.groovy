package com.dashboard.util

enum AuthFail {
    USER_DOES_NOT_EXIST("User doesn't exist"),
    AUTHENTICATION_ERROR("Authentication Error"),
    ACCOUNT_NOT_ACTIVATED("Account has been disabled"),
    USERNAME_PASSWORD_WRONG("Either username or password is wrong"),
    AUTHENTICATION_FAILED("Authentication Failed")

    String value

    AuthFail(String value) {
        this.value = value
    }

    String toString() {
        value
    }
}