package com.dashboard.repository

import com.dashboard.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username)

    Optional<User> findByUuid(String uuid)

    Optional<User> findOneById(String id)

    Long deleteUserById(String id)

    Page<User> findAll(Pageable pageable)
}
