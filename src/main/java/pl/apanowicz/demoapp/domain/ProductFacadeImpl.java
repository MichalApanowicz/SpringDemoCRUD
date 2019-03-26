package pl.apanowicz.demoapp.domain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.apanowicz.demoapp.infrastructure.ProductRepository;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProductFacadeImpl implements ProductFacade {


    public final ProductRepository productRepository;

    ProductFacadeImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequest){
        if (!productRequest.isValid()){
            throw new RuntimeException("Product name cannot be empty!");
        }
        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Product product = new Product(id, productRequest.getName(), createdAt);

        productRepository.save(product);

        ProductResponseDto responseDto = new ProductResponseDto(product.getId(),product.getName());

        return responseDto;
    }

    @Override
    public ProductResponseDto get(String id) throws ProductNotFoundException{
        Product product = productRepository.findById(id);
        if(product==null) {
            throw new ProductNotFoundException();
        }
        return new ProductResponseDto(product.getId(), product.getName());
    }

    @Override
    public ProductResponseDto update(String id, ProductRequestDto productRequest) throws ProductNotFoundException {
        Product product = productRepository.findById(id);
        if(product==null) {
            throw new ProductNotFoundException();
        }
        Product updatedProduct = new Product(id, productRequest.getName(), product.getCreatedAt());
        productRepository.update(id, updatedProduct);
        return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName());
    }

    @Override
    public ProductResponseDto remove(String id) throws ProductNotFoundException {
        Product product = productRepository.findById(id);
        if(product==null) {
            throw new ProductNotFoundException();
        }

        productRepository.removeById(id);
        return new ProductResponseDto(product.getId(), product.getName());
    }


}

