package pl.apanowicz.demoapp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.apanowicz.demoapp.domain.ProductFacade;
import pl.apanowicz.demoapp.dto.ProductRequestDto;
import pl.apanowicz.demoapp.dto.ProductResponseDto;
import pl.apanowicz.demoapp.dto.ProductsResponseDto;
import pl.apanowicz.demoapp.domain.exceptions.ProductNotFoundException;
import pl.apanowicz.demoapp.domain.exceptions.ProductValidationException;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("api/v1/products")
class ProductEndpoint {

    private final ProductFacade productFacade;
    public ProductEndpoint(ProductFacade productFacade){
        this.productFacade = productFacade;
    }

    @GetMapping
    ResponseEntity<ProductsResponseDto> getAllProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productFacade.getAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") String id) throws ProductNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productFacade.get(id));
    }

    @PostMapping
    ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto request) throws ProductValidationException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productFacade.create(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") String id,
                                                     @Valid @RequestBody ProductRequestDto request){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productFacade.update(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable("id") String id){
        productFacade.remove(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}