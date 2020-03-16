package com.dashboard.services

import com.dashboard.command.UserCommand
import com.dashboard.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    User findByUsername(String username)

    User findById(String id)

    User findByUUID(String uuid)

    User save(User user)

    List<UserCommand> findAll()

    User update(String id, User object)

    String delete(String id)

    User confirmUser(String uuid)

    Page<User> fetchAllUsers(Pageable pageable)
}
