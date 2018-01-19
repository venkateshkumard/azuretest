package com.oms.inventory.service;

import com.oms.inventory.exception.InvalidUpdateRequestException;
import com.oms.inventory.exception.InventoryProductNotFoundException;
import com.oms.inventory.model.*;
import com.oms.inventory.repository.InventoryRepository;
import com.oms.inventory.repository.InventoryRepositoryCustom;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private InventoryRepositoryCustom inventoryRepositoryCustom;

    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryResponse addAvailableProductToInventory(InventoryRequest inventoryRequest) {
        List<InventoryItem> itemList = inventoryRequest.getInventoryItemList();
        if(CollectionUtils.isEmpty(itemList)){
           return new InventoryResponse(Arrays.asList());
        }
        List<String> productIdList = itemList.stream()
                                         .map(InventoryItem::getProductId)
                                         .collect(Collectors.toList());
        List<InventoryProduct> fetchedInventoryProducts = inventoryRepositoryCustom.findAllInventoryProducts(productIdList);
        if(fetchedInventoryProducts.size() != productIdList.size()){
            String errorMsg = "Some productIds are not valid. Kindly check your inventory request.";
            LOGGER.error("Error = {}, Ids ={}", errorMsg, productIdList);
            throw new InventoryProductNotFoundException(errorMsg);
        }
        inventoryRepositoryCustom.updateProductsInInventory(addProductsToInventoryUpdate(inventoryRequest));
        return new InventoryResponse(inventoryRepositoryCustom.findAllInventoryProducts(productIdList));
    }

    @Override
    public int getAvailableProductCountFromInventory(String productId) {
        int productCount = 0;
        if(StringUtils.isNotEmpty(productId)){
            productCount = inventoryRepository.findOne(productId).getCount();
        }
        return productCount;
    }

    @Override
    public void updateInventory(List<InventoryUpdate> inventoryUpdateList) {
        if(CollectionUtils.isEmpty(inventoryUpdateList)){
            return;
        }
        if(!isValidUpdateRequest(inventoryUpdateList)){
            String errorMsg = "The items count / threshold cannot be less than zero";
            LOGGER.error("Error = {}", errorMsg);
            throw new InvalidUpdateRequestException(errorMsg);
        }
        inventoryRepositoryCustom.updateProductsInInventory(inventoryUpdateList);
    }

    @Override
    public void deleteProductFromInventory(List<String> productIdList) {
        if(CollectionUtils.isEmpty(productIdList)){
            return;
        }
        inventoryRepositoryCustom.deleteProductsFromInventory(productIdList);
    }

    private List<InventoryUpdate> addProductsToInventoryUpdate(InventoryRequest inventoryRequest){
        List<InventoryUpdate> inventoryUpdateList = new ArrayList<>();
        inventoryRequest.getInventoryItemList().forEach(inventoryItem -> {
            InventoryUpdate addInventoryProduct = new InventoryUpdate();
            int availableCount = inventoryItem.getCount();
            int threshold = inventoryItem.getThreshold();
            addInventoryProduct.setCount(availableCount >= 0 ? availableCount : 0);
            addInventoryProduct.setThreshold(threshold >= 0 ? threshold : 0);
            addInventoryProduct.setProductId(inventoryItem.getProductId());
            inventoryUpdateList.add(addInventoryProduct);
        });
        return inventoryUpdateList;
    }

    private boolean isValidUpdateRequest(List<InventoryUpdate> inventoryUpdateList){
        boolean hasInvalidCounts = inventoryUpdateList.stream()
                                                 .map(InventoryUpdate::getCount)
                                                 .filter(count -> count < 0).count() > 0;
        boolean hasInValidThreshold = inventoryUpdateList.stream()
                                                       .map(InventoryUpdate::getThreshold)
                                                       .filter(threshold -> threshold < 0).count() > 0;
        if(hasInvalidCounts || hasInValidThreshold) {
            return false;
        }
        return true;
    }

    @Override
    public List<InventoryProduct> addProductToRepository(List<InventoryProduct> inventoryProducts) {
        List<InventoryProduct> inventoryProdList = new ArrayList<>();
        inventoryProducts.forEach(inventoryProduct -> {
            inventoryProduct.setCreatedDate(new Date());
            inventoryProdList.add(inventoryRepository.insert(inventoryProduct));
        });
        return inventoryProdList;
    }

    @Override
    public void deleteProductRecords(List<String> productIdList) {
        productIdList.forEach(productId -> {
            inventoryRepository.delete(productId);
        });
    }
}
