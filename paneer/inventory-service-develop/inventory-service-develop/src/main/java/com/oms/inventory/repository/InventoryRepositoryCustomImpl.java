package com.oms.inventory.repository;

import com.oms.inventory.model.InventoryProduct;
import com.oms.inventory.model.InventoryUpdate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class InventoryRepositoryCustomImpl implements InventoryRepositoryCustom {
    public static final String PROD_ID = "_id";
    public static final String PROD_COUNT = "count";
    public static final String PROD_THRESHOLD = "threshold";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateProductsInInventory(List<InventoryUpdate> inventoryUpdateList) {
        BulkOperations bulk = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, InventoryProduct.class);
        bulk.updateMulti(updateInventoryProductsQuery(inventoryUpdateList)).execute();
    }

    @Override
    public void deleteProductsFromInventory(List<String> productIds) {
        BulkOperations bulk = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, InventoryProduct.class);
        bulk.updateMulti(deleteInventoryProductsQuery(productIds)).execute();
    }

    @Override
    public List<InventoryProduct> findAllInventoryProducts(List<String> productIdList) {
        Query query = new Query();
        query.addCriteria(where(PROD_ID).in(productIdList));
        return mongoTemplate.find(query, InventoryProduct.class);
    }

    private List<Pair<Query, Update>> updateInventoryProductsQuery(List<InventoryUpdate> inventoryUpdateList) {
        List<Pair<Query, Update>> operations = new ArrayList<>();
        inventoryUpdateList.stream().forEach(inventoryUpdate -> {
            Criteria prodIdCriteria = new Criteria().andOperator(
                    where(PROD_ID).is(new ObjectId(inventoryUpdate.getProductId())));
            Query updateQuery = new Query();
            updateQuery.addCriteria(prodIdCriteria);
            Update update = new Update();
            update.set(PROD_COUNT, inventoryUpdate.getCount());
            update.set(PROD_THRESHOLD, inventoryUpdate.getThreshold());
            operations.add(Pair.of(updateQuery, update));
        });
        return operations;
    }

    private List<Pair<Query, Update>> deleteInventoryProductsQuery(List<String> productDeleteList) {
        List<Pair<Query, Update>> operations = new ArrayList<>();
        productDeleteList.stream().forEach(productId -> {
            Criteria prodIdCriteria = new Criteria().andOperator(
                    where(PROD_ID).is(new ObjectId(productId)));
            Query updateQuery = new Query();
            updateQuery.addCriteria(prodIdCriteria);
            Update update = new Update();
            update.set(PROD_COUNT, 0);
            update.set(PROD_THRESHOLD, 0);
            operations.add(Pair.of(updateQuery, update));
        });
        return operations;
    }
}
