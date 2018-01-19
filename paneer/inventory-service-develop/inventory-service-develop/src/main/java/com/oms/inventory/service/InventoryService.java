package com.oms.inventory.service;

import com.oms.inventory.model.InventoryProduct;
import com.oms.inventory.model.InventoryRequest;
import com.oms.inventory.model.InventoryResponse;
import com.oms.inventory.model.InventoryUpdate;

import java.util.List;

public interface InventoryService {
    InventoryResponse addAvailableProductToInventory(InventoryRequest inventoryRequest);

    int getAvailableProductCountFromInventory(String productId);

    void updateInventory(List<InventoryUpdate> inventoryUpdateList);

    void deleteProductFromInventory(List<String> productIdList);

    //Proxy for Product service.
    List<InventoryProduct> addProductToRepository(List<InventoryProduct> inventoryProducts);

    void deleteProductRecords(List<String> productIdList);
}
