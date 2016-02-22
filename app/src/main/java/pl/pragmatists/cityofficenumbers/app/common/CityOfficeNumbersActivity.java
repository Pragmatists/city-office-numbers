package pl.pragmatists.cityofficenumbers.app.common;

import android.support.v7.app.AppCompatActivity;

public class CityOfficeNumbersActivity extends AppCompatActivity {
    protected CityOfficeNumbersApplication getMyApplication() {
        return (CityOfficeNumbersApplication) getApplication();
    }
}
