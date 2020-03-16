package com.dashboard.model.twitter

import com.dashboard.model.BaseEntity
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotEmpty

public class TwitterSetting extends BaseEntity {
    @NotEmpty(message = "*Please provide api key")
    String apiKey;
    @NotEmpty(message = "*Please provide api secret key")
    String apiSecretKey;
    String accessToken;
    String accessTokenSecret;

    TwitterSetting() {
    }

    TwitterSetting(String apiKey, String apiSecretKey) {
        this.apiKey = apiKey
        this.apiSecretKey = apiSecretKey
    }

    TwitterSetting(String apiKey, String apiSecretKey, String accessToken, String accessTokenSecret) {
        this.apiKey = apiKey
        this.apiSecretKey = apiSecretKey
        this.accessToken = accessToken
        this.accessTokenSecret = accessTokenSecret
    }

    String getApiKey() {
        return apiKey
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey
    }

    String getApiSecretKey() {
        return apiSecretKey
    }

    void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey
    }

    String getAccessToken() {
        return accessToken
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken
    }

    String getAccessTokenSecret() {
        return accessTokenSecret
    }

    void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret
    }


}



