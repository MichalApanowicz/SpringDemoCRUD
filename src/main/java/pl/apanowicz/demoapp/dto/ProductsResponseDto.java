package pl.apanowicz.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsResponseDto {

    private final List<ProductResponseDto> products;

    @JsonCreator
    public ProductsResponseDto(@JsonProperty("products") List<ProductResponseDto> products) {
        this.products = products;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "ProductResponseDto{" +
                products.stream()
                        .map(p -> p.toString())
                        .collect(Collectors.joining(", ")) +
                '}';
    }
}
