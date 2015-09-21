package pl.pragmatists.cityofficenumbers.app.selectoffice;

import static pl.pragmatists.cityofficenumbers.app.selectoffice.SelectOfficeActivity.*;

import javax.inject.Inject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;

public class ToggleFavoriteIntentService extends IntentService {

    public static final String ARG_OFFICE_ID = "arg-office-id";

    public static final String ARG_FAVORITE = "is-favorite-now";

    @Inject
    FavoriteService favoriteService;

    public ToggleFavoriteIntentService() {
        super("ToggleFavoriteIntentService");
    }

    public static void startFor(Context context, String userId, Office office) {
        Intent intent = new Intent(context, ToggleFavoriteIntentService.class)
                .putExtra(ARG_USER_ID, userId)
                .putExtra(ARG_OFFICE_ID, office.getId())
                .putExtra(ARG_FAVORITE, office.favorite);
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
        String userId = intent.getStringExtra(ARG_USER_ID);
        String officeId = intent.getStringExtra(ARG_OFFICE_ID);
        boolean isFavoriteNow = intent.getBooleanExtra(ARG_FAVORITE, false);
        favoriteService.toggleFavorite(userId, officeId, isFavoriteNow);
    }
}
