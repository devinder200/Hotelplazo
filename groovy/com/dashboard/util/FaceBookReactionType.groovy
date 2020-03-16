package com.dashboard.util

enum FaceBookReactionType {

    NONE('None'),
    LIKE('Like'),
    LOVE('Love'),
    WOW('Wow'),
    HAHA('Haha'),
    SAD('Sad'),
    ANGRY('Angry'),
    THANKFUL('Thankful'),
    PRIDE('Pride')

    String value

    FaceBookReactionType(String value){
        this.value =value
    }
}
