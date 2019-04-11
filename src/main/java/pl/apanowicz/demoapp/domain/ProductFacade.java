package pl.apanowicz.demoapp.domain;

import pl.apanowicz.demoapp.dto.ProductRequestDto;
import pl.apanowicz.demoapp.dto.ProductResponseDto;
import pl.apanowicz.demoapp.dto.ProductsResponseDto;
import pl.apanowicz.demoapp.domain.exceptions.ProductNotFoundException;

public interface ProductFacade {

    ProductResponseDto get(String id) throws ProductNotFoundException;

    ProductsResponseDto getAll();

    ProductResponseDto create(ProductRequestDto productRequest);

    ProductResponseDto update(String id, ProductRequestDto productRequest) throws ProductNotFoundException;;

    ProductResponseDto remove(String id) throws ProductNotFoundException;
}
