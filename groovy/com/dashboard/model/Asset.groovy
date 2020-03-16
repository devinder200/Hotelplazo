package com.dashboard.model

import com.dashboard.util.AssetType

import javax.validation.constraints.NotEmpty

class Asset extends BaseEntity {
    AssetType fileType
    @NotEmpty(message = "*Please provide your Title Name")
    String keyName
    String url
    String description
    String createdBy
    List<SocialMediaStat> socialMediaStats = []

    public Asset() {

    }
}
