package com.dashboard.model

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.userdetails.UserDetails

import javax.validation.constraints.NotEmpty

@Document(collection = "users")
@TypeAlias("company")
class Company extends User implements UserDetails {
    @NotEmpty(message = "*Please provide a company name")
    String companyName
    String address
}
