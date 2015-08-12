package pl.pragmatists.http;

public interface Http {
    <T> T get(String url, Class<T> body);
}
