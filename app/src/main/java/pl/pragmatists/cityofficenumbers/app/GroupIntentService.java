package pl.pragmatists.cityofficenumbers.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;

public class GroupIntentService extends IntentService {

    private OfficeGroupsFetcher fetcher;

    @Override
    public void onCreate() {
        super.onCreate();
        fetcher = ((CityOfficeNumbersApplication) getApplication()).getOfficeGroupsFetcher();
    }

    public static void startFetchGroupsAction(Context context, String officeId) {
        Intent intent = new Intent(context, GroupIntentService.class);
        intent.putExtra(SelectGroupActivity.ARG_OFFICE_ID, officeId);
        context.startService(intent);
    }

    public GroupIntentService() {
        super("GroupIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String officeId = intent.getStringExtra(SelectGroupActivity.ARG_OFFICE_ID);
        fetcher.fetch(officeId);
    }

}
