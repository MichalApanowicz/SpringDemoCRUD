package pl.apanowicz.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductsResponseDto {

    private final List<Product> products;


    @JsonCreator
    public ProductsResponseDto(@JsonProperty("products") List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

}
