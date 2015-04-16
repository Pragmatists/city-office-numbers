package pl.pragmatists.cityofficenumbers.app;

import android.app.Application;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;

public class CityOfficeNumbersApplication extends Application {
    public CityOfficesModel getCityOfficesModel() {
        return new CityOfficesHardcoded();
    }
}
