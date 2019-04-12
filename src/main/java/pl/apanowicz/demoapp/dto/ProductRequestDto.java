package pl.apanowicz.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


public class ProductRequestDto {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private final String name;

    @Valid
    @NotNull(message = "Price cannot be null")
    private final PriceDto price;

    @Valid
    private ImageDto image;

    private List<TagDto> tags;

    @JsonCreator
    public ProductRequestDto(@JsonProperty("name") String name,
                             @JsonProperty("price") PriceDto price,
                             @JsonProperty("image") ImageDto image,
                             @JsonProperty("tags") List<TagDto> tags) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.tags = tags;
    }

    public String getName() {
        return this.name;
    }

    public PriceDto getPrice() {
        return this.price;
    }

    public ImageDto getImage() {
        return this.image;
    }

    public List<TagDto> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", tags=" + tags +
                '}';
    }
}
