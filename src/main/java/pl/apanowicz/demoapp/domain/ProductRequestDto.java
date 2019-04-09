package pl.apanowicz.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;


public class ProductRequestDto {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private final String name;

    @Valid
    @NotNull(message = "Price cannot be null")
    private final Price price;

    @Valid
    private Image image;

    @JsonCreator
    public ProductRequestDto(@JsonProperty("name") String name,
                             @JsonProperty("price") Price price,
                             @JsonProperty(value = "image", required = false) Image image) {
        this.name = name;
        this.price = price;
        this.image = image;

    }

    public ProductRequestDto(String name, Price price) {
        this.name = name;
        this.price = price;
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


    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "name='" + name + '\'' +
                "price='" + price.toString() + '\'' +
                "image='" + image.toString() + '\'' +
                '}';
    }
}
