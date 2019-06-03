package pl.apanowicz.demoapp.api

import pl.apanowicz.demoapp.IntegrationSpec
import pl.apanowicz.demoapp.dto.ProductRequestDto
import pl.apanowicz.demoapp.dto.ProductResponseDto

class ProductEndpointSpec extends IntegrationSpec {
    def "should return product with id"() {
        given:
        def product = new ProductRequestDto(null, null, null, null)
        def url = "http://localhost:${this.port}/api/v1/products/${existingProduct.getId()}"
        when:
        def result = httpClient.getForEntity(url, ProductResponseDto.class)
        then:
        result.getStatusCodeValue() == 200
    }
}