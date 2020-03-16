package com.dashboard.command

import com.dashboard.util.FaceBookReactionType


public class FaceBookLike {
    String id;
    String name;
    FaceBookReactionType faceBookReactionType ;

    public FaceBookLike() {
    }

    FaceBookLike(String id, String name, FaceBookReactionType faceBookReactionType) {
        this.id = id
        this.name = name
        this.faceBookReactionType = faceBookReactionType
    }


    @Override
    public String toString() {
        return "FaceBookLike{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", faceBookReactionType=" + faceBookReactionType +
                '}';
    }
}
