package com.dashboard.model

import org.springframework.data.mongodb.core.index.Indexed

import javax.validation.constraints.NotEmpty

public class FaceBookSetting extends BaseEntity {
    @Indexed(unique = true)
    @NotEmpty(message = "*Please provide App Id")
    String appId
    String userAccessToken
    String appSecret
    String pageAccessToken
    String pageId

    FaceBookSetting() {
    }

    FaceBookSetting(String appId, String userAccessToken, String appSecret, String pageAccessToken, String pageId) {
        this.appId = appId
        this.userAccessToken = userAccessToken
        this.appSecret = appSecret
        this.pageAccessToken = pageAccessToken
        this.pageId = pageId
    }

    @Override
    public String toString() {
        return "FaceBookSetting{" +
                "appId='" + appId + '\'' +
                ", userAccessToken='" + userAccessToken + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", pageAccessToken='" + pageAccessToken + '\'' +
                ", pageId='" + pageId + '\'' +
                '}';
    }
}