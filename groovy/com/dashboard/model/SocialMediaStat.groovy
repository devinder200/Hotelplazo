package com.dashboard.model

import com.dashboard.util.SocialMediaType

class SocialMediaStat extends BaseEntity {
    SocialMediaType socialMediaType = SocialMediaType.FACEBOOK
    List<String> postIds = []
    Long commentCount
    Long likeCount
}
