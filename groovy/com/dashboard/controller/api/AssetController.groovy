package com.dashboard.controller.api

import com.dashboard.config.security.CurrentUser
import com.dashboard.model.Asset
import com.dashboard.model.User
import com.dashboard.services.AmazonService
import com.dashboard.services.AssetService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = "/api/assets")
@Slf4j
class AssetController {

    @Autowired
    AmazonService amazonService
    @Autowired
    AssetService assetService

    @PostMapping(value = "/create")
    public HttpEntity<?> create(@CurrentUser User user, @RequestParam(name = "fileUpload", required = false) MultipartFile uploadingFiles, @RequestParam(name = 'description', required = false) String description) {
        if (!user) {
            return new ResponseEntity<Asset>(HttpStatus.UNAUTHORIZED)
        } else if (!uploadingFiles && !description) {
            return new ResponseEntity<Asset>(HttpStatus.NO_CONTENT)
        }
        Asset asset = assetService.createAsset(uploadingFiles, description, user)
        return new ResponseEntity<Asset>(asset, HttpStatus.CREATED)
    }

    @GetMapping(value = "/")
    public Page<Asset> getAllAssets(Pageable pageable) {
        return assetService.fetchAssets(pageable)
    }
}
