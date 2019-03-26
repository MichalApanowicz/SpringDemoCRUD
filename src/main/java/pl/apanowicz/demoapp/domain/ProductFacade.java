package pl.apanowicz.demoapp.domain;

public interface ProductFacade {

    ProductResponseDto get(String id) throws ProductNotFoundException;

    ProductResponseDto create(ProductRequestDto productRequest);

    ProductResponseDto update(String id, ProductRequestDto productRequest) throws ProductNotFoundException;;

    ProductResponseDto remove(String id) throws ProductNotFoundException;
}
