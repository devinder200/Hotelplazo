package com.dashboard.services

import com.dashboard.model.twitter.TwitterSetting

interface TwitterSettingService {

    TwitterSetting save (TwitterSetting twitterSetting)

    TwitterSetting findById(String id)

    TwitterSetting findByUUID(String uuid)

    TwitterSetting update(String id , TwitterSetting object)

    String delete(String id)

    List<TwitterSetting> findAll()

}





