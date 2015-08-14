package pl.pragmatists.cityofficenumbers.officegroups;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.http.RestClient;

public class OfficeGroupsFetcher {

    private final RestClient restClient;

    private final EventBus bus;

    public OfficeGroupsFetcher(RestClient restClient, EventBus bus) {
        this.restClient = restClient;
        this.bus = bus;
    }

    public void fetch(String officeId) {
        OfficeGroupsResult officeGroupsResult = restClient.getForObject("/api/action/wsstore_get/?id=" + officeId, OfficeGroupsResult.class);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bus.post(new OfficeGroupsFetched(officeGroupsResult.officeGroups()));
    }

}
