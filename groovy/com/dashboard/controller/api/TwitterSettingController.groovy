package com.dashboard.controller.api

import com.dashboard.model.twitter.TwitterSetting
import com.dashboard.services.TwitterSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.validation.Valid
import groovy.json.JsonSlurper

@RestController
@RequestMapping(value = "/api/twitter")
class TwitterSettingController {
    @Autowired
    TwitterSettingService twitterSettingService


    @PostMapping(value = "/createTwitter")
    HttpEntity<?> registerTwitter(@Valid @RequestBody TwitterSetting twitterSetting, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<?>(result.allErrors, HttpStatus.OK)
        }
        twitterSettingService.save(twitterSetting)
        return new ResponseEntity<TwitterSetting>(twitterSetting, HttpStatus.CREATED)
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable String id) {
        twitterSettingService.delete(id)
        return id
    }

    @PutMapping(value = "/{id}")
    HttpEntity<?> update(@RequestBody TwitterSetting twitterSetting, @PathVariable String id) {
        if (!twitterSettingService.findById(id)) {
            return new ResponseEntity<?>(HttpStatus.NOT_FOUND)
        }
        twitterSettingService.update(id, twitterSetting)
        return new ResponseEntity<TwitterSetting>(twitterSetting, HttpStatus.OK)
    }

    @GetMapping(value = "/")
    public List<TwitterSetting> listTwitterSettings() {
        twitterSettingService.findAll()
    }

    @GetMapping(value = "/webhook")
    public String hello(@RequestParam String crc_token) {
        try {
            String twitter_consumer_secret = "ZyQjVdkH0nOhf6G8NVCElKmwLffZEQM2dDT46U3SRr4wQWPF9p"
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(twitter_consumer_secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(crc_token.getBytes()));
            System.out.println(hash);
            String encodedString = 'sha256=' + hash
            encodedString = "{\"response_token\":" + "\"" + encodedString + "\"}";
            return encodedString

        }
        catch (Exception e) {
            System.out.println("Error");
            return e
        }
    }

    @PostMapping(value = '/webhook')
    public HttpEntity<?> webhookEvent(@RequestBody String obj) {
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(obj)
        def two_d_url_list = object.tweet_create_events.entities.urls.expanded_url
        def url_list = two_d_url_list[0]
        // our url to compare with the posted url
        def url = "https://www.google.com/"
        url_list.each {
            if (it == url) {
                println("this is our url: $it")
                println("this is the id of the user posted  : " + object.for_user_id)
                println("this is the id of the post : " + object.tweet_create_events.id)
                println("this is the string id of the post : " + object.tweet_create_events.id_str)
            }
        }
        return new ResponseEntity<?>(HttpStatus.OK)
    }
}
