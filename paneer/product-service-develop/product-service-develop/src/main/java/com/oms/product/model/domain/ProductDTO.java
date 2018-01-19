package com.oms.product.model.domain;


import com.oms.product.model.PackingInfo;
import com.oms.product.model.Specifications;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ProductDTO {

    private String id;

    @NotBlank(message = "Product name can't be blank")
    @Size(min = 5, max = 15)
    private String productDisplayName;

    @NotBlank(message = "Product description can't be blank")
    @Size(min = 10, max = 25)
    private String description;

    @DecimalMin(value = "1.00", message = "Price must be higher than ${value}")
    @DecimalMax(value = "99999.999", message = "Price must be lower than ${value}")
    private Double price;

    @Valid
    @NotNull(message = "Product PackingInfo can't be blank")
    private PackingInfo packingInfo;

    @Valid
    @NotNull(message = "Product Specifications can't be blank")
    private Specifications specifications;

    private Date createdDate;
    private Date lastModifiedDate;

    public String getId() {
        return id;
    }

    public ProductDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductDisplayName() {
        return productDisplayName;
    }

    public ProductDTO setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public PackingInfo getPackingInfo() {
        return packingInfo;
    }

    public ProductDTO setPackingInfo(PackingInfo packingInfo) {
        this.packingInfo = packingInfo;
        return this;
    }

    public Specifications getSpecifications() {
        return specifications;
    }

    public ProductDTO setSpecifications(Specifications specifications) {
        this.specifications = specifications;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public ProductDTO setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ProductDTO setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }


}
