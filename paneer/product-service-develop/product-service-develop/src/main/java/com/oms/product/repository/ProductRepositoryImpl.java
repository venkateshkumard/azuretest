package com.oms.product.repository;


import com.mongodb.WriteResult;
import com.oms.product.model.Dimensions;
import com.oms.product.model.PackingInfo;
import com.oms.product.model.Specifications;
import com.oms.product.model.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

public class ProductRepositoryImpl implements ProductCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public ProductEntity updateProductByName(ProductEntity productEntity) {
        Query query = new Query(Criteria.where("_id").is(productEntity.getId()));
        Update update = new Update();

        String productDisplayName = productEntity.getProductDisplayName();
        if(productDisplayName!=null && !productDisplayName.isEmpty())
            update.set("productDisplayName", productDisplayName);

        String description = productEntity.getDescription();
        if (description != null && !description.isEmpty())
            update.set("description", description);

        Double price = productEntity.getPrice();
        if (price != null)
            update.set("price", price);

        Date lastModifiedDate = productEntity.getLastModifiedDate();
        update.set("lastModifiedDate", lastModifiedDate);

        PackingInfo packingInfo = productEntity.getPackingInfo();
        if (packingInfo != null) {
            getPackingInfoUpdateFields(update, packingInfo);
        }

        Specifications specifications = productEntity.getSpecifications();
        if (specifications != null) {
            getSpecificationUpdateFields(update, specifications);
        }

        WriteResult result = mongoTemplate.updateFirst(query, update, ProductEntity.class);

        ProductEntity productEntityRetrieved = null;
        if (result != null && result.getN() == 1)
            productEntityRetrieved = mongoTemplate.findOne(query, ProductEntity.class);

        return productEntityRetrieved;
    }

    private void getPackingInfoUpdateFields(Update update, PackingInfo packingInfo) {
        Double weight = packingInfo.getWeight();
        if (weight != null)
            update.set("packingInfo.weight", weight);
        Dimensions dimensions = packingInfo.getDimensions();
        if (dimensions != null) {
            Double weightOfDimensions = dimensions.getWeight();
            if (weightOfDimensions != null)
                update.set("packingInfo.dimensions.weight", weightOfDimensions);
            Double heightOfDimensions = dimensions.getHeight();
            if (heightOfDimensions != null)
                update.set("packingInfo.dimensions.height", heightOfDimensions);
            Double depthOfDimensions = dimensions.getDepth();
            if (depthOfDimensions != null)
                update.set("packingInfo.dimensions.depth", depthOfDimensions);
        }
    }

    private void getSpecificationUpdateFields(Update update, Specifications specifications) {
        String name = specifications.getName();
        if (name != null && !name.isEmpty())
            update.set("specifications.name", name);
        String value = specifications.getValue();
        if (value != null && !value.isEmpty())
            update.set("specifications.value", value);
    }
}
