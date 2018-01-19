package com.oms.inventory.repository;

import com.oms.inventory.model.InventoryProduct;
import com.oms.inventory.model.InventoryUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryProduct, String> {
}
