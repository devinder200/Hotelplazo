package com.dashboard.model

import com.dashboard.util.Authority
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "users")
@TypeAlias("admin")
class Admin extends User implements UserDetails {
    List<Authority> authorities = [Authority.ROLE_ADMIN]
}
