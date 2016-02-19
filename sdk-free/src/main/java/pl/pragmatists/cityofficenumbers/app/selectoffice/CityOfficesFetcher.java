package pl.pragmatists.cityofficenumbers.app.selectoffice;

import java.util.Arrays;

import pl.pragmatists.cityofficenumbers.app.selectoffice.messages.CityOfficesFetchedEvent;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestServerError;
import pl.pragmatists.http.RestClient;
import pl.pragmatists.http.exceptions.RestClientCannotMakeRequestToServer;
import pl.pragmatists.http.exceptions.RestClientServerError;

public class CityOfficesFetcher {
    private final RestClient restClient;

    private final EventBus bus;

    public CityOfficesFetcher(RestClient restClient, EventBus bus) {

        this.restClient = restClient;
        this.bus = bus;
    }

    public void fetch(String userId) {
        try {
            Office[] offices = restClient.getForObject("/users/" + userId + "/offices", Office[].class);
            bus.post(new CityOfficesFetchedEvent(Arrays.asList(offices)));
        } catch (RestClientCannotMakeRequestToServer e) {
            bus.post(new RestNetworkError());
        } catch (RestClientServerError e) {
            bus.post(new RestServerError(e.getMessage()));
        }

    }
}
