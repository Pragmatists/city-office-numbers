package pl.pragmatists.cityofficenumbers.app;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;

public class GroupIntentService extends IntentService {
    static final String OFFICE_ID_KEY = "pl.pragmatists.cityofficenumbers.app.action.FOO";
    private RestTemplate restTemplate;
    protected EventBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        restTemplate = new RestTemplate();
        bus = BusInstance.instance();
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

}
