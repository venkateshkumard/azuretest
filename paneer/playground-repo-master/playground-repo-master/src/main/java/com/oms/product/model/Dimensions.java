package com.oms.product.model;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.function.Function;

public class Dimensions {

    @DecimalMin(value = "1.00", message = "Packing Weight must be higher than {value} mg")
    @DecimalMax(value = "10.00", message = "Packing Weight must be lower than {value} mg")
    private Double weight;

    @DecimalMin(value = "1.00", message = "Packing Weight must be higher than {value} m")
    @DecimalMax(value = "5.00", message = "Packing Weight must be lower than {value} m")
    private Double height;

    @DecimalMin(value = "1.00", message = "Packing Weight must be higher than {value} m")
    @DecimalMax(value = "3.00", message = "Packing Weight must be lower than {value} m")
    private Double depth;


    public Double getWeight() {
        return weight;
    }

    public Dimensions setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public Double getHeight() {
        return height;
    }

    public Dimensions setHeight(Double height) {
        this.height = height;
        return this;
    }

    public Double getDepth() {
        return depth;
    }

    public Dimensions setDepth(Double depth) {
        this.depth = depth;
        return this;
    }

    public <T>  void filedValueIfUpdated(T targetValue, T sourceValue, Function<T,?> diffSetter){
        if(targetValue!=null && !targetValue.equals(sourceValue)){
            diffSetter.apply(targetValue);
        }
    }
}
