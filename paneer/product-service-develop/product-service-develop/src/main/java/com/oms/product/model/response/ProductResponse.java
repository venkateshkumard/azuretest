package com.oms.product.model.response;


import com.oms.product.model.domain.ProductDTO;

import java.util.List;

public class ProductResponse {
    private List<ProductDTO> products;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public ProductResponse setProducts(List<ProductDTO> products) {
        this.products = products;
        return this;
    }
}
