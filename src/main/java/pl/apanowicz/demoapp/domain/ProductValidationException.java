package pl.apanowicz.demoapp.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductValidationException extends RuntimeException {
    public ProductValidationException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public ProductValidationException(List<String> errorMessages) {
        super(String.join(", ",errorMessages));
    }


}
