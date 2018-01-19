package com.oms.inventory.model;

import java.util.List;

public class InventoryRequest {
    private List<InventoryItem> inventoryItemList;

    public List<InventoryItem> getInventoryItemList() {
        return inventoryItemList;
    }

    public void setInventoryItemList(List<InventoryItem> inventoryItemList) {
        this.inventoryItemList = inventoryItemList;
    }
}
