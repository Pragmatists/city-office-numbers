package pl.pragmatists.cityofficenumbers.offices;

import pl.pragmatists.cityofficenumbers.app.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.app.OfficeGroupsResult;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.http.RestClient;

public class OfficeGroupsFetcher {

    private final RestClient restClientWithRestTemplate;

    private final EventBus bus;

    public OfficeGroupsFetcher(RestClient restClientWithRestTemplate, EventBus bus) {
        this.restClientWithRestTemplate = restClientWithRestTemplate;
        this.bus = bus;
    }

    public void fetch(String officeId) {
        OfficeGroupsResult officeGroupsResult = restClientWithRestTemplate.getForObject("/api/action/wsstore_get/?id=" + officeId, OfficeGroupsResult.class);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bus.post(new OfficeGroupsFetched(officeGroupsResult.officeGroups()));
    }

}
