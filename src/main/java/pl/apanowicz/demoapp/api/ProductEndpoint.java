package pl.apanowicz.demoapp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.apanowicz.demoapp.domain.ProductFacade;
import pl.apanowicz.demoapp.domain.ProductNotFoundException;
import pl.apanowicz.demoapp.domain.ProductRequestDto;
import pl.apanowicz.demoapp.domain.ProductResponseDto;

@RestController
@RequestMapping("api/v1/products")
class ProductEndpoint {

    private final ProductFacade productFacade;
    public ProductEndpoint(ProductFacade productFacade){
        this.productFacade = productFacade;
    }

    @PostMapping
    ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productFacade.create(productRequestDto));
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") String id) throws ProductNotFoundException{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productFacade.get(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") String id, @RequestBody ProductRequestDto productRequest){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productFacade.update(id, productRequest));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable("id") String id){
        productFacade.remove(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}