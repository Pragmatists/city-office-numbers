package pl.pragmatists.http;

import static java.lang.String.*;

public class Host {

    private  final String value;

    public Host(String protocol, String hostName, int port) {
        value = format("%s://%s:%s", protocol, hostName, port);
    }

    public Host(String value) {
        this.value = value;
    }

    public String getHostUrlPart() {
        return value;
    }
}
