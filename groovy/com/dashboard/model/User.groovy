package com.dashboard.model

import com.dashboard.util.Authority
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Document(collection = "users")
public abstract class User extends BaseEntity implements UserDetails {
    @Indexed(unique = true)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    String username

    String firstName
    String lastName

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    String password
    @Transient
    String confirmPassword

    Boolean accountNonExpired
    Boolean accountNonLocked
    Boolean credentialsNonExpired
    Boolean isEnabled = true

    Boolean active = true

    List<Authority> authorityList

    User() {
    }

    User(String username, String password, String firstName, String lastName) {
        this.username = username
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
    }

    User(String username, String password) {
        this.username = username
        this.password = password
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired
    }

    @Override
    public boolean isEnabled() {
        return isEnabled
    }
}