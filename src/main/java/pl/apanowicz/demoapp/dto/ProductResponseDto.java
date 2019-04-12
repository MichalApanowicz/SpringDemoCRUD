package pl.apanowicz.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.apanowicz.demoapp.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseDto {

    private final String id;
    private final String name;
    private final PriceDto price;
    private final ImageDto image;
    private List<TagDto> tags;

    @JsonCreator
    public ProductResponseDto(@JsonProperty("id") String id,
                              @JsonProperty("name") String name,
                              @JsonProperty("price") PriceDto price,
                              @JsonProperty("image") ImageDto image,
                              @JsonProperty("tags") List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.tags = tags;
    }

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = new PriceDto(product.getPrice());
        this.image = new ImageDto(product.getImage());
        if(product.getTags() != null && !product.getTags().isEmpty()) {
            this.tags = product.getTags()
                    .stream()
                    .map(t -> new TagDto(t.getName()))
                    .collect(Collectors.toList());
        }
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

    public List<TagDto> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return "ProductResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
