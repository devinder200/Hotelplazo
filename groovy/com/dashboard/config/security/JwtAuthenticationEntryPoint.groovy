package com.dashboard.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.dashboard.util.ApiResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        System.out.println("Responding with unauthorized error. Message - {}" + e.getMessage())
        ApiResponse apiResponse = new ApiResponse(401, "Unauthorised")
        apiResponse.setMessage("Unauthorised")
        OutputStream out = httpServletResponse.getOutputStream()
        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.writeValue(out, apiResponse)
        out.flush()
    }
}
