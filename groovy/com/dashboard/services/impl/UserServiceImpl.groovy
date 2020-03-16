package com.dashboard.services.impl

import com.dashboard.command.UserCommand
import com.dashboard.model.User
import com.dashboard.repository.UserRepository
import com.dashboard.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service("userService")
class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder

    User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()))
        user.active = true
        user.isEnabled = true
        userRepository.save(user)
    }

    User confirmUser(String uuid) {
        User user = findByUUID(uuid)
        user.active = true
        user.isEnabled = true
        userRepository.save(user)
    }

    User findByUsername(String username) {
        return userRepository.findByUsername(username)
    }

    User findById(String id) {
        Optional<User> user = userRepository.findById(id)
        if (user.isPresent()) {
            return user.get()
        }
        return null
    }

    @Override
    User findByUUID(String uuid) {
        return userRepository.findByUuid(uuid)
    }

    public List<UserCommand> findAll() {
        try {
            return userRepository.findAll().collect { user ->
                new UserCommand(user)
            }
        } catch (Exception e) {
            e.printStackTrace()
            return []
        }

    }

    public User update(final String id, final User user) {
        try {
            user.setId(id)

            final User saved = userRepository.findOneById(id)

            /* if (saved != null) {
                 user.dateCreated = saved.dateCreated
                 user.lastUpdated = (String.valueOf(LocalDateTime.now()))
             } else {
                 user.setCreatedAt(String.valueOf(LocalDateTime.now()))
             }*/
            userRepository.save(user)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return user
    }

    public String delete(String id) {
        userRepository.deleteUserById(id)
        return id
    }

    @Override
    Page<User> fetchAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
    }
}
