package com.dashboard.repository

import com.dashboard.model.Company
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface CompanyRepository extends CrudRepository<Company, String> {
    Company findCompanyByUuid(String uuid)

    Company findCompanyByUsername(String username)

    Page<Company> findAll(Pageable pageable)
}