package pl.apanowicz.demoapp.infrastructure;

import pl.apanowicz.demoapp.domain.Product;

public interface ProductRepository {
    void save(Product product);

    Product findById(String id);

    void update(String id, Product product);

    void removeById(String id);
}
