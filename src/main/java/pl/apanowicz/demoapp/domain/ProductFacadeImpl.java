package pl.apanowicz.demoapp.domain;

import org.springframework.stereotype.Component;
import pl.apanowicz.demoapp.infrastructure.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
class ProductFacadeImpl implements ProductFacade {


    public final ProductRepository productRepository;

    ProductFacadeImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequest){
        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Product product = new Product(id, productRequest.getName(), productRequest.getPrice(), createdAt);

        productRepository.save(product);

        ProductResponseDto responseDto = new ProductResponseDto(product);

        return responseDto;
    }

    @Override
    public ProductsResponseDto getAll(){
        List<Product> products = productRepository.findAll();

        return new ProductsResponseDto(products);
    }

    @Override
    public ProductResponseDto get(String id) throws ProductNotFoundException{
        Product product = productRepository.findById(id);
        if(product==null) {
            throw new ProductNotFoundException(id);
        }
        return new ProductResponseDto(product);
    }

    @Override
    public ProductResponseDto update(String id, ProductRequestDto productRequest) throws ProductNotFoundException {
        Product product = productRepository.findById(id);
        if(product==null) {
            throw new ProductNotFoundException(id);
        }
        Product updatedProduct = new Product(id, productRequest.getName(), productRequest.getPrice(), product.getCreatedAt());
        productRepository.update(id, updatedProduct);
        return new ProductResponseDto(updatedProduct);
    }

    @Override
    public ProductResponseDto remove(String id) throws ProductNotFoundException {
        Product product = productRepository.findById(id);
        if(product==null) {
            throw new ProductNotFoundException(id);
        }

        productRepository.removeById(id);
        return new ProductResponseDto(product);
    }


}

