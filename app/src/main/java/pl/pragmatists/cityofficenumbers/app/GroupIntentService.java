package pl.pragmatists.cityofficenumbers.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;

public class GroupIntentService extends IntentService {
    static final String OFFICE_ID_KEY = "pl.pragmatists.cityofficenumbers.app.action.FOO";


    private OfficeGroupsFetcher fetcher;

    @Override
    public void onCreate() {
        super.onCreate();
        fetcher = ((CityOfficeNumbersApplication) getApplication()).getOfficeGroupsFetcher();
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
        fetcher.fetch(officeId);
    }

}
