package com.dashboard.command

import com.dashboard.model.BaseEntity

class BasicCommand {
    String id
    String uuid
    Date dateCreated
    Date lastUpdated

    BasicCommand() {

    }

    BasicCommand(BaseEntity baseEntity) {
        this.id = baseEntity.id
        this.uuid = baseEntity.uuid
        this.dateCreated = baseEntity.dateCreated
        this.lastUpdated = baseEntity.lastUpdated
    }
}