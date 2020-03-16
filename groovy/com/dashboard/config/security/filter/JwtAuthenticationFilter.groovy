package com.dashboard.config.security.filter

import com.dashboard.config.security.JwtTokenProvider
import com.dashboard.util.ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static com.dashboard.config.security.constants.SecurityConstants.*

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService
    @Autowired
    private JwtTokenProvider tokenProvider

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiResponse apiResponse = null
        String email = ""
        try {
            String jwt = getJwtFromRequest(request)
            println("jwt $jwt")
            println("jwt $jwt")
            println("jwt $jwt")
            if (StringUtils.hasText(jwt)) {
                Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(jwt)
                email = tokenProvider.getUserIdFromJWT(jwt)
                println("email $email")
                println("email $email")
                println("email $email")
                UserDetails userDetails = userDetailsService.loadUserByUsername(email)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))
                SecurityContextHolder.getContext().setAuthentication(authentication)
            }
        } catch (SignatureException ex) {
            apiResponse = new ApiResponse(401, "Signature Not Matched", "Signature Not Matched")
            System.out.println("Invalid JWT signature")
        } catch (MalformedJwtException ex) {
            apiResponse = new ApiResponse(401, "Invalid JWT Token", "Invalid JWT Token")
            System.out.println("Invalid JWT token")
        } catch (ExpiredJwtException ex) {
            apiResponse = new ApiResponse(401, "Expired JWT Token", "Expired JWT Token")
            System.out.println("Expired JWT token")
        } catch (UnsupportedJwtException ex) {
            apiResponse = new ApiResponse(401, "Unsupported JWT Token", "Unsupported JWT Token")
            System.out.println("Unsupported JWT token")
        } catch (IllegalArgumentException ex) {
            apiResponse = new ApiResponse(401, "Invalid JWT Token", "Invalid JWT Token")
            System.out.println("JWT claims string is empty.")
        } catch (Exception ex) {
            apiResponse = new ApiResponse(401, "Exception Occurred", "Exception Occurred")
            logger.error("Could not set user authentication in security context", ex)
        }

        if (apiResponse != null) {
            response.setStatus(apiResponse.getStatus())
            response.setContentType("application/json")

            ObjectMapper mapper = new ObjectMapper()
            PrintWriter out = response.getWriter()
            out.print(mapper.writeValueAsString(apiResponse))
            out.flush()

            return
        }

        filterChain.doFilter(request, response)
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length())
        }
        return null
    }
}

