package pl.pragmatists.cityofficenumbers.app.selectoffice;

import javax.inject.Inject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesFetcher;

public class OfficesIntentService extends IntentService{

    @Inject
    CityOfficesFetcher cityOfficesFetcher;

    public OfficesIntentService() {
        super("OfficesIntentService");
    }

    public static void startFetchOfficesAction(Context context, String userId) {
        Intent intent = new Intent(context, OfficesIntentService.class);
        intent.putExtra(SelectOfficeActivity.ARG_USER_ID, userId);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getMyApplication().component().inject(this);
    }

    private CityOfficeNumbersApplication getMyApplication() {
        return (CityOfficeNumbersApplication) getApplication();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String userId = intent.getStringExtra(SelectOfficeActivity.ARG_USER_ID);
        cityOfficesFetcher.fetch(userId);
    }
}
