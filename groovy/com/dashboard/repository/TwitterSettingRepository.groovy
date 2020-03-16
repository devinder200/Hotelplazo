package com.dashboard.repository


import com.dashboard.model.twitter.TwitterSetting
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
public interface TwitterSettingRepository extends MongoRepository<TwitterSetting , String> {

    TwitterSetting findByUuid(String uuid)

    Long deleteTwitterSettingById(String id)

    TwitterSetting findOneById(String id)
}