package pl.pragmatists.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import pl.pragmatists.http.exceptions.RestClientCannotMakeRequestToServer;
import pl.pragmatists.http.exceptions.RestClientServerError;

import java.io.IOException;
import java.io.InputStream;

public class RestClientWithOkHttp implements RestClient {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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
            Response response = createRequestTo(url).execute();
            if (!response.isSuccessful()) {
                if (response.code() >= 400) {
                    throw new RestClientServerError("Server responded with: " + response.code() + " " + response.message());
                }
            }
            InputStream inputStream = response.body().byteStream();
            return objectMapper.readValue(inputStream, responseType);

        } catch (IOException e) {
            throw new RestClientCannotMakeRequestToServer(e);
        }
    }

    @Override
    public void postForObject(String path, Object object) {
        String url = host.getHostUrlPart() + path;
        try {
            String content = objectMapper.writeValueAsString(object);
            RequestBody body = RequestBody.create(JSON, content);
            Request request = new Request.Builder().post(body).url(url).build();
            okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Call createRequestTo(String url) {
        return okHttpClient.newCall(new Request.Builder().url(url).build());
    }
}
