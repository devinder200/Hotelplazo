package com.dashboard.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class MongoEventListenerImpl extends AbstractMongoEventListener<Object> {
    private final Logger logger = LoggerFactory.getLogger(MongoEventListenerImpl.class);

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        logger.info("Inside onAfterSave event of MongoDB for {}", source.getClass());
    }
}
