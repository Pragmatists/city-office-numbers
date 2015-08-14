package pl.pragmatists.http;

import java.io.IOException;

public class RestClientCannotMakeRequestToServer extends RuntimeException {
    public RestClientCannotMakeRequestToServer(IOException inner) {
        super(inner);
    }
}
