package lebibop.warehouseservice.service;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalService {
    @Value("${user.service.url}")
    private String externalUserServiceUrl;

    @Value("${external.service.token}") @Getter
    private String externalToken;

    private final RestTemplate restTemplate;

    public ExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public void checkBalanceToExternalService(Double price, String jwtToken) {
        String url = externalUserServiceUrl + "/check-balance";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.set("External-Token", externalToken);

        HttpEntity<Double> requestEntity = new HttpEntity<>(price, headers);

        try {
            restTemplate.postForEntity(url, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new AccessDeniedException("Access is denied: " + e.getResponseBodyAsString());
            } else {
                throw new IllegalArgumentException(e.getResponseBodyAsString());
            }
        }
    }

}
