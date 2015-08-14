package pl.pragmatists.http;

public class RestClientServerError extends RuntimeException {
    public RestClientServerError(String details) {
        super(details);
    }
}
