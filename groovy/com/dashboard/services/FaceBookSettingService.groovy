package com.dashboard.services

import com.dashboard.command.FaceBookComment
import com.dashboard.command.FaceBookLike
import com.dashboard.model.FaceBookSetting

interface FaceBookSettingService {

    FaceBookSetting findByAppId(String appId)

    FaceBookSetting save(FaceBookSetting faceBookSetting)

    List<FaceBookSetting> findAll()

    FaceBookSetting update(String appId, FaceBookSetting object)

    String delete(String appId)

    List<FaceBookLike> getPostLikes(String postId)

    List<FaceBookComment> getPostComments(String postId)

    FaceBookSetting getPageTokenByPageId(String pageId)

    Long likesCount(String postId)

    Long reactionsCount(String postId)

    Long commentsCount(String postId)
}
