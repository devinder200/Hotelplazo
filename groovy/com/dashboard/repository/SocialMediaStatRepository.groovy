package com.dashboard.repository

import com.dashboard.model.SocialMediaStat
import com.dashboard.util.SocialMediaType
import org.springframework.data.mongodb.repository.MongoRepository

interface SocialMediaStatRepository extends MongoRepository<SocialMediaStat,String> {

    SocialMediaStat findBySocialMediaType(SocialMediaType socialMediaType)
}