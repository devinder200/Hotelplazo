package com.dashboard.services.impl

import com.dashboard.model.User
import com.dashboard.model.twitter.TwitterSetting
import com.dashboard.repository.TwitterSettingRepository
import com.dashboard.services.TwitterSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("twitterSettingService")
class TwitterSettingServiceImpl implements TwitterSettingService {
    @Autowired
    TwitterSettingRepository twitterSettingRepository

    TwitterSetting save(TwitterSetting twitterSetting) {

        twitterSettingRepository.save(twitterSetting)
    }

    TwitterSetting findById(String id) {
        Optional<TwitterSetting> twitterSetting = twitterSettingRepository.findById(id)
        if (twitterSetting.isPresent()) {
            return twitterSetting.get()
        }

        return null
    }

    @Override
    TwitterSetting findByUUID(String uuid) {
        return twitterSettingRepository.findByUuid(uuid)
    }

    TwitterSetting update(final String id, final TwitterSetting twitterSetting) {
        try {
            twitterSetting.setId(id)
            twitterSettingRepository.save(twitterSetting)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return twitterSetting

    }

    String delete(String id) {
        twitterSettingRepository.deleteTwitterSettingById(id)
        return id
    }

    List<TwitterSetting> findAll() {
        return twitterSettingRepository.findAll()
    }
}
