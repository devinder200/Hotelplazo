package com.dashboard.services.impl

import com.dashboard.model.SocialMediaStat
import com.dashboard.repository.SocialMediaStatRepository
import com.dashboard.services.SocialMediaStatService
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service("socialMediaStatService")
class SocialMediaStatServiceImpl implements SocialMediaStatService {
    @Autowired
    SocialMediaStatRepository socialMediaStatRepository

    SocialMediaStat save(SocialMediaStat socialMediaStat) {
        socialMediaStatRepository.save(socialMediaStat)
    }
}
