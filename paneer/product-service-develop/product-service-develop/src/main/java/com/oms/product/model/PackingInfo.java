package com.oms.product.model;


import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

public class PackingInfo {

    @DecimalMin(value = "1.00", message = "Packing Weight must be higher than {value} kg")
    @DecimalMax(value = "50.00", message = "Packing Weight must be lower than {value} kg")
    private Double weight;

    @Valid
    @NotNull(message = "PackingInfo Dimensions can't be blank")
    private Dimensions dimensions;


    public Double getWeight() {
        return weight;
    }

    public PackingInfo setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public PackingInfo setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public <T>  void filedValueIfUpdated(T targetValue, T sourceValue, Function<T,?> diffSetter){
        if(targetValue!=null && !targetValue.equals(sourceValue)){
            diffSetter.apply(targetValue);
        }
    }

}
