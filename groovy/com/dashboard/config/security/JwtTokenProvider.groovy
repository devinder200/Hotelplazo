package com.dashboard.config.security

import com.dashboard.model.User
import com.dashboard.util.Authority
import com.dashboard.util.JwtAuthenticationResponse
import groovy.util.logging.Slf4j
import io.jsonwebtoken.*
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

import static com.dashboard.config.security.constants.SecurityConstants.ACCESS_TOKEN_VALIDITY_SECONDS
import static com.dashboard.config.security.constants.SecurityConstants.SIGNING_KEY

@Component
@Slf4j
public class JwtTokenProvider implements Serializable {
    public JwtAuthenticationResponse generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal()
        List<Authority> roles = new ArrayList<>(userPrincipal.getAuthorities())
        Calendar calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, ACCESS_TOKEN_VALIDITY_SECONDS)
        String jwt = Jwts.builder()
                .setSubject(userPrincipal.username)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact()

        return new JwtAuthenticationResponse(accessToken: jwt, roles: roles, username: userPrincipal, uuid: userPrincipal.uuid)
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody()

        return claims.getSubject()
    }

    public boolean validateToken(String authToken) throws Exception {
        try {
            Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(authToken);
            return true
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature")
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token")
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token")
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token")
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.")
        }
        return false
    }
}

