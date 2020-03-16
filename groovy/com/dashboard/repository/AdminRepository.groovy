package com.dashboard.repository

import com.dashboard.model.Admin
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface AdminRepository extends CrudRepository<Admin, String> {
    Admin findAdminByUsername(String username)

    Page<Admin> findAll(Pageable pageable)
}