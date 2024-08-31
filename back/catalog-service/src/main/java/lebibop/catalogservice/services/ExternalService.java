package lebibop.catalogservice.services;

import lebibop.catalogservice.models.Cart;
import lebibop.catalogservice.DTO.CartItemRequest;
import lebibop.catalogservice.DTO.CartMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExternalService {
    @Value("${warehouse.service.url}")
    private String externalServiceUrl;

    @Value("${external.service.token}")
    private String externalToken;

    private final RestTemplate restTemplate;

    public ExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void checkAvailabilityToExternalService(Cart cart, String jwtToken) {
        String url = externalServiceUrl + "/check-availability";
        CartItemRequest cartDTO = CartMapper.toCartItemRequest(cart);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.set("External-Token", externalToken);

        HttpEntity<CartItemRequest> requestEntity = new HttpEntity<>(cartDTO, headers);
        try {
            restTemplate.postForEntity(url, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException(String.valueOf(e.getMessage()));
        }
    }
}
