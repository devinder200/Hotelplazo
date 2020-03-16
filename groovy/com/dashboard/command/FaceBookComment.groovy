package com.dashboard.command

public class FaceBookComment {
    String createdTime
    String id
    String message
    FaceBookCommentFrom from

    public FaceBookComment() {
    }

    public FaceBookComment(String createdTime, String id, String message, FaceBookCommentFrom from) {
        this.createdTime = createdTime
        this.id = id
        this.message = message
        this.from = from
    }

    @Override
    public String toString() {
        return """FacebookComment{
                    createdTime=${createdTime},
                    id=${id},
                    message=${message},
                    from=${from}
                }
                """
    }
}
