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
import pl.apanowicz.demoapp.domain.Currency;
import pl.apanowicz.demoapp.domain.ProductFacade;
import pl.apanowicz.demoapp.domain.exceptions.ProductNotFoundException;
import pl.apanowicz.demoapp.dto.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductEndpointTests extends DemoappApplicationTests {

    @Autowired
    ProductFacade productFacade;

    private final PriceDto samplePrice = new PriceDto(100, Currency.PLN);
    private final ImageDto sampleImage = new ImageDto("http://apanowi.cz/image1");
    private final List<TagDto> sampleTags = Arrays.asList(new TagDto("test"));
    private final ProductRequestDto sampleProduct = new ProductRequestDto("product", samplePrice, sampleImage, sampleTags);

    @Test
    public void shouldReturnExistingProduct() {
        ProductResponseDto existingProduct = productFacade.create(sampleProduct);
        final String url = "http://localhost:" + this.port + "/api/v1/products/" + existingProduct.getId();

        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualToComparingFieldByField(existingProduct);
    }

    @Test
    public void shouldReturnExistingProductsByTag() {
        productFacade.create(new ProductRequestDto("product1", samplePrice, sampleImage, Arrays.asList(new TagDto("jeden"))));
        productFacade.create(new ProductRequestDto("product2", samplePrice, sampleImage, Arrays.asList(new TagDto("dwa"), new TagDto("cztery"))));
        productFacade.create(new ProductRequestDto("product3", samplePrice, sampleImage, Arrays.asList(new TagDto("dwa"))));
        productFacade.create(new ProductRequestDto("product4", samplePrice, sampleImage, Arrays.asList(new TagDto("trzy"))));

        final String url = "http://localhost:" + this.port + "/api/v1/products/?tag=dwa";

        ResponseEntity<ProductsResponseDto> result = httpClient.getForEntity(url, ProductsResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getProducts().size()).isEqualTo(2);
        assertThat(result.getBody().getProducts().stream().map(p -> p.getName()).collect(Collectors.toList())
                .containsAll(Arrays.asList("product2", "product3")))
                .isTrue();
    }

    @Test
    public void shouldReturnStatus404WhenGetAndProductNotExist() {
        final String url = "http://localhost:" + this.port + "/api/v1/products/fake_product";

        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url, ProductResponseDto.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void shouldCreateProduct() {
        String productJson = mapToJson(sampleProduct);

        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(
                "http://localhost:" + this.port + "/api/v1/products/",
                getHttpRequest(productJson),
                ProductResponseDto.class
        );

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody().getName()).isEqualTo("product");
    }

    @Test
    public void shouldCreateProductWithPrice() {
        String productJson = mapToJson(sampleProduct);

        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(
                "http://localhost:" + this.port + "/api/v1/products/",
                getHttpRequest(productJson),
                ProductResponseDto.class
        );

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody().getPrice()).isEqualTo(samplePrice);
    }

    @Test
    public void shouldCreateProductWithImage() {
        String productJson = mapToJson(sampleProduct);

        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(
                "http://localhost:" + this.port + "/api/v1/products/",
                getHttpRequest(productJson),
                ProductResponseDto.class
        );

        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody().getImage()).isEqualTo(sampleImage);
    }

    @Test
    public void shouldUpdateProduct() {
        ProductResponseDto createdProduct = productFacade.create(sampleProduct);
        final ProductRequestDto updatedProduct = new ProductRequestDto("zaktualizowanyProdukt", samplePrice, sampleImage, null);
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
    public void shouldUpdateProductPrice() {
        ProductResponseDto createdProduct = productFacade.create(sampleProduct);
        PriceDto newPrice = new PriceDto(200, Currency.EUR);
        final ProductRequestDto updatedProductRequest = new ProductRequestDto("nowyProdukt", newPrice, sampleImage, null);

        ResponseEntity<ProductResponseDto> updateResult = httpClient.exchange(
                "http://localhost:" + this.port + "/api/v1/products/" + createdProduct.getId(),
                HttpMethod.PUT,
                getHttpRequest(mapToJson(updatedProductRequest)),
                ProductResponseDto.class
        );

        ProductResponseDto updatedProduct = productFacade.get(createdProduct.getId());
        assertThat(updatedProduct.getPrice()).isEqualTo(newPrice);
        assertThat(updateResult.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldUpdateProductImage() {
        ProductResponseDto createdProduct = productFacade.create(sampleProduct);
        ImageDto newImage = new ImageDto("https://apanowi.cz/xxx/100");
        final ProductRequestDto updatedProductRequest = new ProductRequestDto("nowyProdukt", samplePrice, newImage, null);

        ResponseEntity<ProductResponseDto> updateResult = httpClient.exchange(
                "http://localhost:" + this.port + "/api/v1/products/" + createdProduct.getId(),
                HttpMethod.PUT,
                getHttpRequest(mapToJson(updatedProductRequest)),
                ProductResponseDto.class
        );

        ProductResponseDto updatedProduct = productFacade.get(createdProduct.getId());
        assertThat(updatedProduct.getImage()).isEqualTo(newImage);
        assertThat(updateResult.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldReturnStatus404WhenUpdateAndProductNotExist() {
        final String url = "http://localhost:" + this.port + "/api/v1/products/fake_product";
        String productJson = mapToJson(sampleProduct);

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
        ProductResponseDto createdProduct = productFacade.create(sampleProduct);

        ResponseEntity<ProductResponseDto> response = httpClient.exchange(
                "http://localhost:" + this.port + "/api/v1/products/" + createdProduct.getId(),
                HttpMethod.DELETE,
                null,
                ProductResponseDto.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        productFacade.get(createdProduct.getId());
    }

    @Test
    public void shouldReturnStatus404WhenDeleteAndProductNotExist() {
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
        productFacade.create(sampleProduct);

        ResponseEntity<ProductsResponseDto> getResult = httpClient.getForEntity(url, ProductsResponseDto.class);

        List<ProductResponseDto> products = getResult.getBody().getProducts();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getName()).isEqualTo("product");
        assertThat(getResult.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldReturnListWhenRepositoryIsEmpty() {
        final String url = "http://localhost:" + this.port + "/api/v1/products/";

        ResponseEntity<ProductsResponseDto> getResult = httpClient.getForEntity(url, ProductsResponseDto.class);

        List<ProductResponseDto> productsResult = getResult.getBody().getProducts();
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
