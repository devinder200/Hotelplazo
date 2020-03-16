package com.dashboard.config

import com.dashboard.model.Admin
import com.dashboard.repository.UserRepository
import com.dashboard.services.UserService
import com.dashboard.util.Authority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository
    @Autowired
    UserService userService

    void run(String... strings) throws Exception {
        createBasicUser()
    }

    void createBasicUser() {
        if (!userRepository.count()) {
            if (!userRepository.findByUsername('vijay@dashboard.com')) {
                Admin user1 = new Admin(username: "vijay@dashboard.com", password: "password", confirmPassword: "password", firstName: "Vijay", lastName: "Shukla")
                user1.accountNonExpired = true
                user1.credentialsNonExpired = true
                user1.accountNonLocked = true
                user1.isEnabled = true
                user1.active = true
                user1.authorities = [Authority.ROLE_ADMIN]
                userService.save(user1)
            }
            if (!userRepository.findByUsername('system@dashboard.com')) {
                Admin user1 = new Admin(username: "system@dashboard.com", password: "password", confirmPassword: "password", firstName: "Vijay", lastName: "Shukla")
                user1.accountNonExpired = false
                user1.accountNonLocked = false
                user1.credentialsNonExpired = false
                user1.isEnabled = false
                user1.authorities = [Authority.ROLE_SYSTEM]
                userService.save(user1)
            }
        }
    }
}