package pl.pragmatists.cityofficenumbers.offices;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import pl.pragmatists.cityofficenumbers.app.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

public class FetchingGroupsTest {

    private EventBus bus = mock(EventBus.class);

    @Test
    public void fetchesGroups() throws IOException {
        MockWebServer server = new MockWebServer();


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
        server.start();
        Host host = new Host("http", server.getHostName(), server.getPort());
        OfficeGroupsFetcher officeGroupsFetcher = new OfficeGroupsFetcher(new RestClientWithOkHttp(host), bus);

        officeGroupsFetcher.fetch("9c3d5770-57d8-4365-994c-69c5ac4186ee");

        ArgumentCaptor<OfficeGroupsFetched> captor = ArgumentCaptor.forClass(OfficeGroupsFetched.class);
        verify(bus).post(captor.capture());
        assertThat(captor.getValue().groups()).hasSize(1);
    }

}