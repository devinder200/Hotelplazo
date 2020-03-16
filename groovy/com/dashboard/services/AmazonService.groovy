package com.dashboard.services

import org.springframework.web.multipart.MultipartFile

public interface AmazonService {
    public ByteArrayOutputStream downloadFile(String keyName)

    public void uploadFile(String keyName, MultipartFile file)

    public void deleteFile(String keyName)

}