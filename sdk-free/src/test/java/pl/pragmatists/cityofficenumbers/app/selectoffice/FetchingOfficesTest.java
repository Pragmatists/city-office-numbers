package pl.pragmatists.cityofficenumbers.app.selectoffice;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.app.selectoffice.messages.CityOfficesFetchedEvent;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

public class FetchingOfficesTest {

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

    //TODO: handle errors for offices
    @Ignore
    @Test
    public void publishesErrorEventOn500() throws IOException {
    }

    //TODO: handle errors for offices
    @Ignore
    @Test
    public void publishesErrorEventOn400() throws IOException {
    }

    //TODO: handle errors for offices
    @Ignore
    @Test
    public void publishesErrorEventWhenServerUnreachable() throws IOException {
    }

    private CityOfficesFetcher createCityOfficesFetcher() {
        return new CityOfficesFetcher(new RestClientWithOkHttp(getHost()), bus);
    }

    private Host getHost() {
        return new Host("http", server.getHostName(), server.getPort());
    }

}