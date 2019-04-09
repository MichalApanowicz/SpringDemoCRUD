package pl.apanowicz.demoapp.domain;


import java.time.LocalDateTime;
import java.util.Objects;

public final class Product {

    private final String id;
    private final String name;
    private final LocalDateTime createdAt;
    private final Price price;
    private final Image image;

    public Product(String id, String name, Price price, Image image, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
    }

    public Product(String id, LocalDateTime createdAt, ProductRequestDto request) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = request.getName();
        this.price = request.getPrice();
        this.image = request.getImage();
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
}
