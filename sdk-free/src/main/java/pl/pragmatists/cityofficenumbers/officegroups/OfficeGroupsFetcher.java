package pl.pragmatists.cityofficenumbers.officegroups;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.http.RestClient;
import pl.pragmatists.http.RestClientCannotMakeRequestToServer;
import pl.pragmatists.http.RestClientServerError;

public class OfficeGroupsFetcher {

    private final RestClient restClient;

    private final EventBus bus;

    public OfficeGroupsFetcher(RestClient restClient, EventBus bus) {
        this.restClient = restClient;
        this.bus = bus;
    }

    public void fetch(String officeId) {
        try {
            OfficeGroupsResult officeGroupsResult = restClient
                    .getForObject("/api/action/wsstore_get/?id=" + officeId, OfficeGroupsResult.class);
            sleepOneSec();
            bus.post(new OfficeGroupsFetched(officeGroupsResult.officeGroups()));
        } catch (RestClientServerError e) {
            bus.post(new OfficeGroupsServerError());
        } catch (RestClientCannotMakeRequestToServer e) {
            bus.post(new OfficeGroupsNetworkError());
        }
    }

    private void sleepOneSec() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
