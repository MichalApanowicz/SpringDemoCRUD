package pl.apanowicz.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponseDto {

    private final String id;
    private final String name;
    private final Price price;
    private final Image image;

    @JsonCreator
    public ProductResponseDto(@JsonProperty("id") String id,
                              @JsonProperty("name") String name,
                              @JsonProperty("price") Price price,
                              @JsonProperty("image") Image image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }


    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.image = product.getImage();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }
}
