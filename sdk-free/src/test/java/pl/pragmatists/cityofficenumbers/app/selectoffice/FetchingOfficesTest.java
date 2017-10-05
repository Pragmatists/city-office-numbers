package pl.pragmatists.cityofficenumbers.app.selectoffice;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestServerError;
import pl.pragmatists.cityofficenumbers.selectoffice.CityOfficesFetcher;
import pl.pragmatists.cityofficenumbers.selectoffice.messages.CityOfficesFetchedEvent;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

public class FetchingOfficesTest {

    private static final String ANY_ID = "any-id";

    private EventBus bus = mock(EventBus.class);

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void fetches_city_offices() throws IOException {
        server.enqueue(new MockResponse().setBody("" +
                "[\n" +
                "    {\n" +
                "        \"name\": \"USC Andersa\",\n" +
                "        \"id\": \"5d2e698a-9c31-456b-8452-7ce33e7deb94\",\n" +
                "        \"favorite\": false\n" +
                "    }\n" +
                "]\n"));
        CityOfficesFetcher officeGroupsFetcher = createCityOfficesFetcher();

        officeGroupsFetcher.fetch("user-id");

        ArgumentCaptor<CityOfficesFetchedEvent> captor = ArgumentCaptor.forClass(CityOfficesFetchedEvent.class);
        verify(bus).post(captor.capture());
        assertThat(captor.getValue().offices()).hasSize(1);
    }

    @Test
    public void publishesErrorEventOn500() throws IOException {
        server.enqueue(new MockResponse().setStatus("HTTP/1.1 500 Internal Server Error"));
        CityOfficesFetcher cityOfficesFetcher = createCityOfficesFetcher();

        cityOfficesFetcher.fetch(ANY_ID);

        verify(bus).post(isA(RestServerError.class));
    }

    //TODO: handle errors for offices
    @Ignore
    @Test
    public void publishesErrorEventOn400() throws IOException {
    }

    @Test
    public void publishesErrorEventWhenServerUnreachable() throws IOException {
        CityOfficesFetcher cityOfficesFetcher = new CityOfficesFetcher(
                new RestClientWithOkHttp(new Host("http://unavailable.server")), bus
        );

        cityOfficesFetcher.fetch(ANY_ID);

        verify(bus).post(isA(RestNetworkError.class));

    }

    private CityOfficesFetcher createCityOfficesFetcher() {
        return new CityOfficesFetcher(new RestClientWithOkHttp(getHost()), bus);
    }

    private Host getHost() {
        return new Host("http", server.getHostName(), server.getPort());
    }

}