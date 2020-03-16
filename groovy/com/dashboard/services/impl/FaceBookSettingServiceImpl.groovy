package com.dashboard.services.impl

import com.dashboard.command.FaceBookComment
import com.dashboard.command.FaceBookCommentFrom
import com.dashboard.command.FaceBookLike
import com.dashboard.model.FaceBookSetting
import com.dashboard.repository.FaceBookSettingRepository
import com.dashboard.services.FaceBookSettingService
import com.dashboard.util.FaceBookReactionType
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service("faceBookSettingService")
class FaceBookSettingServiceImpl implements FaceBookSettingService {
    @Autowired
    FaceBookSettingRepository faceBookSettingRepository

    FaceBookSetting save(FaceBookSetting faceBookSetting) {
        faceBookSettingRepository.save(faceBookSetting)
    }

    FaceBookSetting findByAppId(String appId) {
        return faceBookSettingRepository.findByAppId(appId)
    }

    public List<FaceBookSetting> findAll() {
        try {
            return faceBookSettingRepository.findAll()
        } catch (Exception e) {
            e.printStackTrace()
            return []
        }
    }

    public FaceBookSetting update(String appId, FaceBookSetting faceBookSetting) {
        try {
            FaceBookSetting saved = faceBookSettingRepository.findByAppId(appId)
            if (saved) {
                saved.appSecret = faceBookSetting.appSecret
                saved.userAccessToken = faceBookSetting.userAccessToken
                saved.pageAccessToken = faceBookSetting.pageAccessToken
                saved.pageId = faceBookSetting.pageId
                faceBookSettingRepository.save(saved)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return faceBookSetting
    }

    public String delete(String appId) {
        faceBookSettingRepository.deleteFaceBookSettingByAppId(appId)
        return appId
    }

    public List<FaceBookLike> getPostLikes(String id) {
        def jsonSlurper = new JsonSlurper()
        def ids = id.split('_')
        String result = ""

        String page_access_token = ""
        FaceBookSetting facebookObj = getPageTokenByPageId(ids[0])
        List<FaceBookLike> likes = []
        if (facebookObj) {
            page_access_token = facebookObj.getPageAccessToken()
//            final String uri = "https://graph.facebook.com/" + id + "/likes?access_token=" + page_access_token;
            final String uri = "https://graph.facebook.com/" + id + "/reactions?access_token=" + page_access_token;

            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(uri, String.class);
            def object = jsonSlurper.parseText(result)
            def data = object.data
            data.each { dataObj ->
                FaceBookLike like = new FaceBookLike()
                like.id = dataObj.id
                like.name = dataObj.name
                like.faceBookReactionType = dataObj.type
                likes.add(like)
            }
        }
        return likes;
    }

    public List<FaceBookComment> getPostComments(String id) {
        def jsonSlurper = new JsonSlurper()
        def ids = id.split('_')
        String result = ""

        String page_access_token = ""
        FaceBookSetting facebookObj = getPageTokenByPageId(ids[0])
        List<FaceBookComment> comments = new ArrayList<>()
        if (facebookObj) {
            page_access_token = facebookObj.getPageAccessToken()
            final String uri = "https://graph.facebook.com/" + id + "/comments?access_token=" + page_access_token;
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(uri, String.class);

            def object = jsonSlurper.parseText(result)

            def data = object.data
            data.each { dataObj ->
                FaceBookComment comment = new FaceBookComment()
                comment.id = dataObj.id
                comment.createdTime = dataObj.created_time
                comment.message = dataObj.message
                FaceBookCommentFrom from = new FaceBookCommentFrom()
                from.id = dataObj.from.id
                from.name = dataObj.from.name
                comment.from = from
                comments.add(comment)
            }
        }

        return comments
    }

    public FaceBookSetting getPageTokenByPageId(String pageId) {
        return faceBookSettingRepository.findByPageId(pageId)
    }

    public Long likesCount(String postId){
        List<FaceBookLike> totalList = getPostLikes(postId);
        List<FaceBookLike> filteredList = new ArrayList<>();
         totalList.each { reactionObj ->
            if(reactionObj.faceBookReactionType.equals(FaceBookReactionType.LIKE)){
                filteredList.add(reactionObj)
            }
        }
        return filteredList.size()
    }

    public Long reactionsCount(String postId){
        List<FaceBookLike> totalList = getPostLikes(postId);
        List<FaceBookLike> filteredList = new ArrayList<>();
        totalList.each { reactionObj ->
            if(!reactionObj.faceBookReactionType.equals(FaceBookReactionType.LIKE)){
                filteredList.add(reactionObj)
            }
        }
        return filteredList.size()
    }

    public Long commentsCount(String postId){
        return getPostComments(postId).size();
    }


}
