package com.oms.product.model.entity;


import com.oms.product.model.PackingInfo;
import com.oms.product.model.Specifications;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.function.Function;

@Document(collection = "Product")
public class ProductEntity {

    @Id
    private String id;

    private String productDisplayName;
    private String description;
    private Double price;
    private PackingInfo packingInfo;
    private Specifications specifications;

    @DateTimeFormat
    private Date createdDate;

    @DateTimeFormat
    private Date lastModifiedDate;


    public String getId() {
        return id;
    }

    public ProductEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductDisplayName() {
        return productDisplayName;
    }

    public ProductEntity setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductEntity setPrice(Double price) {
        this.price = price;
        return this;
    }

    public PackingInfo getPackingInfo() {
        return packingInfo;
    }

    public ProductEntity setPackingInfo(PackingInfo packingInfo) {
        this.packingInfo = packingInfo;
        return this;
    }

    public Specifications getSpecifications() {
        return specifications;
    }

    public ProductEntity setSpecifications(Specifications specifications) {
        this.specifications = specifications;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public ProductEntity setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ProductEntity setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public <T>  void filedValueIfUpdated(T targetValue, T sourceValue, Function<T,?> diffSetter){
        if(targetValue!=null && !targetValue.equals(sourceValue)){
            diffSetter.apply(targetValue);
        }
    }
}
