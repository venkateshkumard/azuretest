package com.oms.product.model.request;


import com.oms.product.model.domain.ProductDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

public class ProductRequest {

    @Valid
    @NotEmpty(message = "Product(s) can't be empty")
    private List<ProductDTO> products;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public ProductRequest setProducts(List<ProductDTO> products) {
        this.products = products;
        return this;
    }
}
