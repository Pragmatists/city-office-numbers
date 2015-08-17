package pl.pragmatists.cityofficenumbers.offices;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsServerError;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

public class FetchingGroupsTest {

    private static final String ANY_ID = "any_office_id";

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
    public void fetchesGroups() throws IOException {
        server.enqueue(new MockResponse().setBody("" +
                "{\n" +
                "    \"result\": {\n" +
                "        \"date\": \"2015-04-19\",\n" +
                "        \"grupy\": [\n" +
                "            {\n" +
                "                \"status\": null,\n" +
                "                \"czasObslugi\": \"0\",\n" +
                "                \"lp\": null,\n" +
                "                \"idGrupy\": \"1\",\n" +
                "                \"liczbaCzynnychStan\": 0,\n" +
                "                \"nazwaGrupy\": \"URODZENIA\",\n" +
                "                \"literaGrupy\": \"U\",\n" +
                "                \"liczbaKlwKolejce\": 0,\n" +
                "                \"aktualnyNumer\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"time\": \"16:36\"\n" +
                "    }\n" +
                "} \n"));
        OfficeGroupsFetcher officeGroupsFetcher = createOfficeGroupsFetcher();

        officeGroupsFetcher.fetch("9c3d5770-57d8-4365-994c-69c5ac4186ee");

        ArgumentCaptor<OfficeGroupsFetched> captor = ArgumentCaptor.forClass(OfficeGroupsFetched.class);
        verify(bus).post(captor.capture());
        assertThat(captor.getValue().groups()).hasSize(1);
    }

    @Test
    public void publishesErrorEventOn500() throws IOException {
        server.enqueue(new MockResponse().setStatus("HTTP/1.1 500 Internal Server Error"));
        OfficeGroupsFetcher officeGroupsFetcher = createOfficeGroupsFetcher();

        officeGroupsFetcher.fetch(ANY_ID);

        verify(bus).post(isA(OfficeGroupsServerError.class));
    }

    @Test
    public void publishesErrorEventOn400() throws IOException {
        server.enqueue(new MockResponse().setStatus("HTTP/1.1 400 Bad Request"));
        OfficeGroupsFetcher officeGroupsFetcher = createOfficeGroupsFetcher();

        officeGroupsFetcher.fetch(ANY_ID);

        verify(bus).post(isA(OfficeGroupsServerError.class));
    }

    @Test
    public void publishesErrorEventWhenServerUnreachable() throws IOException {
        OfficeGroupsFetcher officeGroupsFetcher = new OfficeGroupsFetcher(
                new RestClientWithOkHttp(new Host("http://unavailable.server")), bus
        );

        officeGroupsFetcher.fetch(ANY_ID);

        verify(bus).post(isA(OfficeGroupsNetworkError.class));
    }

    private OfficeGroupsFetcher createOfficeGroupsFetcher() {
        return new OfficeGroupsFetcher(new RestClientWithOkHttp(getHost()), bus);
    }

    private Host getHost() {
        return new Host("http", server.getHostName(), server.getPort());
    }

}