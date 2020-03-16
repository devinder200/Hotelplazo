package com.dashboard.config.security

import org.springframework.security.core.annotation.AuthenticationPrincipal

import java.lang.annotation.*

@Target([ElementType.PARAMETER, ElementType.TYPE])
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
@interface CurrentUser {
}

