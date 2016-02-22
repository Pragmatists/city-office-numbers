package pl.pragmatists.cityofficenumbers.app.selectgroup;

import android.content.Context;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.RestServerError;

public class ErrorUi {

    public static final String NETWORK_PROBLEM_MESSAGE = "Nieudane połączenie z serwerem.";

    private final Context context;

    public ErrorUi(Context context) {
        this.context = context;
    }

    public void onEventMainThread(RestNetworkError error) {
    }

    public void onEventMainThread(RestServerError error) {
    }

}
