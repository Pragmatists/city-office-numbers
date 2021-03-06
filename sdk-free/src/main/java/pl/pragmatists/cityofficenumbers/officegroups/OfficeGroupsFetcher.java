package pl.pragmatists.cityofficenumbers.officegroups;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupsResultJson;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestServerError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestNetworkError;
import pl.pragmatists.http.RestClient;
import pl.pragmatists.http.exceptions.RestClientCannotMakeRequestToServer;
import pl.pragmatists.http.exceptions.RestClientServerError;

public class OfficeGroupsFetcher {

    private final RestClient restClient;

    private final EventBus bus;

    public OfficeGroupsFetcher(RestClient restClient, EventBus bus) {
        this.restClient = restClient;
        this.bus = bus;
    }

    public void fetch(String officeId) {
        try {
            OfficeGroupsResultJson officeGroupsResultJson = restClient
                    .getForObject("/api/action/wsstore_get/?id=" + officeId, OfficeGroupsResultJson.class);
            sleepOneSec();
            OfficeGroups officeGroups = OfficeGroups.fromJson(officeGroupsResultJson).officeId(officeId);
            bus.post(new OfficeGroupsFetched(officeGroups));
        } catch (RestClientServerError e) {
            bus.post(new RestServerError(e.getMessage()));
        } catch (RestClientCannotMakeRequestToServer e) {
            bus.post(new RestNetworkError());
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
