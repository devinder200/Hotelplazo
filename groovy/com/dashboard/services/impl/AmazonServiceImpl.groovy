package com.dashboard.services.impl

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.*
import com.dashboard.services.AmazonService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import javax.annotation.PostConstruct

@Service("amazonService")
@Slf4j
class AmazonServiceImpl implements AmazonService {

    @Value('${amazonProperties.bucketName}')
    private String bucketName
    @Value('${amazonProperties.accessKey}')
    private String accessKey
    @Value('${amazonProperties.secretKey}')
    private String secretKey
    @Value('${amazonProperties.region}')
    private String region

    private AmazonS3 s3client

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey)
        this.s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build()
    }

    @Override
    ByteArrayOutputStream downloadFile(String keyName) {
        try {
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName))

            InputStream is = s3object.getObjectContent()
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
            int len
            byte[] buffer = new byte[4096]
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len)
            }

            return baos
        } catch (IOException ioe) {
            log.error("IOException: " + ioe.getMessage())
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException from GET requests, rejected reasons:")
            log.info("Message: {}/Status: {}/Code: {}/Type: {}/Request: {}", ase.message, ase.statusCode, ase.errorCode, ase.errorType, ase.requestId)
            throw ase
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException: ")
            log.info("Error Message: " + ace.getMessage())
            throw ace
        }
        return null
    }

    @Override
    void uploadFile(String keyName, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata()
            metadata.setContentLength(file.getSize())
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, file.getInputStream(), metadata)
            putObjectRequest.cannedAcl = CannedAccessControlList.PublicRead
            s3client.putObject(putObjectRequest)
        } catch (IOException ioe) {
            log.error("IOException: " + ioe.getMessage())
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException from PUT requests, rejected reasons:")
            log.info("Message: {}/Status: {}/Code: {}/Type: {}/Request: {}", ase.message, ase.statusCode, ase.errorCode, ase.errorType, ase.requestId)
            throw ase
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException: ")
            log.info("Error Message: " + ace.getMessage())
            throw ace
        } catch (Exception e) {
            log.info("Caught an Exception: ")
            log.info("Error Message: " + e.getMessage())
            throw e
        }
    }

    public void deleteFile(String keyName) {
        try {
            s3client.deleteObject(new DeleteObjectRequest(bucketName, keyName))
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException from PUT requests, rejected reasons:")
            log.info("Message: {}/Status: {}/Code: {}/Type: {}/Request: {}", ase.message, ase.statusCode, ase.errorCode, ase.errorType, ase.requestId)
            throw ase
        } catch (SdkClientException sce) {
            log.info("Caught an SdkClientException: ")
            log.info("Error Message: " + sce.getMessage())
            throw sce
        }
    }
}
