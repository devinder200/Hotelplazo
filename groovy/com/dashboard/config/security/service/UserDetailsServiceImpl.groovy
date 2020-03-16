package com.dashboard.config.security.service

import com.dashboard.exception.UserNotFoundException
import com.dashboard.model.User
import com.dashboard.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userDetailsService")
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<")
        User user = userRepository.findByUsername(username)
                .orElseThrow({ ->
                    new UserNotFoundException("User not found with username or email : " + username)
                })
        if (user) {
            return user
        } else {
            log.error("No User found")
            throw new UserNotFoundException("It seems you are not registered with us")
        }
    }
}

