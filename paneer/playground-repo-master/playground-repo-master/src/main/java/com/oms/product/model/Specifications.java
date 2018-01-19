package com.oms.product.model;


import org.hibernate.validator.constraints.NotBlank;

import java.util.function.Function;

public class Specifications {

    @NotBlank(message = "Specification name can't be blank")
    private String name;

    @NotBlank(message = "Specification value can't be blank")
    private String value;


    public String getName() {
        return name;
    }

    public Specifications setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Specifications setValue(String value) {
        this.value = value;
        return this;
    }

    public <T>  void filedValueIfUpdated(T targetValue, T sourceValue, Function<T,?> diffSetter){
        if(targetValue!=null && !targetValue.equals(sourceValue)){
            diffSetter.apply(targetValue);
        }
    }
}
