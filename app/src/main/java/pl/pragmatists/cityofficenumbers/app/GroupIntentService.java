package pl.pragmatists.cityofficenumbers.app;

import java.util.List;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import de.greenrobot.event.EventBus;

public class GroupIntentService extends IntentService {
    private static final String OFFICE_ID_KEY = "pl.pragmatists.cityofficenumbers.app.action.FOO";
    private RestTemplate restTemplate;
    private EventBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        restTemplate = new RestTemplate();
        bus = EventBus.getDefault();
    }

    public static void startFetchGroupsAction(Context context, String officeId) {
        Intent intent = new Intent(context, GroupIntentService.class);
        intent.putExtra(OFFICE_ID_KEY, officeId);
        context.startService(intent);
    }

    public GroupIntentService() {
        super("GroupIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String officeId = intent.getStringExtra(OFFICE_ID_KEY);
        final String url = "https://api.um.warszawa.pl/api/action/wsstore_get/?id=" + officeId;
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        OfficeGroupsResult officeGroupsResult = restTemplate.getForObject(url, OfficeGroupsResult.class);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bus.post(new OfficeGroupsFetched(officeGroupsResult.officeGroups()));
    }

    public static class OfficeGroupsFetched {

        private final List<OfficeGroup> officeGroups;

        public OfficeGroupsFetched(List<OfficeGroup> officeGroups) {

            this.officeGroups = officeGroups;
        }

        public List<OfficeGroup> groups() {
            return officeGroups;
        }

    }

}
