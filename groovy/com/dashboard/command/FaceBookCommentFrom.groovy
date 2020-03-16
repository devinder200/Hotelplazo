package com.dashboard.command;

public class FaceBookCommentFrom {
    String id
    String name

    public FaceBookCommentFrom() {
    }

    public FaceBookCommentFrom(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "FaceBookCommentFrom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
