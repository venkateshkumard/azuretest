package com.oms.product.controller;

import com.oms.product.model.domain.ProductDTO;
import com.oms.product.model.request.ProductRequest;
import com.oms.product.model.response.ProductResponse;
import com.oms.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Service  Name: Product Service
 * File Name : ProductController.java
 * Description : Managing Product Details
 * Story ID : None
 * Dependent Service : Inventory-Service
 */
@RestController
@RequestMapping("/Product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProductDetails(@Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProductDetails(productDTO);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ProductResponse getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @DeleteMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void cancelProduct(@PathVariable String id) {
        productService.cancelProduct(id);
    }

    @GetMapping("/searchAllProducts")
    @ResponseStatus(HttpStatus.FOUND)
    public ProductResponse searchAllProducts() {
        return productService.searchAllProducts();
    }

    @GetMapping("/searchByName/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public ProductResponse searchByName(@RequestParam(required = false) boolean isLike,@PathVariable String name) {
        return productService.searchProductsByName(name,isLike);
    }


}
