package pl.apanowicz.demoapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import pl.apanowicz.demoapp.DemoappApplicationTests;
import pl.apanowicz.demoapp.domain.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductEndpointTests extends DemoappApplicationTests {

    @Autowired
    ProductFacade productFacade;

    @Test
    public void shouldGetExistingProduct() {
        ProductRequestDto requestDto = new ProductRequestDto("product");
        ProductResponseDto existingProduct = productFacade.create(requestDto);
        final String url = "http://localhost:" + this.port + "/api/v1/products/" + existingProduct.getId();

        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualToComparingFieldByField(existingProduct);
    }

    @Test
    public void shouldReturnStatus404WhenGetAndProductNotExist() {
        ProductRequestDto requestDto = new ProductRequestDto("product");
        final String url = "http://localhost:" + this.port + "/api/v1/products/fake_product";

        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void shouldCreateProduct() {
        final ProductRequestDto product = new ProductRequestDto("nowyProdukt");
        String productJson = mapToJson(product);

        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(
                "http://localhost:" + this.port + "/api/v1/products/",
                getHttpRequest(productJson),
                ProductResponseDto.class
        );

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody().getName()).isEqualTo("nowyProdukt");
    }

    @Test
    public void shouldUpdateProduct() {
        final ProductRequestDto product = new ProductRequestDto("nowyProdukt");
        ProductResponseDto createdProduct = productFacade.create(product);
        final ProductRequestDto updatedProduct = new ProductRequestDto("zaktualizowanyProdukt");
        String updatedProductJson = mapToJson(updatedProduct);

        ResponseEntity<ProductResponseDto> updateResult = httpClient.exchange(
                "http://localhost:" + this.port + "/api/v1/products/" + createdProduct.getId(),
                HttpMethod.PUT,
                getHttpRequest(updatedProductJson),
                ProductResponseDto.class
        );

        assertThat(productFacade.get(createdProduct.getId()).getName())
                .isEqualTo("zaktualizowanyProdukt");
        assertThat(updateResult.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldReturnStatus404WhenUpdateAndProductNotExist() {
        ProductRequestDto requestDto = new ProductRequestDto("product");
        final String url = "http://localhost:" + this.port + "/api/v1/products/fake_product";
        final ProductRequestDto product = new ProductRequestDto("zaktualizowanyProdukt");
        String productJson = mapToJson(product);

        ResponseEntity<ProductResponseDto> updateResult = httpClient.exchange(
                url,
                HttpMethod.PUT,
                getHttpRequest(productJson),
                ProductResponseDto.class
        );

        assertThat(updateResult.getStatusCodeValue()).isEqualTo(404);
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldDeleteProduct() {
        final ProductRequestDto product = new ProductRequestDto("nowyProdukt");
        ProductResponseDto createdProduct = productFacade.create(product);

        ResponseEntity<ProductResponseDto> result = httpClient.exchange(
                "http://localhost:" + this.port + "/api/v1/products/" + createdProduct.getId(),
                HttpMethod.DELETE,
                null,
                ProductResponseDto.class
        );

        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        productFacade.get(createdProduct.getId());
    }

    @Test
    public void shouldReturnStatus404WhenDeleteAndProductNotExist() {
        ProductRequestDto requestDto = new ProductRequestDto("product");
        final String url = "http://localhost:" + this.port + "/api/v1/products/fake_product";

        ResponseEntity<ProductResponseDto> deleteResult = httpClient.exchange(
                url,
                HttpMethod.DELETE,
                null,
                ProductResponseDto.class
        );

        assertThat(deleteResult.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void shouldReturnListWhenRepositoryNotEmpty() {
        final String url = "http://localhost:" + this.port + "/api/v1/products/";
        productFacade.create(new ProductRequestDto("nowy"));

        ResponseEntity<ProductsResponseDto> getResult = httpClient.getForEntity(url, ProductsResponseDto.class);

        List<Product> products = getResult.getBody().getProducts();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getName()).isEqualTo("nowy");
        assertThat(getResult.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldReturnListWhenRepositoryIsEmpty() {
        final String url = "http://localhost:" + this.port + "/api/v1/products/";

        ResponseEntity<ProductsResponseDto> getResult = httpClient.getForEntity(url, ProductsResponseDto.class);

        List<Product> productsResult = getResult.getBody().getProducts();
        assertThat(productsResult.isEmpty()).isTrue();
        assertThat(getResult.getStatusCodeValue()).isEqualTo(200);
    }


    String mapToJson(ProductRequestDto product) {
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> getHttpRequest(String json) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json");
        return new HttpEntity<>(json, httpHeaders);
    }
}
