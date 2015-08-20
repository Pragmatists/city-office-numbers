package pl.pragmatists.cityofficenumbers.offices;

import java.util.Arrays;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.offices.messages.CityOfficesFetchedEvent;
import pl.pragmatists.http.RestClient;

public class CityOfficesFetcher {
    private final RestClient restClient;

    private final EventBus bus;

    public CityOfficesFetcher(RestClient restClient, EventBus bus) {

        this.restClient = restClient;
        this.bus = bus;
    }

    public void fetch(String userId) {
        Office[] offices = restClient.getForObject("/users/" + userId + "/offices", Office[].class);
        bus.post(new CityOfficesFetchedEvent(Arrays.asList(offices)));
    }
}
