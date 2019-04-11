package pl.apanowicz.demoapp.domain;

import org.springframework.stereotype.Component;
import pl.apanowicz.demoapp.dto.ProductRequestDto;
import pl.apanowicz.demoapp.dto.ProductResponseDto;
import pl.apanowicz.demoapp.dto.ProductsResponseDto;
import pl.apanowicz.demoapp.domain.exceptions.ProductNotFoundException;
import pl.apanowicz.demoapp.infrastructure.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Product product = new Product(id, createdAt, productRequest);

        productRepository.save(product);

        ProductResponseDto responseDto = new ProductResponseDto(product);

        return responseDto;
    }

    @Override
    public ProductsResponseDto getAll(){
        List<ProductResponseDto> products = productRepository
                .findAll()
                .stream()
                .map(x -> new ProductResponseDto(x))
                .collect(Collectors.toList());

        return new ProductsResponseDto(products);
    }

    @Override
    public ProductResponseDto get(String id) throws ProductNotFoundException {
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
        Product updatedProduct = new Product(id, product.getCreatedAt(), productRequest);
        productRepository.update(updatedProduct);
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

