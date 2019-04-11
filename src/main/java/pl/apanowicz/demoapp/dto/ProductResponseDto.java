package pl.apanowicz.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.apanowicz.demoapp.domain.Product;

public class ProductResponseDto {

    private final String id;
    private final String name;
    private final PriceDto price;
    private final ImageDto image;

    @JsonCreator
    public ProductResponseDto(@JsonProperty("id") String id,
                              @JsonProperty("name") String name,
                              @JsonProperty("price") PriceDto price,
                              @JsonProperty("image") ImageDto image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }


    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = new PriceDto(product.getPrice());
        this.image = new ImageDto(product.getImage());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PriceDto getPrice() {
        return price;
    }

    public ImageDto getImage() {
        return image;
    }
}
