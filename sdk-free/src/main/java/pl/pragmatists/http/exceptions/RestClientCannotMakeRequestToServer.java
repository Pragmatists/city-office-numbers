package pl.pragmatists.http.exceptions;

import java.io.IOException;

public class RestClientCannotMakeRequestToServer extends RuntimeException {
    public RestClientCannotMakeRequestToServer(IOException inner) {
        super(inner);
    }
}
