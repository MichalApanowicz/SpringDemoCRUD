package pl.apanowicz.demoapp.infrastructure;

import pl.apanowicz.demoapp.domain.Product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);

    Product findById(String id);

    List<Product> findAll();

    void update(Product product);

    void removeById(String id);
}
