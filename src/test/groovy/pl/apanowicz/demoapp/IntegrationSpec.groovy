package pl.apanowicz.demoapp

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ContextConfiguration
import pl.apanowicz.demoapp.dto.ProductRequestDto

@ContextConfiguration
@SpringBootTest(
        classes = DemoappApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class IntegrationSpec {

    @Autowired
    protected TestRestTemplate httpClient

    @LocalServerPort
    protected int port

    @Autowired
    protected ObjectMapper objectMapper

    protected String mapToJson(ProductRequestDto product) {
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpEntity<String> getHttpRequest(String json) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json");
        return new HttpEntity<>(json, httpHeaders);
    }
}
