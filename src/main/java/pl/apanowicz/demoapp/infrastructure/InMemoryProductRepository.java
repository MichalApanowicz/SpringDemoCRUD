package pl.apanowicz.demoapp.infrastructure;

import org.springframework.stereotype.Component;
import pl.apanowicz.demoapp.domain.Product;
import pl.apanowicz.demoapp.domain.ProductResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class InMemoryProductRepository implements ProductRepository{

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        products.put(product.getId(),product);
    }

    @Override
    public Product findById(String id) {
        return products.get(id);
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    @Override
    public void update(String id, Product product) {
        products.replace(id, product);
    }

    @Override
    public void removeById(String id) {
        products.remove(id);
    }
}
