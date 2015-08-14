package pl.pragmatists.cityofficenumbers.app;

import android.app.Application;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

public class CityOfficeNumbersApplication extends Application {
    public CityOfficesModel getCityOfficesModel() {
        return new CityOfficesHardcoded();
    }

    public OfficeGroupsFetcher getOfficeGroupsFetcher() {
        return new OfficeGroupsFetcher(new RestClientWithOkHttp(new Host("https://api.um.warszawa.pl")), BusInstance.instance());
    }
}
