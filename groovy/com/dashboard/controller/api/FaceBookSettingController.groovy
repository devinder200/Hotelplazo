package com.dashboard.controller.api

import com.dashboard.command.FaceBookComment
import com.dashboard.command.FaceBookLike
import com.dashboard.model.Asset

import com.dashboard.model.SocialMediaStat
import com.dashboard.services.AssetService
import com.dashboard.services.SocialMediaStatService
import com.dashboard.util.SocialMediaType
import groovy.json.JsonSlurper
import com.dashboard.model.FaceBookSetting
import groovy.util.logging.Slf4j
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import com.dashboard.services.FaceBookSettingService
import javax.validation.Valid

@RestController
@RequestMapping(value = "/api/facebook")
@Slf4j
class FaceBookSettingController {

    @Autowired
    FaceBookSettingService facebookSettingService

    @Autowired
    AssetService assetService

    @Autowired
    SocialMediaStatService socialMediaStatService

    @GetMapping(value = "/")
    public List<FaceBookSetting> listFaceBookSettings() {
        facebookSettingService.findAll()
    }

    @PostMapping(value = "/create")
    HttpEntity<?> register(@Valid @RequestBody FaceBookSetting faceBookSetting, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<?>(result.allErrors, HttpStatus.OK)
        }

        facebookSettingService.save(faceBookSetting)
        return new ResponseEntity<FaceBookSetting>(faceBookSetting, HttpStatus.CREATED)
    }

    @DeleteMapping(value = "/{appId}")
    public String delete(@PathVariable String appId) {
        facebookSettingService.delete(appId)
        return appId
    }

    @PutMapping(value = "/{appId}")
    HttpEntity<?> update(@RequestBody FaceBookSetting faceBookSetting, @PathVariable String appId) {
        if (!facebookSettingService.findByAppId(appId)) {
            return new ResponseEntity<?>(HttpStatus.NOT_FOUND)
        }
        facebookSettingService.update(appId, faceBookSetting)
        return new ResponseEntity<FaceBookSetting>(faceBookSetting, HttpStatus.OK)
    }

    @GetMapping(value = "/webhooks")
    public String webhooks(@RequestParam(name = "hub.mode") String mode, @RequestParam(name = "hub.challenge") String challenge,
                           @RequestParam(name = "hub.verify_token") String verifyToken) {


        //for authentication of webhook
        //TheTokenOfVerification
        return challenge;
    }

    @PostMapping(value = "/webhooks")
    public HttpEntity<?> webhookEvent(@RequestBody String obj) {

        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(obj)
        println("json string ==================" + object)
        def link = object.entry[0].changes[0].value.link
        def postType = object.entry[0].changes[0].value.item
        def postEvent = object.entry[0].changes[0].value.verb
        if(object.object.equals("page") && postType.equals("share") && postEvent.equals("add")) {
            //check that new post is added on page event occur and not any other event occur
            Asset asset = assetService.findByUrl(link);
            if(asset){
                if(asset.socialMediaStats.size()>0)
                {
                    asset.socialMediaStats.each { statObj ->
                        if(statObj.socialMediaType == SocialMediaType.FACEBOOK) {
                            statObj.postIds.add((object.entry[0].changes[0].value.post_id))
                            socialMediaStatService.save(statObj)
                        }
                    }
                }else{
                    SocialMediaStat facebookSocialMediaStats = new SocialMediaStat();
                    facebookSocialMediaStats.postIds.add(((object.entry[0].changes[0].value.post_id)))
                    socialMediaStatService.save(facebookSocialMediaStats)
                    asset.socialMediaStats.add(facebookSocialMediaStats)
                }
                assetService.save(asset)
            }
        }
        return new ResponseEntity<?>(HttpStatus.OK)
    }


    @PostMapping(value = "/{postId}/likes")
    public List<FaceBookLike> getPostLikes(@PathVariable String postId) {
        return facebookSettingService.getPostLikes(postId)
    }

    @PostMapping(value = "/{postId}/comments")
    public List<FaceBookComment> getPostComments(@PathVariable String postId) {
        return facebookSettingService.getPostComments(postId)
    }


}

