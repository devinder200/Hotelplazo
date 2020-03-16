package com.dashboard.model

import com.dashboard.util.SocialMediaType

import javax.validation.constraints.NotEmpty

class SocialMediaSetting {
    SocialMediaType socialMediaType = SocialMediaType.FACEBOOK
    @NotEmpty(message = "*Please provide app key")
    String appKey
    @NotEmpty(message = "*Please provide app secret")
    String appSecret
}
