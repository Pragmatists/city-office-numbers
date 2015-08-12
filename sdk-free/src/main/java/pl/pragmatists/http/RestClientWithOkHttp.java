package pl.pragmatists.http;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class RestClientWithOkHttp implements RestClient {

    private final Host host;

    OkHttpClient okHttpClient = new OkHttpClient();

    ObjectMapper objectMapper = new ObjectMapper();

    public RestClientWithOkHttp(Host host) {
        this.host = host;
    }

    @Override
    public <T> T getForObject(String path, Class<T> responseType) {
        try {
            String url = host.getHostUrlPart() + path;
            InputStream inputStream = okHttpClient.newCall(new Request.Builder().url(url).build()).execute().body().byteStream();
            return objectMapper.readValue(inputStream, responseType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
