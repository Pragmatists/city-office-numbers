package pl.pragmatists.cityofficenumbers.offices;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import pl.pragmatists.cityofficenumbers.app.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.http.RestClientWithRestTemplate;

public class FetchingGroupsTest {

    private EventBus bus = mock(EventBus.class);

    @Test
    public void fetchesGroups() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo("https://api.um.warszawa.pl/api/action/wsstore_get/?id=9c3d5770-57d8-4365-994c-69c5ac4186ee"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("" +
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
                        "} \n", MediaType.APPLICATION_JSON));
        OfficeGroupsFetcher officeGroupsFetcher = new OfficeGroupsFetcher(new RestClientWithRestTemplate(), bus);

        officeGroupsFetcher.fetch("9c3d5770-57d8-4365-994c-69c5ac4186ee");

        ArgumentCaptor<OfficeGroupsFetched> captor = ArgumentCaptor.forClass(OfficeGroupsFetched.class);
        verify(bus).post(captor.capture());
        org.assertj.core.api.Assertions.assertThat(captor.getValue().groups()).isNotEmpty();
    }

}