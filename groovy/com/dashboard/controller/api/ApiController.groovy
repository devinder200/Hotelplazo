package com.dashboard.controller.api

import com.dashboard.command.LoginCommand
import com.dashboard.config.security.JwtTokenProvider
import com.dashboard.config.security.service.TokenService
import com.dashboard.model.User
import com.dashboard.util.JwtAuthenticationResponse
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/api")
@Slf4j
class ApiController {
    @Autowired
    TokenService tokenService
    @Autowired
    UserDetailsService userDetailsService
    @Autowired
    AuthenticationManager authenticationManager
    @Autowired
    JwtTokenProvider tokenProvider

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginCommand loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().setAuthentication(authentication)
        JwtAuthenticationResponse response = tokenProvider.generateToken(authentication)
        return new ResponseEntity<JwtAuthenticationResponse>(response, HttpStatus.OK)
    }
}

