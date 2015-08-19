package pl.pragmatists.cityofficenumbers.app.selectgroup;

import android.content.Context;
import android.widget.Toast;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsServerError;

public class ErrorUi {

    public static final String NETWORK_PROBLEM_MESSAGE = "Nieudane połączenie z serwerem.";

    private final Context context;

    public ErrorUi(Context context) {
        this.context = context;
    }

    public void onEventMainThread(OfficeGroupsNetworkError error) {
        Toast.makeText(context, NETWORK_PROBLEM_MESSAGE, Toast.LENGTH_LONG).show();
    }

    public void onEventMainThread(OfficeGroupsServerError error) {
        Toast.makeText(context, "Nie udało się odczytać danych z serwera. " + error.details, Toast.LENGTH_LONG).show();
    }

}
