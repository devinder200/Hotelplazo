package com.dashboard.services

import com.dashboard.model.Asset
import com.dashboard.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface AssetService {
    Asset createAsset(MultipartFile uploadingFiles, String description, User user)

    Page<Asset> fetchAssets(Pageable pageable)

    Asset findByUrl(String url)

    Asset save(Asset asset)
}