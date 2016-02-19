package pl.pragmatists.cityofficenumbers.app.selectoffice;

import android.support.v7.app.AppCompatActivity;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;

public class CityOfficeNumbersActivity extends AppCompatActivity {
    protected CityOfficeNumbersApplication getMyApplication() {
        return (CityOfficeNumbersApplication) getApplication();
    }
}
