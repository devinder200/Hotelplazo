package com.dashboard.util

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum AssetType {
    IMAGE("Image"),
    VIDEO("Video"),
    FILE("File"),
    TEXT('Text'),
    IMAGE_TEXT('Image & Text'),
    VIDEO_TEXT('Video & Text'),
    FILE_TEXT('File & Text')
    String value

    AssetType(String value) {
        this.value = value
    }

    String value() {
        return value
    }

    static List<AssetType> getList() {
        return [IMAGE, VIDEO, FILE, TEXT, IMAGE_TEXT, VIDEO_TEXT, FILE_TEXT]
    }


    static AssetType extractFileType(String type, String description) {
        String fileType = type.split("/")[0]
        if (fileType.equalsIgnoreCase("Image")) {
            return description ? IMAGE_TEXT : IMAGE
        } else if (fileType.equalsIgnoreCase("Video")) {
            return description ? VIDEO_TEXT : VIDEO
        } else {
            return description ? FILE_TEXT : FILE
        }
    }
}