package com.oms.product.delegate;

import com.oms.product.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.inventoryServiceURL}")
    private String inventoryService_url;

    public void deleteProductFromInventory(String productId){
        List<String> productIdList = new ArrayList<String>();
        productIdList.add(productId);
        LOGGER.info("message={}", "Before Calling Inventory To Remove Count Details");
        LOGGER.info("Inventory Service URL for IPC call={}", inventoryService_url);
        restTemplate.postForObject(inventoryService_url, productIdList, String.class);
        LOGGER.info("message={}", "After Successfully Removing Count Details From Inventory");
    }

}
