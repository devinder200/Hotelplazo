package com.dashboard.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document

@Document
class BaseEntity {
    String uuid = UUID.randomUUID().toString()
    @Id
    String id
    @Version
    Integer version
    @CreatedDate
    Date dateCreated
    @LastModifiedDate
    Date lastUpdated = new Date()
}

