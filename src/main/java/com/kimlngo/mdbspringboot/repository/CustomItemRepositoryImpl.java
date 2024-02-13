package com.kimlngo.mdbspringboot.repository;

import com.kimlngo.mdbspringboot.model.GroceryItem;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CustomItemRepositoryImpl implements CustomItemRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemRepositoryImpl.class);

    private MongoTemplate mongoTemplate;

    @Autowired
    public CustomItemRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateItemQuantity(String name, int newQuantity) {
        LOGGER.debug("Perform Update quantity for " + name + "to " + newQuantity);

        Query query = new Query(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("quantity", newQuantity);

        UpdateResult result = mongoTemplate.updateFirst(query, update, GroceryItem.class);
        LOGGER.debug("Was update acknowledged? - "  + (result.wasAcknowledged() ? "YES" : "NO"));
    }
}
