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
    ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto){
        return productFacade.create(productRequestDto);
    }

    @GetMapping("/{id}")
    ProductResponseDto getProduct(@PathVariable("id") String id){
        try {
            return productFacade.get(id);
        }
        catch (ProductNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found", ex);
        }

    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateProduct(@PathVariable("id") String id, @RequestBody ProductRequestDto productRequest){
        try {
            productFacade.update(id, productRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch (ProductNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found", ex);
        }

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable("id") String id){
        try {
            productFacade.remove(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch (ProductNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found", ex);
        }

    }
}
//@JSONCreator