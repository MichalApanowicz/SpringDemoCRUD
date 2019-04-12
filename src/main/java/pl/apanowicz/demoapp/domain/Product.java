package pl.apanowicz.demoapp.domain;


import pl.apanowicz.demoapp.dto.TagDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Product {

    private final String id;
    private final String name;
    private final LocalDateTime createdAt;
    private final Price price;
    private final Image image;
    private final List<Tag> tags;


    private Product(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.createdAt = builder.createdAt;
        this.price = builder.price;
        this.image = builder.image;
        this.tags = builder.tags;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Price getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, createdAt);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price.toString() + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }


    public static class Builder {

        private final String id;
        private final String name;
        private final LocalDateTime createdAt;
        private Price price;
        private Image image;
        private List<Tag> tags;

        public Builder(String id, String name, LocalDateTime createdAt) {
            if (id == null || name == null || createdAt == null) {
                throw new IllegalArgumentException("profession and name can not be null");
            }
            this.id = id;
            this.name = name;
            this.createdAt = createdAt;
        }

        public Builder withPrice(Price price){
            this.price = price;
            return this;
        }

        public Builder withImage(Image image){
            this.image = image;
            return this;
        }

        public Builder withTags(ArrayList<Tag> tags){
            this.tags = tags;
            return this;
        }

        public Builder withTagsFromDto(List<TagDto> tags) {
            if (tags != null && !tags.isEmpty()) {
                this.tags = tags.stream()
                        .map(t -> new Tag(t.getName()))
                        .collect(Collectors.toList());
            } else {
                this.tags = null;
            }
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
