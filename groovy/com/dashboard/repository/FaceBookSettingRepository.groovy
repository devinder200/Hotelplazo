package com.dashboard.repository

import com.dashboard.model.FaceBookSetting
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
public interface FaceBookSettingRepository extends MongoRepository<FaceBookSetting, String> {

    FaceBookSetting findByAppId(String appId)

    Long deleteFaceBookSettingByAppId(String appId)

    FaceBookSetting findByPageId(String pageId)

}
