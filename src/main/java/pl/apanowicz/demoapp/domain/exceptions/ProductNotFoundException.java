package pl.apanowicz.demoapp.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    private String requestedProductId;

    public ProductNotFoundException(String requestedProductId) {
        this.requestedProductId = requestedProductId;
    }
}
