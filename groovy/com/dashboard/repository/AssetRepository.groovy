package com.dashboard.repository

import com.dashboard.model.Asset
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
interface AssetRepository extends MongoRepository<Asset, String> {
    Page<Asset> findAll(Pageable pageable)

    Asset findByUrl(String url)
}