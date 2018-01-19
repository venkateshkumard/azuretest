package com.oms.inventory.model;

import java.util.List;

public class InventoryResponse {
    private List<InventoryProduct> inventoryProducts;

    public InventoryResponse(List<InventoryProduct> inventoryProducts){
        this.inventoryProducts = inventoryProducts;
    }

    public List<InventoryProduct> getInventoryResponse() {
        return inventoryProducts;
    }

    public void setInventoryResponse(List<InventoryProduct> inventoryResponse) {
        this.inventoryProducts = inventoryResponse;
    }
}
