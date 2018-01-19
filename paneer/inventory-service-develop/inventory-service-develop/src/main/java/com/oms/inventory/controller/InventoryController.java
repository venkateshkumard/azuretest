package com.oms.inventory.controller;

import com.oms.inventory.model.InventoryProduct;
import com.oms.inventory.model.InventoryRequest;
import com.oms.inventory.model.InventoryResponse;
import com.oms.inventory.model.InventoryUpdate;
import com.oms.inventory.service.InventoryService;
import com.oms.inventory.service.InventoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    @Value("${message.orderPaymentExchange:my default value}")
    private String test;

    public InventoryController(InventoryServiceImpl inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public InventoryResponse addAvailableProduct(@RequestBody InventoryRequest inventoryRequest){
        return inventoryService.addAvailableProductToInventory(inventoryRequest);
    }

    @GetMapping("/{productId}/count")
    public int getProductCount(@PathVariable String productId){
        return inventoryService.getAvailableProductCountFromInventory(productId);
    }

    @PostMapping("/products/delete")
    public void deleteProductFromInventory(@RequestBody List<String> productIdList){
        inventoryService.deleteProductFromInventory(productIdList);
    }

    @PatchMapping("/update")
    public void updateProductFromInventory(@RequestBody List<InventoryUpdate> inventoryUpdateList){
        inventoryService.updateInventory(inventoryUpdateList);
    }

    @PostMapping("/addProduct")
    public List<InventoryProduct> addProduct(@RequestBody List<InventoryProduct> inventoryProducts){
        LOGGER.info("*****PVR_Ashok_Test : " + test);
        return inventoryService.addProductToRepository(inventoryProducts);
    }

    @PostMapping("/deleteProductRecord")
    public void deleteProducts(@RequestBody List<String> productIdList){
        LOGGER.info("message={}","Logging IPC call from Product on delete event");
        inventoryService.deleteProductRecords(productIdList);
    }
}
