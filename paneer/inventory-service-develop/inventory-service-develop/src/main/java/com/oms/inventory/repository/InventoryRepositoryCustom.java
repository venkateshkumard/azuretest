package com.oms.inventory.repository;

import com.oms.inventory.model.InventoryProduct;
import com.oms.inventory.model.InventoryUpdate;

import java.util.List;

public interface InventoryRepositoryCustom {
    void updateProductsInInventory(List<InventoryUpdate> inventoryUpdate);

    void deleteProductsFromInventory(List<String> productIds);

    List<InventoryProduct> findAllInventoryProducts(List<String> productIdList);
}
