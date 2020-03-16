package com.dashboard.util

enum ApiErrorCode {
    ALREADY_EXISTS("Email is already registered"),
    PASSWORD_CONFIRM_PASSWORD_NOT_MATCH("Password and Confirm Password Not Match"),
    USERNAME_NULL("Username can not be blank"),
    NAME_NULL("Name can not be blank")
    String code

    ApiErrorCode(String code) {
        this.code = code
    }

    String getCode() {
        return code
    }

}