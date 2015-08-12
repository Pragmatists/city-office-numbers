package pl.pragmatists.http;

import org.springframework.web.client.RestTemplate;

public class RestClientWithRestTemplate implements RestClient {
    private RestTemplate template = new RestTemplate();

    @Override
    public <T> T getForObject(String url, Class<T> responseType) {
        return template.getForObject(url, responseType);
    }
}
