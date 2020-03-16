package com.dashboard.config.security.service

import com.dashboard.model.User
import com.dashboard.util.AuthFail
import groovy.util.logging.Slf4j
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
@Slf4j
class JsonWebTokenService implements TokenService {

    private static int tokenExpirationTime = 120

    @Value('${security.token.secret.key}')
    private String tokenKey
    @Autowired
    UserDetailsService userDetailsService
    @Autowired
    BCryptPasswordEncoder passwordEncoder

    @Override
    public String getToken(final String username, final String password) {
        if (!username || !password) {
            return null
        }
        String auth = ""
        try {
            final User user = (User) userDetailsService.loadUserByUsername(username)
            if (!user) {
                auth = AuthFail.USER_DOES_NOT_EXIST.value
            } else if (!user.active) {
                auth = AuthFail.ACCOUNT_NOT_ACTIVATED.value
            } else {
                Map<String, Object> tokenData = new HashMap<>()
                if (passwordEncoder.matches(password, user.password)) {
                    tokenData.clientType = "user"
                    tokenData.userID = user.uuid
                    tokenData.username = user.username
                    tokenData.token_create_date = LocalDateTime.now()
                    Calendar calendar = Calendar.getInstance()
                    calendar.add(Calendar.MINUTE, tokenExpirationTime)
                    tokenData.token_expiration_date = calendar.getTime()
                    JwtBuilder jwtBuilder = Jwts.builder()
                    jwtBuilder.setExpiration(calendar.getTime())
                    jwtBuilder.setClaims(tokenData)
                    auth = jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact()
                } else {
                    auth = AuthFail.USERNAME_PASSWORD_WRONG.value
                }
            }
        } catch (UsernameNotFoundException ex) {
            log.error(ex.message)
            auth = AuthFail.USERNAME_PASSWORD_WRONG.value
        } catch (Exception ex) {
            log.error(ex.message)
            auth = AuthFail.AUTHENTICATION_ERROR.value
        }
        return auth
    }

    public static void setTokenExpirationTime(final int tokenExpirationTime) {
        JsonWebTokenService.tokenExpirationTime = tokenExpirationTime
    }
}