package pl.pragmatists.http.infrastructure;

import org.springframework.web.client.RestTemplate;

import pl.pragmatists.http.Http;

public class RestTemplateHttp implements Http {

    private final RestTemplate restTemplate;

    public RestTemplateHttp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T get(String url, Class<T> body) {
        return restTemplate.getForObject(url, body);
    }
}
