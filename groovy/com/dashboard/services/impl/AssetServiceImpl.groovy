package com.dashboard.services.impl

import com.dashboard.model.Asset
import com.dashboard.model.User
import com.dashboard.repository.AssetRepository
import com.dashboard.services.AmazonService
import com.dashboard.services.AssetService
import com.dashboard.util.AssetType
import com.github.slugify.Slugify
import org.apache.tika.Tika
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service("assetService")
class AssetServiceImpl implements AssetService {
    @Autowired
    AssetRepository assetRepository
    @Autowired
    AmazonService amazonService
    @Value('${amazonProperties.bucketName}')
    String bucketName
    static final String AWS_URL = 'https://s3.amazonaws.com/'
    Slugify slugify = new Slugify()

    @Override
    Asset createAsset(MultipartFile uploadingFiles, String description, User user) {
        AssetType type = AssetType.TEXT
        String keyName = ''
        String url = ''
        if (uploadingFiles) {
            Tika tika = new Tika()
            type = AssetType.extractFileType(tika.detect(uploadingFiles.getBytes()), description)
            keyName = user.uuid + "/" + URLEncoder.encode(slugify.slugify(uploadingFiles.originalFilename), 'UTF-8')
            url = AWS_URL + bucketName + "/" + keyName
            amazonService.uploadFile(keyName, uploadingFiles)
        }
        Asset asset = new Asset(description: description, url: url, keyName: keyName, fileType: type, createdBy: user.uuid)
        assetRepository.save(asset)
        return asset
    }

    @Override
    Page<Asset> fetchAssets(Pageable pageable) {
        return assetRepository.findAll(pageable)
    }
    Asset findByUrl(String url){
        return assetRepository.findByUrl(url);
    }

    Asset save(Asset asset){
        return assetRepository.save(asset);
    }

}
