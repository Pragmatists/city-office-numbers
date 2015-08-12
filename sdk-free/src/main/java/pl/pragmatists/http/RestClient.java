package pl.pragmatists.http;

public interface RestClient {
    <T> T getForObject(String url, Class<T> responseType);
}
