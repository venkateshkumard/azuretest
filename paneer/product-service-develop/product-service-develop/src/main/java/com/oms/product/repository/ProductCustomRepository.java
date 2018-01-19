package com.oms.product.repository;


import com.oms.product.model.entity.ProductEntity;

public interface ProductCustomRepository {

    public ProductEntity updateProductByName(ProductEntity productEntity);
}
